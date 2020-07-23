package com.example.coronaapp.model;

import java.util.Date;

public class Situacion {
    private String uid;
    private int infectados;
    private int muertos;
    private String formattedDate;
    private Date date;

    public Situacion(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getInfectados() {
        return infectados;
    }

    public void setInfectados(int infectados) {
        this.infectados = infectados;
    }

    public int getMuertos() {
        return muertos;
    }

    public void setMuertos(int muertos) {
        this.muertos = muertos;
    }

    @Override
    public String toString() {
        return "Infectados: " + this.infectados + "\nMuertos: " + this.muertos + "\nFecha: " + this.formattedDate;
    }
}
