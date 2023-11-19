
import java.util.ArrayList;

public class Usuario {
    // metodo que
    // devuelva array, usuario 5 atributos=
    // array con 5 espacios
    // atributos
    private String nombre;
    private String apellidos;
    private String correo;
    private String usuario;
    private String contrasena;

    // constructor
    Usuario(String nombre, String apellidos, String correo, String usuario, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    // método getter y setter para 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // método getter y setter para 'apellidos'
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // método getter y setter para 'correo'
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // método getter y setter para 'usuario'
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // método getter y setter para 'contrasena'
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // método para crear un ArrayList con los atributos e imprimirlos en lugar de un
    // toString
    public ArrayList<String> datosUsuarioArrayList() {
        ArrayList<String> datosUsuario = new ArrayList<>();
        datosUsuario.add(nombre);
        datosUsuario.add(apellidos);
        datosUsuario.add(correo);
        datosUsuario.add(usuario);
        datosUsuario.add(contrasena);
        return datosUsuario;
    }
}