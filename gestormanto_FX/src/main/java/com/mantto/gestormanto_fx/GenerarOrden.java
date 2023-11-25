package com.mantto.gestormanto_fx;

import java.util.ArrayList;

public class GenerarOrden {
    private Usuario usuario;
    private Equipo equipo;
    private Plan plan;

    // Otros atributos y métodos específicos de la orden de trabajo...

    // Constructor
    public GenerarOrden(Usuario usuario, Equipo equipo, Plan plan) {
        this.usuario = usuario;
        this.equipo = equipo;
        this.plan = plan;
    }

    // Métodos para acceder y modificar los atributos

    // Método para imprimir detalles de la orden
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Orden:\n")
                .append("Usuario: ").append(usuario.getName()).append("\n")
                .append("Equipo: ").append(equipo.getName()).append("\n")
                .append("Plan: ").append(plan.getNombre()).append("\n")
                .append("Actividades:\n");

        for (Actividad act : plan.getActividades()) {
            result.append("  Parte: ").append(act.getParte()).append("\n")
                    .append("  Tiempo: ").append(act.getTiempo()).append("\n")
                    .append("  Nota: ").append(act.getNota()).append("\n\n");
        }

        return result.toString();
    }

}
