package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 20/05/2017.
 */

public class TDASucursal {

    private int sucursal_id;
    private String pais;
    private String ciudad;
    private String direccion;
    private float latitud;
    private float longitud;

    public TDASucursal() {
    }

    public TDASucursal(int sucursal_id, String pais, String ciudad, String direccion, float latitud, float longitud) {
        this.sucursal_id = sucursal_id;
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getSucursal_id() {
        return sucursal_id;
    }

    public void setSucursal_id(int sucursal_id) {
        this.sucursal_id = sucursal_id;
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

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
}
