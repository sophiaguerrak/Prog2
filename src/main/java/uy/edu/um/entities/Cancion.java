package uy.edu.um.entities;

import uy.edu.um.adt.linkedlist.MyLinkedListImpl;

public class Cancion {
    String nombre;
    MyLinkedListImpl<Artista> artistas = new MyLinkedListImpl<>();
    int posicion;
    float tempo;

    public Cancion(String nombre, MyLinkedListImpl<Artista> artistas, int posicion, float tempo) {
        this.nombre = nombre;
        this.artistas = artistas;
        this.posicion = posicion;
        this.tempo = tempo;
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

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }
}
