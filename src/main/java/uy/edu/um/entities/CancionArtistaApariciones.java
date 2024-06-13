package uy.edu.um.entities;

import uy.edu.um.adt.linkedlist.MyLinkedListImpl;

public class CancionArtistaApariciones {
    String nombre;
    MyLinkedListImpl<Artista> artistas = new MyLinkedListImpl<>();
    int apariciones = 1;

    public CancionArtistaApariciones(String nombre, MyLinkedListImpl<Artista> artistas) {
        this.nombre = nombre;
        this.artistas = artistas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MyLinkedListImpl<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(MyLinkedListImpl<Artista> artistas) {
        this.artistas = artistas;
    }

    public int getApariciones() {
        return apariciones;
    }

    public void setApariciones(int apariciones) {
        this.apariciones = apariciones;
    }

}
