package com.mantto.gestormanto_fx;

import java.util.ArrayList;

public class GenerarOrden {
    int idTemporal;
    /*
    private Usuario usuario;
    private Equipo equipo;
    private Plan plan;
    */

    // Atributos adicionales
    public String usuario;
    public String equipo;
    public String plan;
    public String observaciones;
    boolean realizado = false;

    // Constructores

    // Constructor vacio
    public GenerarOrden() {
        this.usuario = "";
        this.equipo = "";
        this.plan = "";
        this.observaciones = "";
        this.realizado = false;
    }
    // Constructor de String
    public GenerarOrden(String nombreUsuario, String nombreEquipo, String nombrePlan,String observaciones, boolean realizado) {
        this.usuario = nombreUsuario;
        this.equipo = nombreEquipo;
        this.plan = nombrePlan;
        this.observaciones = observaciones;
        this.realizado = realizado;
    }

    // Constructor de objetoc
    /*
    public GenerarOrden(Usuario usuario, Equipo equipo, Plan plan) {
        this.usuario = usuario;
        this.equipo = equipo;
        this.plan = plan;
    }
     */

    // getters and setters
    public int getIdTemporal() {
        return idTemporal;
    }

    public void setIdTemporal(int idTemporal) {
        this.idTemporal = idTemporal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    // Métodos para acceder y modificar los atributos

    public String toString() {
        return "Usuario: " + usuario + "\n" +
                "Equipo: " + equipo+ "\n" +
                "Plan: " + plan + "\n" +
                "Realizado: " + realizado + "\n";

    }
    /*
    // Método para imprimir detalles de la orden
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Orden:\n")
                .append("Usuario: ").append(usuario.getName()).append("\n")
                .append("Equipo: ").append(equipo.getNombre()).append("\n")
                .append("Plan: ").append(plan.getNombre()).append("\n")
                .append("Actividades:\n");

        for (Actividad act : plan.getActividades()) {
            result.append("  Parte: ").append(act.getParte()).append("\n")
                    .append("  Tiempo: ").append(act.getTiempo()).append("\n")
                    .append("  Nota: ").append(act.getNota()).append("\n\n");
        }

        return result.toString();
    }*/

}
