package com.example.coronaapp.model;

public class Recomendacion {
    private String uid;
    private String titulo;
    private String contenido;

    public Recomendacion(){
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString(){
        return "Titulo: "+this.titulo;
    }
}
