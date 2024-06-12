package uy.edu.um.entities;

import java.util.Date;

public class Top50 {
    String pais;
    Date fecha;
    Cancion[] playlist =  new Cancion[50];

    public Top50(String pais, Date fecha) {
        this.pais = pais;
        this.fecha = fecha;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Cancion[] getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Cancion[] playlist) {
        this.playlist = playlist;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
