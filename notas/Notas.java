package com.hgf.android.notas;

/**
 * Created by hector on 8/6/16.
 */
public class Notas {

    public int id;
    public String titulo;
    public String Contexto;
    public String fecha;

    public Notas(String titulo, String contexto) {
        Contexto = contexto;
        this.titulo = titulo;
    }

    public Notas(int id, String titulo, String contexto, String fecha) {
        this.id = id;
        this.titulo = titulo;
        Contexto = contexto;
        this.fecha = fecha;

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
}
