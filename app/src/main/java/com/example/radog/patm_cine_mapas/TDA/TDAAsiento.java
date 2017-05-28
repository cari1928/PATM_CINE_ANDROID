package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 28/05/2017.
 */

public class TDAAsiento {

    private int columna;
    private String fila;
    private int sala_id;
    private int asiento_id;
    private int funcion_id;

    public TDAAsiento() {

    }

    public TDAAsiento(int columna, String fila, int sala_id, int asiento_id, int funcion_id) {
        this.columna = columna;
        this.fila = fila;
        this.sala_id = sala_id;
        this.asiento_id = asiento_id;
        this.funcion_id = funcion_id;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }

    public int getAsiento_id() {
        return asiento_id;
    }

    public void setAsiento_id(int asiento_id) {
        this.asiento_id = asiento_id;
    }

    public int getFuncion_id() {
        return funcion_id;
    }

    public void setFuncion_id(int funcion_id) {
        this.funcion_id = funcion_id;
    }
}
