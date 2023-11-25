package com.mantto.gestormanto_fx;

import java.util.ArrayList;

public class Equipo {
    // Atributos del Equipo
    public String name;
    public String modelo;
    public String marca;
    public String estado_equipo;
    public String localizacion;
    public String nota = "";


    // constructor por default
    public Equipo() {
        this.name = "";
        this.modelo = "";
        this.marca = "";
        this.estado_equipo = "";
        this.localizacion = "";
        this.nota = "";
    }

    // constructor con parametros
    public Equipo(String name, String modelo, String marca,String estado_equipo, String localizacion, String nota) {
        this.name = name;
        this.modelo = modelo;
        this.marca = marca;
        this.estado_equipo = estado_equipo;
        this.localizacion = localizacion;
        this.nota = nota;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstado_Equipo() {
        return estado_equipo;
    }

    public void setEstado_equipo(String estado_equipo) {
        this.estado_equipo = estado_equipo;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public ArrayList<String> toArrayList() {
        // Retorna un ArrayList con todos los atributos
        ArrayList<String> atributos = new ArrayList<>();
        atributos.add(this.name);
        atributos.add(this.modelo);
        atributos.add(this.marca);
        atributos.add(this.estado_equipo);
        atributos.add(this.localizacion);
        atributos.add(this.nota);
        return atributos;
    }

    public String toString() {
        return  "Nombre: " + name + '\n' +
                "Modelo: " + modelo + '\n' +
                "Marca: " + marca + '\n' +
                "Estado: " + estado_equipo + '\n' +
                "Localizacion: " + localizacion + '\n' +
                "Notas: " + nota + '\n';
    }

}