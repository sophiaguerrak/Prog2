package uy.edu.um;

import uy.edu.um.entities.Artista;
import uy.edu.um.entities.Cancion;
import uy.edu.um.entities.CancionArtistaApariciones;
import uy.edu.um.entities.Top50;
import uy.edu.um.adt.hash.HashImpl;
import uy.edu.um.exceptions.InformacionInvalida;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Main {

    private static final String MYPATH = "/Users/sophiaguerra/Desktop/universal_top_spotify_songs.csv";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static HashImpl<Date, HashImpl<String, Top50>> top50Map = new HashImpl<>(100);
    private static MyLinkedListImpl<String> paises = new MyLinkedListImpl<>();
    private static HashImpl<String, Artista> artistasTodos = new HashImpl<>(100);
    static HashImpl<String, CancionArtistaApariciones> aparicionesCancion = new HashImpl<>(100);
    static MyLinkedListImpl<CancionArtistaApariciones> cancionArtistaAparicionesTodos = new MyLinkedListImpl();

    public static void main(String[] args) {
        String fechaStr;
        String anio;
        String mes;
        String dia;
        String opcion = "x";

        try {
            cargardatos();
            while (!opcion.equals("0")) {
                imprimirMenu();
                Scanner input = new Scanner(System.in);
                opcion = input.nextLine();
                switch (opcion) {
                    case "1":
                        System.out.println("Elija un pais: ");
                        String pais = input.nextLine();
                        System.out.println("Seleccione la fecha - ANIO: ");
                        anio = input.nextLine() + "-";
                        System.out.println("MES: ");
                        mes = input.nextLine() + "-";
                        System.out.println("DIA: ");
                        dia = input.nextLine();
                        fechaStr = anio + mes + dia;
                        obtenerTop10Canciones(pais, fechaStr);
                        break;
                    case "2":
                        System.out.println("Seleccione la fecha. ANIO: ");
                        anio = input.nextLine() + "-";
                        System.out.println("MES: ");
                        mes = input.nextLine() + "-";
                        System.out.println("DIA: ");
                        dia = input.nextLine();
                        fechaStr = anio + mes + dia;
                        obtener5CancionesMasRepetidas(fechaStr);
                        break;
                    case "4":
                        System.out.println("Seleccione un artista. ARTISTA: ");
                        String artista = input.nextLine() + "-";
                        System.out.println("Seleccione una fecha: ANIO: ");
                        anio = input.nextLine() + "-";
                        System.out.println("MES: ");
                        mes = input.nextLine() + "-";
                        System.out.println("DIA: ");
                        dia = input.nextLine();
                        fechaStr = anio + mes + dia;
                        //obtenerAparicionesArtista(artista,fechaStr);
                        break;
                    default:
                        System.out.println("No hay mas opciones");
                }

                System.out.println("\n");
            }
        } catch (FileNotFoundException | ParseException | InformacionInvalida e) {
            e.printStackTrace();
        }
    }


    public static void imprimirMenu() throws InformacionInvalida {
        System.out.println("BIENVENIDO AL SISTEMA DE CONSULTAS DE DATOS DE SPOTIFY");
        System.out.println("1 - TOP 10 CANCIONES (segun pais y fecha)");
        System.out.println("2 - TOP 5 CANCIONES en TOPs 50 (segun fecha)");
        System.out.println("3 - TOP 7 ARTISTAS en TOPs 50 (para un rango de fechas)");
        System.out.println("4 - APARICIONES DE ARTISTA ESPECIFICO (en una fecha)");
        System.out.println("5 - CANTIDAD DE CANCIONES para un rango de tempo y fechas especifico");

        System.out.println("Digite el numero de la consulta que quiere realizar: ");

    }

    public static void cargardatos() throws FileNotFoundException, ParseException, InformacionInvalida {
        File file = new File(MYPATH);
        Scanner sc = new Scanner(file);

        // Se saltea la primer linea
        if (sc.hasNext()) {
            sc.nextLine();
        }

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
            if (!paises.contains(pais))
                paises.add(pais);
            String fechaStr = data[6].replaceAll("\"", "").trim();
            Date fecha = DATE_FORMAT.parse(fechaStr);

            String[] artistasArray = artistaNombre.split(",");
            MyLinkedListImpl<Artista> artistasList = new MyLinkedListImpl<>();
            Cancion cancion = new Cancion(nombre, artistasList, posicion, tempo);
//            CancionArtistaApariciones cancionArtistaApariciones = new CancionArtistaApariciones(nombre,artistasList,fecha);
//            cancionArtistaAparicionesTodos.add(cancionArtistaApariciones);


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
            HashImpl<String, Top50> top50Todos = top50Map.search(fecha);
            if (top50Todos == null) {
                top50Todos = new HashImpl<>(100);
                top50Map.insert(fecha, top50Todos);
            }

            // busca el top50 correspondiente al pais, lo crea si no existe
            Top50 top50Pais = top50Todos.search(pais);
            if (top50Pais == null) {
                top50Pais = new Top50(pais, fecha);
                top50Todos.insert(pais, top50Pais);
            }

            // agrega la canción al Top50 que le corresponde
            for (int i = 0; i < top50Pais.getPlaylist().length; i++) {
                if (top50Pais.getPlaylist()[i] == null) {
                    top50Pais.getPlaylist()[i] = cancion;
                    break;
                }
            }
        }
        sc.close();
    }

    public static void obtenerTop10Canciones(String pais, String fechaStr) throws ParseException, InformacionInvalida {
        Date fecha = DATE_FORMAT.parse(fechaStr);
        HashImpl<String, Top50> top50Todos = top50Map.search(fecha);
        if (top50Todos != null) {
            Top50 top50 = top50Todos.search(pais);
            if (top50 != null) {
                Cancion[] playlist = top50.getPlaylist();
                int count = 0;
                System.out.println("Top 10 canciones para " + pais + " en la fecha " + fechaStr + ":");
                while (count < 11) {
                    for (int i = 0; i < 10; i++){
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
            }
            else{
                System.out.println("No se encontraron datos para " + pais + " en la fecha " + fechaStr);
                }
            } else {
                System.out.println("No se encontraron datos para " + pais + " en la fecha " + fechaStr);
            }
        }

        public static void obtener5CancionesMasRepetidas (String fechaStr) throws ParseException, InformacionInvalida {
            System.out.println("LOADING...\n");
            Date fecha = DATE_FORMAT.parse(fechaStr);
            MyLinkedListImpl<CancionArtistaApariciones> cancionesFecha = new MyLinkedListImpl<>();

            CancionArtistaApariciones[] top5 = new CancionArtistaApariciones[5];
            for (int j = 0 ; j < 5 ; j++){
                top5[j] = new CancionArtistaApariciones(null, null,fecha);
            }

            for (int i = 0; i < paises.size(); i++) {
                HashImpl<String, Top50> paisTop50 = top50Map.search(fecha);
                Top50 top50 = paisTop50.search(paises.get(i));
                for (Cancion cancion : top50.getPlaylist()) {
                    boolean repetido = false;
                    for (int j = 0; j < cancionesFecha.size(); j++) {
                        if (cancionesFecha.get(j).getNombre().equals(cancion.getNombre())) {
                            int apariciones = cancionesFecha.get(j).getAparicionesCancion();
                            cancionesFecha.get(j).setAparicionesCancion(apariciones + 1);
                            repetido = true;
                            break;
                        }
                    }
                    if (!repetido) {
                        CancionArtistaApariciones temp = new CancionArtistaApariciones(cancion.getNombre(), cancion.getArtistas(),fecha);
                        temp.setAparicionesCancion(1);
                        cancionesFecha.add(temp);
                    }
                }
            }

            for (int k = 0; k < cancionesFecha.size(); k++) {
                if (top5[0].getAparicionesCancion() < cancionesFecha.get(k).getAparicionesCancion()) {
                    top5[4] = top5[3];
                    top5[3] = top5[2];
                    top5[2] = top5[1];
                    top5[1] = top5[0];
                    top5[0] = cancionesFecha.get(k);
                } else if ((top5[1].getAparicionesCancion() < cancionesFecha.get(k).getAparicionesCancion()) && (top5[0].getAparicionesCancion() > cancionesFecha.get(k).getAparicionesCancion())) {
                    top5[4] = top5[3];
                    top5[3] = top5[2];
                    top5[2] = top5[1];
                    top5[1] = cancionesFecha.get(k);
                } else if ((top5[2].getAparicionesCancion() < cancionesFecha.get(k).getAparicionesCancion()) && (top5[1].getAparicionesCancion() > cancionesFecha.get(k).getAparicionesCancion())) {
                    top5[4] = top5[3];
                    top5[3] = top5[2];
                    top5[2] = cancionesFecha.get(k);
                } else if ((top5[3].getAparicionesCancion() < cancionesFecha.get(k).getAparicionesCancion()) && (top5[2].getAparicionesCancion() > cancionesFecha.get(k).getAparicionesCancion())) {
                    top5[4] = top5[3];
                    top5[3] = cancionesFecha.get(k);
                } else if ((top5[4].getAparicionesCancion() < cancionesFecha.get(k).getAparicionesCancion()) && (top5[3].getAparicionesCancion() > cancionesFecha.get(k).getAparicionesCancion())) {
                    top5[4] = cancionesFecha.get(k);
                }
            }
            System.out.println("Las canciones que mas aparecen en un Top 50 para la fecha seleccionada son: \n");
            for(int l = 0 ; l < 5 ; l++){
                System.out.println((l+1) + " - " + top5[l].getNombre() + " - " + " con " + top5[l].getAparicionesCancion() + " apariciones");
            }
        }



    }
