package com.example.coronaapp.model;

public class Delivery {
    private String uid;
    private String nombre;
    private String descripcion;
    private String contacto;

    public Delivery(){
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    @Override
    public String toString (){
        return "Nombre: "+this.nombre+"\nContacto: "+this.contacto;
    }
}
