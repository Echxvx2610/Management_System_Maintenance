package com.mantto.gestormanto_fx;

public class Actividad {
    // atributos
    int id_temporal;
    String parte;
    String tiempo;
    String nota;

    // constuctor
    public Actividad(int id_temporal,String parte, String tiempo, String nota){
        this.id_temporal = id_temporal;
        this.parte = parte;
        this.tiempo = tiempo;
        this.nota = nota;
    }
    //getter and setter
    public int getId(){
        return id_temporal;
    }
    public void setId(int id){
        this.id_temporal = id;
    }
    public String getParte(){
        return parte;
    }
    public void setParte(String parte){
        this.parte = parte;
    }
    public String getTiempo(){
        return tiempo;
    }
    public void setTiempo(String tiempo){
        this.tiempo = tiempo;
    }
    public String getNota(){
        return nota;
    }
    public void setNota(String nota){
        this.nota = nota;
    }

    public String toString(){
        return "\tParte: " + parte + "\n" +
                "\tTiempo: " + tiempo + "\n" +
                "\tNota: " + nota + "\n";
    }


}
