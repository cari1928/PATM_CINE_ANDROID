package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 20/05/2017.
 */

public class TDACategoria {

    private int categoria_id;
    private String categoria;

    public TDACategoria() {
    }

    public TDACategoria(int categoria_id, String categoria) {
        this.categoria_id = categoria_id;
        this.categoria = categoria;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
