package uy.edu.um;

import uy.edu.um.adt.hash.HashImpl;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.entities.Artista;
import uy.edu.um.entities.Cancion;
import uy.edu.um.entities.CancionArtistaApariciones;
import uy.edu.um.entities.Top50;
import uy.edu.um.exceptions.InformacionInvalida;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static uy.edu.um.SpotifyMgr.*;


public class Main {


    public static void main(String[] args) {
        String fechaStr;
        String fechaStr2;
        String anio;
        String anio2;
        String mes;
        String mes2;
        String dia;
        String dia2;
        String opcion = "x";

        SpotifyMgr miSpotiy = new SpotifyMgr();

        try {
            miSpotiy.cargardatos();
            while (!opcion.equals("0")) {
                miSpotiy.imprimirMenu();
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
                        miSpotiy.obtenerTop10Canciones(pais, fechaStr);
                        break;
                    case "2":
                        System.out.println("Seleccione la fecha. ANIO: ");
                        anio = input.nextLine() + "-";
                        System.out.println("MES: ");
                        mes = input.nextLine() + "-";
                        System.out.println("DIA: ");
                        dia = input.nextLine();
                        fechaStr = anio + mes + dia;
                        miSpotiy.obtener5CancionesMasRepetidas(fechaStr);
                        break;
                    case "3":
                        System.out.println("Seleccione la fecha de inicio. ANIO: ");
                        anio = input.nextLine() + "-";
                        System.out.println("MES: ");
                        mes = input.nextLine() + "-";
                        System.out.println("DIA: ");
                        dia = input.nextLine();
                        fechaStr = anio + mes + dia;
                        System.out.println("Seleccione la fecha de fin. ANIO: ");
                        anio2 = input.nextLine() + "-";
                        System.out.println("MES: ");
                        mes2 = input.nextLine() + "-";
                        System.out.println("DIA: ");
                        dia2 = input.nextLine();
                        fechaStr2 = anio2 + mes2 + dia2;
                        miSpotiy.obtenerTop7Artistas(fechaStr, fechaStr2);
                        break;

                    case "4":
                        System.out.println("Seleccione un artista. ARTISTA: ");
                        String artista = input.nextLine();
                        System.out.println("Seleccione una fecha: ANIO: ");
                        anio = input.nextLine() + "-";
                        System.out.println("MES: ");
                        mes = input.nextLine() + "-";
                        System.out.println("DIA: ");
                        dia = input.nextLine();
                        fechaStr = anio + mes + dia;
                        miSpotiy.obtenerAparicionesArtista(artista,fechaStr);
                        break;
                    case "5":
                        System.out.println("Seleccione un tempo inicial. Tempo: ");
                        String tempo1 = input.nextLine();
                        System.out.println("Seleccione un tempo final. Tempo: ");
                        String tempo2 = input.nextLine();
                        System.out.println("Seleccione una fecha inicial: ANIO: ");
                        String anioInicio = input.nextLine() + "-";
                        System.out.println("MES: ");
                        String mesInicio = input.nextLine() + "-";
                        System.out.println("DIA: ");
                        String diaInicio = input.nextLine();
                        String fechaInicioStr = anioInicio + mesInicio + diaInicio;
                        System.out.println("Seleccione una fecha final: ANIO: ");
                        String anioFin = input.nextLine() + "-";
                        System.out.println("MES: ");
                        String mesFin = input.nextLine() + "-";
                        System.out.println("DIA: ");
                        String diaFin = input.nextLine();
                        String fechaFinStr = anioFin + mesFin + diaFin;
                        miSpotiy.obtenerCancionesTempoEspecifico(tempo1,tempo2,fechaInicioStr,fechaFinStr);
                    default:
                        System.out.println("No hay mas opciones");
                }

                System.out.println("\n");
            }
        } catch (FileNotFoundException | ParseException | InformacionInvalida e) {
            e.printStackTrace();
        }
    }
}
