package uy.edu.um.entities;

import lombok.Getter;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;

import java.util.Date;

@Getter
public class CancionArtistaApariciones {
    String nombre;
    MyLinkedListImpl<Artista> artistas;
    int aparicionesCancion = 0;
    int aparicionesArtista = 0;
    Date fecha;

    public CancionArtistaApariciones(String nombre, MyLinkedListImpl<Artista> artistas, Date fecha) {
        this.nombre = nombre;
        this.artistas = new MyLinkedListImpl<>();;
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setArtistas(MyLinkedListImpl<Artista> artistas) {
        this.artistas = artistas;
    }

    public void setAparicionesCancion(int aparicionesCancion) {
        this.aparicionesCancion = aparicionesCancion;
    }
    public void setAparicionesArtista(int aparicionesArtista) {
        this.aparicionesArtista = aparicionesArtista;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
