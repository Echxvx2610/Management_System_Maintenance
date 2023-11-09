public class Equipo {
    // atributos
    private String id;
    private String nombre;
    private String modelo;
    private String marca;
    private String serie;
    private String estado;
    private String descripcion;

    // constructor
    public Equipo(String id, String nombre, String modelo, String marca, String serie, String estado,
            String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.marca = marca;
        this.serie = serie;
        this.estado = estado;
        this.descripcion = descripcion;
    }
    // getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // toString
    @Override
    public String toString() {
        return "Informacion del equipo: \n" + "ID: " + id + "\n" + "Nombre: " + nombre + "\n" + "Modelo: "
                + modelo + "\n" + "Marca: " + marca + "\n" + "Serie: " + serie + "\n" + "Estado: " + estado
                + "\n" + "Descripción: " + descripcion;
    }
}
