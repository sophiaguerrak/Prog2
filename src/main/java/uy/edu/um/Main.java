package uy.edu.um;
import uy.edu.um.exceptions.InformacionInvalida;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        String fechaStr;
        String fechaStr2;
        String opcion = "x";

        SpotifyMgr miSpotify = new SpotifyMgr();

        try {
            miSpotify.cargardatos();
            while (!opcion.equals("0")) {
                miSpotify.imprimirMenu();
                Scanner input = new Scanner(System.in);
                opcion = input.nextLine();
                switch (opcion) {
                    case "1":
                        System.out.println("Elija un pais: ");
                        String pais = input.nextLine();
                        System.out.println("Seleccione la fecha: (yyyy-MM-dd) ");
                        fechaStr = input.nextLine();
                        miSpotify.obtenerTop10Canciones(pais, fechaStr);
                        break;

                    case "2":
                        System.out.println("Seleccione la fecha: (yyyy-MM-dd) ");
                        fechaStr = input.nextLine();
                        miSpotify.obtener5CancionesMasRepetidas(fechaStr);
                        break;

                    case "3":
                        System.out.println("Seleccione la fecha de inicio: (yyyy-MM-dd) ");
                        fechaStr = input.nextLine();
                        System.out.println("Seleccione la fecha de fin: (yyyy-MM-dd) ");
                        fechaStr2 = input.nextLine();
                        miSpotify.obtenerTop7Artistas(fechaStr, fechaStr2);
                        break;

                    case "4":
                        System.out.println("Seleccione un artista: ");
                        String artista = input.nextLine();
                        System.out.println("Seleccione la fecha: (yyyy-MM-dd) ");
                        fechaStr = input.nextLine();
                        miSpotify.obtenerAparicionesArtista(artista, fechaStr);
                        break;

                    case "5":
                        System.out.println("Seleccione un tempo inicial: ");
                        String tempo1 = input.nextLine();
                        System.out.println("Seleccione un tempo final: ");
                        String tempo2 = input.nextLine();
                        System.out.println("Seleccione la fecha de inicio: (yyyy-MM-dd) ");
                        fechaStr = input.nextLine();
                        System.out.println("Seleccione la fecha de fin: (yyyy-MM-dd) ");
                        fechaStr2 = input.nextLine();
                        miSpotify.obtenerCancionesTempoEspecifico(tempo1, tempo2, fechaStr, fechaStr2);
                        break;

                    default:
                        if (!opcion.equals("0")) {
                            System.out.println("No hay mas opciones");
                        }
                }
                System.out.println("\n");
            }
        } catch (FileNotFoundException | ParseException | InformacionInvalida e) {
            e.printStackTrace();
        }
    }
}
