package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 20/05/2017.
 */

public class TDAFuncion {

    private int funcion_id;
    private int pelicula_id;
    private int sala_id;
    private String fecha;
    private String hora;
    private String fecha_fin;
    private String hora_fin;

    public TDAFuncion() {
    }

    public TDAFuncion(int funcion_id, int pelicula_id, int sala_id, String fecha, String hora, String fecha_fin, String hora_fin) {
        this.funcion_id = funcion_id;
        this.pelicula_id = pelicula_id;
        this.sala_id = sala_id;
        this.fecha = fecha;
        this.hora = hora;
        this.fecha_fin = fecha_fin;
        this.hora_fin = hora_fin;
    }

    public int getFuncion_id() {
        return funcion_id;
    }

    public void setFuncion_id(int funcion_id) {
        this.funcion_id = funcion_id;
    }

    public int getPelicula_id() {
        return pelicula_id;
    }

    public void setPelicula_id(int pelicula_id) {
        this.pelicula_id = pelicula_id;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }
}
