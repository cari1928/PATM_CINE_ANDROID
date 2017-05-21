package com.example.radog.patm_cine_mapas;

/**
 * Created by radog on 20/05/2017.
 */

public class itemFunctionEvent {

    private int picture, funcion_id;
    private String titulo, categoria;
    private int duracion;

    public itemFunctionEvent(int picture, int funcion_id, String titulo, String categoria, int duracion) {
        this.picture = picture;
        this.funcion_id = funcion_id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.duracion = duracion;
    }

    public int getFuncion_id() {
        return funcion_id;
    }

    public void setFuncion_id(int funcion_id) {
        this.funcion_id = funcion_id;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
