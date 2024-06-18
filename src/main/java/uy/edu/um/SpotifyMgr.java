package uy.edu.um;

import uy.edu.um.adt.hash.HashImpl;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.entities.Artista;
import uy.edu.um.entities.Cancion;
import uy.edu.um.entities.CancionArtistaApariciones;
import uy.edu.um.entities.Top50;
import uy.edu.um.exceptions.InformacionInvalida;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SpotifyMgr implements SpotifyMgt {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String MYPATH = "/Users/sophiaguerra/Desktop/universal_top_spotify_songs.csv";

    private static HashImpl<Date, HashImpl<String, Top50>> top50Map = new HashImpl<>(100);
    private static MyLinkedListImpl<String> paises = new MyLinkedListImpl<>();
    private static HashImpl<String, Artista> artistasTodos = new HashImpl<>(100);

    public void imprimirMenu() throws InformacionInvalida {
        System.out.println("BIENVENIDO AL SISTEMA DE CONSULTAS DE DATOS DE SPOTIFY");
        System.out.println("1 - TOP 10 CANCIONES (segun pais y fecha)");
        System.out.println("2 - TOP 5 CANCIONES en TOPs 50 (segun fecha)");
        System.out.println("3 - TOP 7 ARTISTAS en TOPs 50 (para un rango de fechas)");
        System.out.println("4 - APARICIONES DE ARTISTA ESPECIFICO (en una fecha)");
        System.out.println("5 - CANTIDAD DE CANCIONES para un rango de tempo y fechas especifico");

        System.out.println("Digite el numero de la consulta que quiere realizar: ");

    }

    public void cargardatos() throws FileNotFoundException, ParseException, InformacionInvalida {
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

            for (String artistName : artistasArray) {
                artistName = artistName.trim();
                Artista artista = artistasTodos.search(artistName);
                if (artista == null) {
                    artista = new Artista(artistName);
                    artistasTodos.insert(artistName, artista);
                }
                artistasList.add(artista);
            }
            cancion.setArtistas(artistasList);

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

    public void obtenerTop10Canciones(String pais, String fechaStr) throws ParseException, InformacionInvalida {
        System.out.println("LOADING...\n");

        long startTime = System.currentTimeMillis();

        Date fecha = DATE_FORMAT.parse(fechaStr);
        HashImpl<String, Top50> top50Todos = top50Map.search(fecha);
        if (top50Todos != null) {
            Top50 top50 = top50Todos.search(pais);
            if (top50 != null) {
                Cancion[] playlist = top50.getPlaylist();
                System.out.println("Top 10 canciones para " + pais + " en la fecha " + fechaStr + ":");
                for (int i = 0; i < 10; i++) {
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

                }

            } else {
                System.out.println("No se encontraron datos para " + pais + " en la fecha " + fechaStr);
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("La función tomó " + duration + " milisegundos en ejecutarse.");
    }


    public void obtener5CancionesMasRepetidas(String fechaStr) throws ParseException, InformacionInvalida {

        long startTime = System.currentTimeMillis();

        System.out.println("LOADING...\n");
        Date fecha = DATE_FORMAT.parse(fechaStr);
        MyLinkedListImpl<CancionArtistaApariciones> cancionesFecha = new MyLinkedListImpl<>();

        CancionArtistaApariciones[] top5 = new CancionArtistaApariciones[5];
        for (int j = 0; j < 5; j++) {
            top5[j] = new CancionArtistaApariciones(null, null, fecha);
        }

        for (int i = 0; i < paises.size(); i++) {
            HashImpl<String, Top50> paisTop50 = top50Map.search(fecha);
            Top50 top50 = paisTop50.search(paises.get(i));
            if (top50 != null) {
                for (Cancion cancion : top50.getPlaylist()) {
                    if (cancion != null) {
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
                            CancionArtistaApariciones temp = new CancionArtistaApariciones(cancion.getNombre(), cancion.getArtistas(), fecha);
                            temp.setAparicionesCancion(1);
                            cancionesFecha.add(temp);
                        }
                    }
                }
            }
        }
        List<CancionArtistaApariciones> listaCanciones = new ArrayList<>();
        for (int i = 0; i < cancionesFecha.size(); i++) {
            listaCanciones.add(cancionesFecha.get(i));
        }
        listaCanciones.sort((a, b) -> b.getAparicionesCancion() - a.getAparicionesCancion()); //ordena por apariciones

        System.out.println("Las canciones que mas aparecen en un Top 50 para la fecha seleccionada son: \n");
        for (int i = 0; i < Math.min(5, listaCanciones.size()); i++) {
            CancionArtistaApariciones cancion = listaCanciones.get(i);
            System.out.println((i + 1) + " - " + cancion.getNombre() + " - " + " con " + cancion.getAparicionesCancion() + " apariciones");
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("La funcion tomo " + duration + " milisegundos en ejecutarse.");
    }

    public void obtenerTop7Artistas(String fechaInicio, String fechaFin) throws ParseException, InformacionInvalida {
        System.out.println("LOADING...\n");
        long startTime = System.currentTimeMillis();

        List<Date> fechas = obtenerDifFechas(fechaInicio, fechaFin);

        HashMap<String, Integer> contadorArtistas = new HashMap<>();

        for (Date fecha : fechas) {
            HashImpl<String, Top50> top50Todos = top50Map.search(fecha);
            if (top50Todos != null) {
                for (int i = 0; i < paises.size(); i++) {
                    String pais = paises.get(i);
                    Top50 top50 = top50Todos.search(pais);
                    if (top50 != null) {
                        for (Cancion cancion : top50.getPlaylist()) {
                            if (cancion != null) {
                                MyLinkedListImpl<Artista> artistas = cancion.getArtistas();
                                for (int j = 0; j < artistas.size(); j++) {
                                    String nombreArtista = artistas.get(j).getNombre();
                                    contadorArtistas.put(nombreArtista, contadorArtistas.getOrDefault(nombreArtista, 0) + 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> listaArtistas = new ArrayList<>(contadorArtistas.entrySet());
        listaArtistas.sort((a, b) -> b.getValue().compareTo(a.getValue())); // ordena por apariciones

        // Mostrar los top 7 artistas
        System.out.println("Top 7 artistas con mas apariciones en Top 50 para el rango de fechas " + fechaInicio + " a " + fechaFin + ":");
        for (int i = 0; i < Math.min(7, listaArtistas.size()); i++) {
            Map.Entry<String, Integer> entry = listaArtistas.get(i);
            System.out.println((i + 1) + " - " + entry.getKey() + " con " + entry.getValue() + " apariciones");
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("La funcion tomo " + duration + " milisegundos en ejecutarse.");

    }

    public List<Date> obtenerDifFechas(String fechaIn, String fechaFin) throws ParseException {

        Date startDate = DATE_FORMAT.parse(fechaIn);
        Date endDate = DATE_FORMAT.parse(fechaFin);

        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }

    public void obtenerAparicionesArtista(String nombreArtista, String fechaStr) throws ParseException, InformacionInvalida {
        long startTime = System.currentTimeMillis();

        System.out.println("LOADING...\n");
        Date fecha = DATE_FORMAT.parse(fechaStr);
        HashImpl<String, Top50> top50Todos = top50Map.search(fecha);
        int contador = 0;

        for (int i = 0; i < paises.size(); i++) {
            Top50 top50 = top50Todos.search(paises.get(i));
            if (top50 != null) {
                for (Cancion cancion : top50.getPlaylist()) {
                    for (int j = 0; j < cancion.getArtistas().size(); j++) {
                        String nombre = cancion.getArtistas().get(j).getNombre();
                        if (nombre.equals(nombreArtista)) {
                            contador++;
                        }
                    }
                }
            }
        }
        System.out.println("El/La artista " + nombreArtista + " tiene " + contador + " apariciones en la fecha " + fechaStr);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("La funcion tomo " + duration + " milisegundos en ejecutarse.");
    }

    public void obtenerCancionesTempoEspecifico(String tempo1, String tempo2, String fechaInicioStr, String fechaFinStr) throws ParseException, InformacionInvalida {
        long startTime = System.currentTimeMillis();

        System.out.println("LOADING...\n");
        List<Date> fechas = obtenerDifFechas(fechaInicioStr, fechaFinStr);
        float tempoInicial = Float.parseFloat(tempo1);
        float tempoFinal = Float.parseFloat(tempo2);

        MyLinkedListImpl<String> cancionesTempo = new MyLinkedListImpl<>();
        for (Date fecha : fechas) {
            HashImpl<String, Top50> top50Todos = top50Map.search(fecha);
            for (int i = 0; i < paises.size(); i++) {
                String pais = paises.get(i);
                Top50 top50 = top50Todos.search(pais);
                if (top50 != null) {
                    for (Cancion cancion : top50.getPlaylist()) {
                        if (cancion != null) {
                            if (!esta(cancionesTempo, cancion.getNombre()))
                                if (cancion.getTempo() >= tempoInicial && cancion.getTempo() <= tempoFinal) {
                                    cancionesTempo.add(cancion.getNombre());
                                }
                        }
                    }
                }
            }
        }
        System.out.println("Hay " + cancionesTempo.size() + " canciones en el intervalo de tempos para el rango de fechas especificado.");

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("La funcion tomo " + duration + " milisegundos en ejecutarse.");
    }

    public static boolean esta(MyLinkedListImpl lista, String nombre) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).equals(nombre)) {
                return true;
            }
        }
        return false;
    }

}
