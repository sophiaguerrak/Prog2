package uy.edu.um.entities;

import lombok.Getter;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;

@Getter
public class CancionArtistaApariciones {
    String nombre;
    MyLinkedListImpl<Artista> artistas = new MyLinkedListImpl<>();
    int apariciones = 0;

    public CancionArtistaApariciones(String nombre, MyLinkedListImpl<Artista> artistas) {
        this.nombre = nombre;
        this.artistas = artistas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setArtistas(MyLinkedListImpl<Artista> artistas) {
        this.artistas = artistas;
    }

    public void setApariciones(int apariciones) {
        this.apariciones = apariciones;
    }

}
