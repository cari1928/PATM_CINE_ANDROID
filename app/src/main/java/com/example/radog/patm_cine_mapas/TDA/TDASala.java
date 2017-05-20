package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 20/05/2017.
 */

public class TDASala {

    private int sala_id;
    private String nombre;
    private int sucursal_id;
    private int numero_sala;

    public TDASala() {
    }

    public TDASala(int sala_id, String nombre, int sucursal_id, int numero_sala) {
        this.sala_id = sala_id;
        this.nombre = nombre;
        this.sucursal_id = sucursal_id;
        this.numero_sala = numero_sala;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSucursal_id() {
        return sucursal_id;
    }

    public void setSucursal_id(int sucursal_id) {
        this.sucursal_id = sucursal_id;
    }

    public int getNumero_sala() {
        return numero_sala;
    }

    public void setNumero_sala(int numero_sala) {
        this.numero_sala = numero_sala;
    }
}
