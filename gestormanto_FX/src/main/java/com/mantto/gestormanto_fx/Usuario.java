package com.mantto.gestormanto_fx;

import java.util.ArrayList;

public class Usuario {
    // atributos para la base de datos
    String name;
    String lastname;
    String email;
    String username;
    String password;
    String confirmPassword;

    //contructor
    public Usuario(String name, String lastname, String email, String username, String password, String confirmPassword) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
    //getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    //retorno de los atributos en un arraylist
    public ArrayList<String> getAttributes() {
        ArrayList<String> attributes = new ArrayList<String>();
        attributes.add(name);
        attributes.add(lastname);
        attributes.add(email);
        attributes.add(username);
        attributes.add(password);
        return attributes;
    }

    public ArrayList<String> toArrayList() {
        // Retorna un ArrayList con todos los atributos
        ArrayList<String> atributos = new ArrayList<>();
        atributos.add(this.getName());
        atributos.add(this.getLastname());
        atributos.add(this.getEmail());
        atributos.add(this.getUsername());
        atributos.add(this.getPassword());
        return atributos;
    }

    @Override
    public String toString() {
        return "nombre: " + name +
                "\napellido: " + lastname +
                "\ncorreo_electronico: " + email +
                "\nusuario: " + username +
                "\ncontraseña: " + password;
    }
}
