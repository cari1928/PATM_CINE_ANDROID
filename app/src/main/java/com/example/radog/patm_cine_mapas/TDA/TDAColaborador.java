package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 20/05/2017.
 */

public class TDAColaborador {

    private int colaborador_id;
    private String nombre;
    private String apellidos;

    public TDAColaborador() {
    }

    public TDAColaborador(int colaborador_id, String nombre, String apellidos) {
        this.colaborador_id = colaborador_id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public int getColaborador_id() {
        return colaborador_id;
    }

    public void setColaborador_id(int colaborador_id) {
        this.colaborador_id = colaborador_id;
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
}


