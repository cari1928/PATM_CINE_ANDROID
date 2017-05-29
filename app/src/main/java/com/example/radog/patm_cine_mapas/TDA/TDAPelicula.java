package com.example.radog.patm_cine_mapas.TDA;

import java.util.List;

/**
 * Created by radog on 20/05/2017.
 */

public class TDAPelicula {

    //info pelicula
    private int pelicula_id;
    private String titulo;
    private String descripcion;
    private String f_lanzamiento;
    private String lenguaje;
    private int duracion;
    private String poster;

    //info funcion
    private int funcion_id;
    private int sala_id;
    private String fecha;
    private String hora;
    private String fecha_fin;
    private String hora_fin;

    //info sala
    private String nombre;
    private int sucursal_id;
    private String pais;
    private String ciudad;
    private String direccion;

    private int numero_sala;

    //info categoria
    private int categoria_id;
    private List<String> categoria;

    public TDAPelicula() {
    }

    public TDAPelicula(int pelicula_id, String titulo, String descripcion, String f_lanzamiento, String lenguaje, int duracion, String poster, int funcion_id, int sala_id, String fecha, String hora, String fecha_fin, String hora_fin, String nombre, int sucursal_id, int numero_sala, int categoria_id, List<String> categoria) {
        this.pelicula_id = pelicula_id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.f_lanzamiento = f_lanzamiento;
        this.lenguaje = lenguaje;
        this.duracion = duracion;
        this.poster = poster;
        this.funcion_id = funcion_id;
        this.sala_id = sala_id;
        this.fecha = fecha;
        this.hora = hora;
        this.fecha_fin = fecha_fin;
        this.hora_fin = hora_fin;
        this.nombre = nombre;
        this.sucursal_id = sucursal_id;
        this.numero_sala = numero_sala;
        this.categoria_id = categoria_id;
        this.categoria = categoria;
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

    public int getFuncion_id() {
        return funcion_id;
    }

    public void setFuncion_id(int funcion_id) {
        this.funcion_id = funcion_id;
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

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public List<String> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<String> categoria) {
        this.categoria = categoria;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
