package uy.edu.um;

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

import static java.lang.System.in;


public class Main {

    private static final String MYPATH = "C:\\Users\\pc\\Desktop\\Facultad\\Semestre3\\Prog2\\DataSet\\universal_top_spotify_songs.csv";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static HashImpl<String, HashImpl<Date, Top50>> top50Map = new HashImpl<>(100);
    private static MyLinkedListImpl<String> paises = new MyLinkedListImpl<>();

    public static void main(String[] args) {
        try {
            cargardatos();

            // obtener el top 10 de canciones para Uruguay en una fecha específica
            String pais = "UY";
            String fechaStr = "2024-01-0";

            obtenerTop10Canciones(pais, fechaStr);
            obtener5CancionesMasRepetidas(fechaStr);

        } catch (FileNotFoundException | ParseException | InformacionInvalida e) {
            e.printStackTrace();
        }
    }

    public static void cargardatos() throws FileNotFoundException, ParseException, InformacionInvalida {
        File file = new File(MYPATH);
        Scanner sc = new Scanner(file);

        // Se saltea la primer linea
        if (sc.hasNext()) {
            sc.nextLine();
        }

        HashImpl<String, Artista> artistasTodos = new HashImpl<>(100);

        while (sc.hasNext()) {
            String line = sc.nextLine();
            // separamos el primer campo
            int primerSeparador = line.indexOf(',');
            String resto = line.substring(primerSeparador + 1);

            // separamos los campos restantes
            String[] data = resto.split("\",\"");

            // limpiamos las comillas que sobran
            String nombre = data[0].replaceAll("\"", "").trim();
            String artistaNombre = data[1].replaceAll("\"", "").trim();
            int posicion = Integer.parseInt(data[2].replaceAll("[^0-9]", "").trim());
            float tempo = Float.parseFloat(data[22].replaceAll("[^0-9.]", "").trim());
            String pais = data[5].replaceAll("\"", "").trim();
            if(!paises.contains(pais))
                paises.add(pais);
            String fechaStr = data[6].replaceAll("\"", "").trim();
            Date fecha = DATE_FORMAT.parse(fechaStr);

            String[] artistasArray = artistaNombre.split(",");
            MyLinkedListImpl<Artista> artistasList = new MyLinkedListImpl<>();
            Cancion cancion = new Cancion(nombre, artistasList, posicion, tempo);

            for (String artistName : artistasArray) {
                artistName = artistName.trim();
                Artista artista = artistasTodos.search(artistName);
                if (artista == null) {
                    artista = new Artista(artistName);
                    artistasTodos.insert(artistName, artista);
                }
                artistasList.add(artista);
            }

            // busca el HashImpl de fechas correspondiente al país, lo crea si no existe
            HashImpl<Date, Top50> fechaMap = top50Map.search(pais);
            if (fechaMap == null) {
                fechaMap = new HashImpl<>(100);
                top50Map.insert(pais, fechaMap);
            }

            // busca el top50 correspondiente a la fecha, lo crea si no existe
            Top50 top50 = fechaMap.search(fecha);
            if (top50 == null) {
                top50 = new Top50(pais, fecha);
                fechaMap.insert(fecha, top50);
            }

            // agrega la canción al Top50 que le corresponde
            for (int i = 0; i < top50.getPlaylist().length; i++) {
                if (top50.getPlaylist()[i] == null) {
                    top50.getPlaylist()[i] = cancion;
                    break;
                }
            }
        }
        sc.close();
    }

    public static void obtenerTop10Canciones(String pais, String fechaStr) throws ParseException, InformacionInvalida {
        Date fecha = DATE_FORMAT.parse(fechaStr);
        HashImpl<Date, Top50> fechaMap = top50Map.search(pais);
        if (fechaMap != null) {
            Top50 top50 = fechaMap.search(fecha);
            if (top50 != null) {
                Cancion[] playlist = top50.getPlaylist();
                int count = 0;
                System.out.println("Top 10 canciones para " + pais + " en la fecha " + fechaStr + ":");
                for (int i = 0; i < playlist.length && count < 10; i++) {
                    if (playlist[i] != null) {
                        Cancion cancion = playlist[i];
                        System.out.print("Cancion: " + cancion.getNombre());
                        System.out.print(" - Artistas: ");
                        MyLinkedListImpl<Artista> artistas = cancion.getArtistas();
                        for (int j = 0; j < artistas.size(); j++) {
                            System.out.print(artistas.get(j).nombre);
                            if (j < artistas.size() - 1) {
                                System.out.print(", ");
                            }
                        }
                        System.out.println(" - Posición: " + cancion.getPosicion());
                        count++;
                    }
                }
            } else {
                System.out.println("No se encontraron datos para " + pais + " en la fecha " + fechaStr);
            }
        } else {
            System.out.println("No se encontraron datos para " + pais + " en la fecha " + fechaStr);
        }
    }

    public static void obtener5CancionesMasRepetidas(String fechaStr) throws ParseException, InformacionInvalida {

    }

}
