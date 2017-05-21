package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 20/05/2017.
 */

public class TDAReparto {

    private int colaborador_id;
    private int pelicula_id;
    private String puesto;
    private int reparto_id;

    public TDAReparto() {
    }

    public TDAReparto(int colaborador_id, int pelicula_id, String puesto, int reparto_id) {
        this.colaborador_id = colaborador_id;
        this.pelicula_id = pelicula_id;
        this.puesto = puesto;
        this.reparto_id = reparto_id;
    }

    public int getColaborador_id() {
        return colaborador_id;
    }

    public void setColaborador_id(int colaborador_id) {
        this.colaborador_id = colaborador_id;
    }

    public int getPelicula_id() {
        return pelicula_id;
    }

    public void setPelicula_id(int pelicula_id) {
        this.pelicula_id = pelicula_id;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getReparto_id() {
        return reparto_id;
    }

    public void setReparto_id(int reparto_id) {
        this.reparto_id = reparto_id;
    }
}
