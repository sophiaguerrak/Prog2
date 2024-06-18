package uy.edu.um.entities;

import lombok.Getter;
import lombok.Setter;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;

import java.util.Date;

@Getter
public class CancionArtistaApariciones {
    String nombre;
    MyLinkedListImpl<Artista> artistas;
    @Setter
    int aparicionesCancion = 0;
    Date fecha;

    public CancionArtistaApariciones(String nombre, MyLinkedListImpl<Artista> artistas, Date fecha) {
        this.nombre = nombre;
        this.artistas = new MyLinkedListImpl<>();;
        this.fecha = fecha;
    }


}
