package com.example.radog.patm_cine_mapas.TDA;

/**
 * Created by radog on 03/06/2017.
 */

public class TDACompra {

    private int compra_id;
    private int cliente_id;
    private int funcion_id;
    private String fecha;
    private float total;
    private int entradas;

    public TDACompra() {
    }

    public TDACompra(int compra_id, int cliente_id, int funcion_id, String fecha, float total, int entradas) {
        this.compra_id = compra_id;
        this.cliente_id = cliente_id;
        this.funcion_id = funcion_id;
        this.fecha = fecha;
        this.total = total;
        this.entradas = entradas;
    }

    public int getCompra_id() {
        return compra_id;
    }

    public void setCompra_id(int compra_id) {
        this.compra_id = compra_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getFuncion_id() {
        return funcion_id;
    }

    public void setFuncion_id(int funcion_id) {
        this.funcion_id = funcion_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }
}
