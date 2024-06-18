package uy.edu.um;

import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.exceptions.InformacionInvalida;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface SpotifyMgt {
    void imprimirMenu()
            throws InformacionInvalida;

    void cargardatos()
            throws FileNotFoundException, ParseException, InformacionInvalida;

    void obtenerTop10Canciones(String pais, String fechaStr)
            throws ParseException, InformacionInvalida;

    void obtener5CancionesMasRepetidas(String fechaStr)
            throws ParseException, InformacionInvalida;

    void obtenerTop7Artistas(String fechaInicio, String fechaFin)
            throws ParseException, InformacionInvalida;

    void obtenerAparicionesArtista(String nombreArtista, String fechaStr)
            throws ParseException, InformacionInvalida;

    void obtenerCancionesTempoEspecifico(String tempo1, String tempo2, String fechaInicioStr,String fechaFinStr)
            throws ParseException, InformacionInvalida;

}
