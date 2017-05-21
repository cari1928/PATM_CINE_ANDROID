package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 20/05/2017.
 */

public class TDACategoriaPelicula {

    private int categoria_id;
    private int pelicula_id;
    private int categoria_pelicula_id;

    public TDACategoriaPelicula() {
    }

    public TDACategoriaPelicula(int categoria_id, int pelicula_id, int categoria_pelicula_id) {
        this.categoria_id = categoria_id;
        this.pelicula_id = pelicula_id;
        this.categoria_pelicula_id = categoria_pelicula_id;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public int getPelicula_id() {
        return pelicula_id;
    }

    public void setPelicula_id(int pelicula_id) {
        this.pelicula_id = pelicula_id;
    }

    public int getCategoria_pelicula_id() {
        return categoria_pelicula_id;
    }

    public void setCategoria_pelicula_id(int categoria_pelicula_id) {
        this.categoria_pelicula_id = categoria_pelicula_id;
    }
}
