package com.mantto.gestormanto_fx;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Plan {
    // clase plan mantenimiento
    // atributos
    int actividadId;  // atributos temporales
    int idTemporalPlan; // atributos temporales
    String nombre;
    String frecuencia;
    ArrayList<Actividad> actividad;
    boolean realizado = false;

    private Calendar fechaActual;
    private Calendar fechaProximaMantenimiento;
    public Calendar calcularFechaProximaMantenimiento;


    // constructor vacio
    public Plan(){
        String nombre = "";
        String frecuencia = "";
        ArrayList<Actividad> actividad;
        boolean realizado = false;
    }

    // constructor parametros
    public Plan(String nombre, String frecuencia, ArrayList<Actividad> actividad,boolean realizado){
        this.nombre = nombre;
        this.frecuencia = frecuencia;
        this.actividad = (actividad != null) ? actividad : new ArrayList<>(); // Inicializa el ArrayList
        //this.actividad  = actividad;
        this.realizado = realizado;
        this.fechaActual = new GregorianCalendar();
        calcularFechaProximaMantenimiento();
        //this.fechaProximaMantenimiento = calcularFechaProximaMantenimiento();
    }

    public Plan(int id, String nombre) {
        this.idTemporalPlan = id;
        this.nombre = nombre;
    }

    //getter and setter
    public int getActividadId() {
        return actividadId;
    }
    public void setActividadId(int actividadId) {
        this.actividadId = actividadId;
    }
    public int getIdTemporalPlan(){
        return idTemporalPlan;
    }
    public void setIdTemporal(int idTemporalPlan){
        this.idTemporalPlan = idTemporalPlan;
    }
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
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fechaActual.getTime());
    }
    public void setFechaActual(Timestamp fechaActual) {
        this.fechaActual = new GregorianCalendar();
        this.fechaActual.setTimeInMillis(fechaActual.getTime());
    }

    public void setFechaProximaMantenimiento(Timestamp fechaProximaMantenimiento) {
        this.fechaProximaMantenimiento = new GregorianCalendar();
        this.fechaProximaMantenimiento.setTimeInMillis(fechaProximaMantenimiento.getTime());
    }

    public void marcarActividadRealizada() {
        this.realizado = true;
        calcularFechaProximaMantenimiento();
        }

    private void calcularFechaProximaMantenimiento() {
        if (fechaProximaMantenimiento == null) {
            fechaProximaMantenimiento = (Calendar) fechaActual.clone();
        }

        int diasFrecuencia = obtenerDiasFrecuencia();

        // Actualiza la fecha próxima de mantenimiento según la frecuencia
        fechaProximaMantenimiento.add(Calendar.DAY_OF_MONTH, diasFrecuencia);
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
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fechaProximaMantenimiento.getTime());
    }

    public void actualizarFechaManto() {
        // Verifica que el plan haya sido realizado antes de actualizar la fecha
        if (realizado) {
            calcularFechaProximaMantenimiento();
        }
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

    public void setActividad(Actividad actividadAsociada) {
        actividad.add(actividadAsociada);
    }

    public String getFechaActual() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return fechaActual != null ? formato.format(fechaActual.getTime()) : "";
    }

    public String getFechaProximaMantenimiento() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return fechaProximaMantenimiento != null ? formato.format(fechaProximaMantenimiento.getTime()) : "";
    }
}
