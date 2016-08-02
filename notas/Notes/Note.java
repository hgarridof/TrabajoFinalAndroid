package com.hgf.android.notas.Notes;

/**
 * Created by hector on 8/6/16.
 */
public class Note {

    public int id;
    public String titulo;
    public String Contexto;
    public String fecha;
    public double latitud;
    public double longitud;

    public Note(String titulo, String contexto) {
        Contexto = contexto;
        this.titulo = titulo;
    }

    public Note(int id, String titulo, String contexto, String fecha) {
        this.id = id;
        this.titulo = titulo;
        Contexto = contexto;
        this.fecha = fecha;

    }

    public Note(int id, String titulo, String contexto, String fecha, double latitud, double longitud) {
        this.id = id;
        this.titulo = titulo;
        Contexto = contexto;
        this.fecha = fecha;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContexto() {
        return Contexto;
    }

    public void setContexto(String contexto) {
        Contexto = contexto;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
