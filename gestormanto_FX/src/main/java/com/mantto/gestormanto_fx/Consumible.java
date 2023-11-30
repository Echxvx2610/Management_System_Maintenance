package com.mantto.gestormanto_fx;

public class Consumible {
    // clase consumibles para registrar objetos en inventario
    private int idTemporal;
    String nombre;

    String proveedor;

    int cantidad;

    String unidad;

    double precio;

    String observaciones;

    // Constructor vacio
    public Consumible(){
        String nombre="";
        String proovedor="";
        int cantidad = 0;
        String unidad = "";
        double precio = 0;
        String observaciones = "";
    }

    // Constructor con parametros
    public Consumible(String nombre,String proveedor,int cantidad,String unidad,double precio,String observaciones){
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.precio = precio;
        this.observaciones = observaciones;
    }

    // getter and setters
    public int getIdTemporal(){
        return idTemporal;
    }
    public void setIdTemporal(int id){
        this.idTemporal = id;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getProveedor(){
        return proveedor;
    }
    public void setProveedor(String proveedor){
        this.proveedor = proveedor;
    }
    public int getCantidad(){
        return cantidad;
    }
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }
    public String getUnidad(){
        return unidad;
    }
    public void setUnidad(String unidad){
        this.unidad = unidad;
    }
    public double getPrecio(){
        return precio;
    }
    public void setPrecio(double precio){
        this.precio = precio;
    }
    public String getObservaciones(){
        return observaciones;
    }
    public void setObservaciones(String observaciones){
        this.observaciones = observaciones;
    }
    //retorna un arraylist de los atributos
    ///

    public String toString(){
        return "Nombre: " + nombre + "\n"
                + "Proveedor: " + proveedor + "\n"
                + "Cantidad: " + cantidad + "\n"
                + "Unidad: " + unidad + "\n"
                + "Precio: " + precio + "\n"
                + "Observaciones: " + observaciones + "\n";
    }


}
