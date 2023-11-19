
import java.util.ArrayList;

public class Equipo {
    // atributos
    // private String id;
    private String nombre;
    private String modelo;
    private String marca;
    private String serie;
    private Boolean activo; // activo = true, inactivo = false
    private String lugar; // localización
    private String descripcion;

    // constructor con todosl os atributos
    Equipo(String nombre, String modelo, String marca, String serie, Boolean activo, String lugar, String descripcion) {
        this.nombre = nombre;
        this.modelo = modelo;
        this.marca = marca;
        this.serie = serie;
        this.activo = activo;
        this.lugar = lugar;
        this.descripcion = descripcion;
    }

    // método getter y setter para 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // método getter y setter para 'modelo'
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    // método getter y setter para 'marca'
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    // método getter y setter para 'serie'
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    // método getter y setter para 'estado'
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    // método getter y setter para 'lugar'
    public String getLugar() {
        return this.lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    // método getter y setter para 'descripcion'
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // método para crear un arrayList con los datos del equipo creado
    // reemplazando el toString
    public ArrayList<String> toArrayList() {
        ArrayList<String> datosEquipo = new ArrayList<>();
        datosEquipo.add(nombre);
        datosEquipo.add(modelo);
        datosEquipo.add(marca);
        datosEquipo.add(serie);
        datosEquipo.add(activo.toString()); // convertir bool a string
        datosEquipo.add(lugar);
        datosEquipo.add(descripcion);
        return datosEquipo;
    }
}
