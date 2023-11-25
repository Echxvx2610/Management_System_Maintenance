package com.mantto.gestormanto_fx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Plan {
    // clase plan mantenimiento
    // atributos
    String id_plan;
    String nombre;
    String frecuencia;
    ArrayList<Actividad> actividad;
    boolean realizado = false;

    private Calendar fechaActual;
    private Calendar fechaProximaMantenimiento;
    public Calendar calcularFechaProximaMantenimiento;
    // constructor
    public Plan(String id_plan, String nombre, String frecuencia, ArrayList<Actividad> actividad,boolean realizado){
        this.id_plan = id_plan;
        this.nombre = nombre;
        this.frecuencia = frecuencia;
        this.actividad  = actividad;
        this.realizado = realizado;
        this.fechaActual = new GregorianCalendar();
        calcularFechaProximaMantenimiento();
        //this.fechaProximaMantenimiento = calcularFechaProximaMantenimiento();
    }

    //getter and setter
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFrecuencia(){
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
    public ArrayList<Actividad> getActividades() {
        return actividad;
    }

    public boolean isRealizado(){
        return realizado;
    }
    public void setRealizado(boolean realizado){
        this.realizado = realizado;
    }

    public String getFechaActualFormateada() {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        return formato.format(fechaActual.getTime());
    }


    public void marcarActividadRealizada() {
        this.realizado = true;
        if (realizado) {
            // Realizar acciones adicionales según sea necesario
            // Por ejemplo, actualizar la fecha próxima de mantenimiento
            calcularFechaProximaMantenimiento();
        }
    }
    // Método para calcular la fecha próxima de mantenimiento
    private void calcularFechaProximaMantenimiento() {
        if (fechaProximaMantenimiento == null) {
            fechaProximaMantenimiento = (Calendar) fechaActual.clone();
        }

        int diasFrecuencia = obtenerDiasFrecuencia();

        if (realizado) {
            // Si ya se realizó una actividad, actualizar la fecha
            fechaProximaMantenimiento.add(Calendar.DAY_OF_MONTH, diasFrecuencia);
        }
    }
    //Método para obtener la frecuencia en días
    private int obtenerDiasFrecuencia() {
        switch (frecuencia.toLowerCase()) {
            case "diario":
                return 1;
            case "semanal":
                return 7;
            case "mensual":
                return 30; // Esto es solo un ejemplo, ajusta según tus necesidades
            default:
                return 0; // Valor predeterminado o manejo de casos no reconocidos
        }
    }

    // Método para obtener la fecha próxima de mantenimiento formateada
    public String getFechaProximaMantenimientoFormateada() {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        return formato.format(fechaProximaMantenimiento.getTime());
    }



    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Plan:\n")
                .append("nombre: ").append(nombre).append("\n")
                .append("frecuencia: ").append(frecuencia).append("\n")
                .append("Actividades: \n");

        for (Actividad act : actividad) {
            result.append(act.toString()).append("\n");
        }

        return result.toString();
    }
}
