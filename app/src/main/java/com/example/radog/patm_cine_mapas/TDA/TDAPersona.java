package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 03/06/2017.
 */

public class TDAPersona {

    private int persona_id;
    private String nombre;
    private String apellidos;
    private String email;
    private String username;
    private String pass;
    private int edad;
    private String tarjeta;

    public TDAPersona() {
    }

    public TDAPersona(int persona_id, String nombre, String apellidos, String email, String username, String pass, int edad, String tarjeta) {
        this.persona_id = persona_id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.username = username;
        this.pass = pass;
        this.edad = edad;
        this.tarjeta = tarjeta;
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
        this.persona_id = persona_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
}
