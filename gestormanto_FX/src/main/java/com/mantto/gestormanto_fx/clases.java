package com.mantto.gestormanto_fx;

import java.util.ArrayList;

public class clases {
    static private ArrayList<Actividad> actividades = new ArrayList<>();

    public static void main(String[] args) {
        /*

        //instanciamos un objeto de equipo y un objeto de usuario
        Equipo equipo = new Equipo("CP", "642", "FUJI", "0001", "Activo", "PCBA", "Equipo traido de Australia");
        Usuario usuario = new Usuario("Cristian", "Echevarria", "cristianechevarriamendoza@gmail.com", "admin", "admin");

        //generar un arraylist de actividades para un plan
        actividades.add(new Actividad("Turrent","10 min","Remover grasa quemada y agregar grasa nueva"));
        actividades.add(new Actividad("LM Rails","5 min","Remover grasa quemada y agregar grasa nueva"));
        actividades.add(new Actividad("Main XYZ","10 min","Remover tapa, engrasar ball screw"));

        Plan cp = new Plan("0001","CP642","Semanal",actividades,false);
        Plan horno = new Plan("0002","BTU Pyramid","Mensual",actividades,false);

        System.out.println("Fecha actual de plan CP: " + cp.getFechaActualFormateada());
        System.out.println("Fecha actual de plan Horno: " + horno.getFechaActualFormateada());
        cp.marcarActividadRealizada();
        horno.marcarActividadRealizada();
        System.out.println("Fecha proxima de plan CP: " + cp.getFechaProximaMantenimientoFormateada());
        System.out.println("Fecha proxima de plan CP: " + horno.getFechaProximaMantenimientoFormateada());

        */

        // ejercicio de prueba
        Equipo montapollas = new Equipo("el perron","RC5500","CROWN","Madreadon","Almacen","Montapollas electrico hombre chupas");
        Usuario lic = new Usuario("lic","malaface","lic_culiadorblackbeld@hotmail.com","sexmachine69","abc123","abc123");

        // crear lista de actividades para plan1
        actividades.add(new Actividad("Niveles de Aceite",5,"Revisar los niveles de aceite hidraulico,nivel de frenos"));
        actividades.add(new Actividad("Nivel de presion",5,"Revisar que los niveles de presion en neumaticos sean optimos"));

        Plan plan1 = new Plan("0002","RC5500","Diario",actividades,false);

        /*
       // generar orden de trabajo
       GenerarOrden orden_1 = new GenerarOrden(usuario,equipo,cp);
       System.out.println(orden_1);
        */

        // generar orden para montapollas
        GenerarOrden orden_2 = new GenerarOrden(lic,montapollas,plan1);
        System.out.println(orden_2);

        plan1.marcarActividadRealizada();
        System.out.println(plan1.getFechaProximaMantenimientoFormateada());

    }
}
