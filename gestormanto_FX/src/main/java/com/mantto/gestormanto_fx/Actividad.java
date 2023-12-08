package com.mantto.gestormanto_fx;

public class Actividad {
    // atributos
    private int idTemporal;
    String parte;
    int tiempo;
    String nota;

    //constructor vacio
    public Actividad(){
        this.parte = "";
        this.tiempo = 0;
        this.nota = "";
    }


    // constuctor con parametros
    public Actividad(String parte, int tiempo, String nota){
        this.parte = parte;
        this.tiempo = tiempo;
        this.nota = nota;
    }
    //getter and setter
    public int getIdTemporal(){
        return idTemporal;
    }
    public void setIdTemporal(int id){
        this.idTemporal = id;
    }
    public String getParte(){
        return parte;
    }
    public void setParte(String parte){
        this.parte = parte;
    }
    public int getTiempo(){
        return tiempo;
    }
    public void setTiempo(int tiempo){
        this.tiempo = tiempo;
    }
    public String getNota(){
        return nota;
    }
    public void setNota(String nota){
        this.nota = nota;
    }

    public String toString(){
        return "\tActividad: " + parte + "\n" +
                "\tTiempo: " + tiempo + "\n" +
                "\tNota: " + nota + "\n";
    }


}
