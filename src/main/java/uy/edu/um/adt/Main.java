package uy.edu.um.adt;

import uy.edu.um.entities.Artista;
import uy.edu.um.entities.Cancion;
import uy.edu.um.entities.Top50;
import uy.edu.um.adt.hash.HashImpl;
import uy.edu.um.exceptions.InformacionInvalida;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {
        try {
            cargardatos();
        } catch (FileNotFoundException | ParseException | InformacionInvalida e) {
            e.printStackTrace();
        }
    }

    public static void cargardatos() throws FileNotFoundException, ParseException, InformacionInvalida {
        File file = new File("/Users/sophiaguerra/Desktop/universal_top_spotify_songs.csv");
        Scanner sc = new Scanner(file);

        // Se saltea la primera línea
        if (sc.hasNext()) {
            sc.nextLine();
        }

        HashImpl<String, Artista> artistasTodos = new HashImpl<>(100000);
        MyLinkedListImpl<Top50> top50List = new MyLinkedListImpl<>();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            // dividimos la línea manualmente para separar el primer campo que no tiene comillas
            int firstCommaIndex = line.indexOf(',');
            String restOfLine = line.substring(firstCommaIndex + 1);

            // usamos split para separar los campos restantes
            String[] data = restOfLine.split("\",\"");

            // limpiamos las comillas de los campos que nos interesan
            String nombre = data[0].replaceAll("\"", "").trim();
            String artistaNombre = data[1].replaceAll("\"", "").trim();
            int posicion = Integer.parseInt(data[2].replaceAll("[^0-9]", "").trim());
            float tempo = Float.parseFloat(data[22].replaceAll("[^0-9.]", "").trim());
            String pais = data[5].replaceAll("\"", "").trim();
            String fechaStr = data[6].replaceAll("\"", "").trim();
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);

            // separamos los artistas si son más de 2 y creamos una lista de artistas
            String[] artistasArray = artistaNombre.split(",");
            MyLinkedListImpl<Artista> artistasList = new MyLinkedListImpl<>();
            Cancion cancion = new Cancion(nombre, artistasList, posicion, tempo);

            for (String artistName : artistasArray) {
                artistName = artistName.trim();
                Artista artista = artistasTodos.search(artistName);
                if (artista == null) {
                    artista = new Artista();
                    artista.nombre = artistName;
                    artistasTodos.insert(artistName, artista);
                }
                artistasList.add(artista);
            }

            // busca el top50 correspondiente y lo crea si no existe
            Top50 top50 = null;
            for (int i = 0; i < top50List.size(); i++) {
                Top50 t = top50List.get(i);
                if (t.getPais().equals(pais) && t.getFecha().equals(fecha)) {
                    top50 = t;
                    break;
                }
            }
            if (top50 == null) {
                top50 = new Top50(pais, new Cancion[50], fecha);
                top50List.add(top50);
            }

            // agrega la canción al Top50 que le corresponde
            for (int i = 0; i < top50.getPlaylist().length; i++) {
                if (top50.getPlaylist()[i] == null) {
                    top50.getPlaylist()[i] = cancion;
                    break;
                }
            }

            // verificamos
            System.out.println("Nombre: " + nombre);
            System.out.println("Artistas: " + artistaNombre);
            System.out.println("Posición: " + posicion);
            System.out.println("Tempo: " + tempo);
            System.out.println("País: " + pais);
            System.out.println("Fecha: " + fecha);
            System.out.println();
        }
        sc.close();
    }
}
