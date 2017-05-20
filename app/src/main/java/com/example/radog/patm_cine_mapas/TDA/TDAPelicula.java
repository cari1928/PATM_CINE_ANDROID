package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 20/05/2017.
 */

public class TDAPelicula {

    private int pelicula_id;
    private String titulo;
    private String descripcion;
    private String f_lanzamiento;
    private String lenguaje;
    private int duracion;
    private String poster;

    public TDAPelicula() {
    }

    public TDAPelicula(int pelicula_id, String titulo, String descripcion, String f_lanzamiento, String lenguaje, int duracion, String poster) {
        this.pelicula_id = pelicula_id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.f_lanzamiento = f_lanzamiento;
        this.lenguaje = lenguaje;
        this.duracion = duracion;
        this.poster = poster;
    }

    public int getPelicula_id() {
        return pelicula_id;
    }

    public void setPelicula_id(int pelicula_id) {
        this.pelicula_id = pelicula_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getF_lanzamiento() {
        return f_lanzamiento;
    }

    public void setF_lanzamiento(String f_lanzamiento) {
        this.f_lanzamiento = f_lanzamiento;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
