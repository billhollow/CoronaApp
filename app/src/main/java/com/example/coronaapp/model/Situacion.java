package com.example.coronaapp.model;

import java.util.Date;

public class Situacion {
    private String uid;
    private double infectados;
    private double muertos;
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

    public double getInfectados() {
        return infectados;
    }

    public void setInfectados(double infectados) {
        this.infectados = infectados;
    }

    public double getMuertos() {
        return muertos;
    }

    public void setMuertos(double muertos) {
        this.muertos = muertos;
    }

    @Override
    public String toString() {
        return "Infectados: " + this.infectados + "\nMuertos: " + this.muertos + "\nFecha: " + this.formattedDate;
    }
}
