
import java.util.ArrayList;

public class Consumible {
    // atributos de la clase
    private String nombre;
    private String tipoConsumible;
    private String proveedor;
    private double cantidad;
    private double precio;
    private String unidadMedida;

    // constructor con todos los atributos
    Consumible(String nombre, String tipoConsumible, String proveedor, double cantidad, double precio,
            String unidadMedida) {
        this.nombre = nombre;
        this.tipoConsumible = tipoConsumible;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
    }

    // getters y setters
    public String getNombre() {
        return nombre;
    }

    public void seanNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoConsumible() {
        return tipoConsumible;
    }

    public void setTipoConsumible(String tipoConsumible) {
        this.tipoConsumible = tipoConsumible;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    // método para crear un arrayList con los datos del equipo creado
    // reemplazando el toString
    public ArrayList<Object> toArrayList() {
        ArrayList<Object> datosConsumible = new ArrayList<>();
        datosConsumible.add(nombre);
        datosConsumible.add(tipoConsumible);
        datosConsumible.add(proveedor);
        datosConsumible.add(Double.valueOf(cantidad));
        datosConsumible.add(Double.valueOf(precio));
        datosConsumible.add(unidadMedida);
        return datosConsumible;
    }
}