
public class PruebasClases {
    public static void main(String[] args) {
        // // creamos un objeto de la clase Usuario
        // Usuario usuario = new Usuario("Oscar", "teran", "elmascapito@gmail.com",
        // "bebexito_Hemoxito", "baby_looko");

        // // recorrer los atributos del usuario
        // for (String atributo : usuario.datosUsuarioArrayList()) {
        // System.out.println(atributo);
        // }

        // // creamos un objeto de la clase Usuario
        // Equipo equipo = new Equipo("Desarmador neumatico", "347-418u", "Deprag",
        // "22211440", true, "Final",
        // "Mamberoiiiii");

        // // recorrer los atributos del equipo
        // for (String atributo : equipo.toArrayList()) {
        // System.out.println(atributo);
        // }
        Consumible consumible = new Consumible("Toallas femeninas", "Limpieza", "Fierros de México S. A. de C. V.", 3,
                157.6543, "Paquete");
        for (Object atributo : consumible.toArrayList()) {
            System.out.println(atributo);
        }
    }
}