package com.example.radog.patm_cine_mapas.pojos;

import com.example.radog.patm_cine_mapas.constantes.G;

/**
 * Created by radog on 15/05/2017.
 */

public class Bitacora {

    private int ID;
    private int ROL_ID;
    private int operacion;

    public Bitacora() {
        this.ID = G.SIN_VALOR_INT;
        this.ROL_ID = G.SIN_VALOR_INT;
        this.operacion = G.SIN_VALOR_INT;
    }

    public Bitacora(int ID, int ROL_ID, int operacion) {
        this.ID = ID;
        this.ROL_ID = ROL_ID;
        this.operacion = operacion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getROL_ID() {
        return ROL_ID;
    }

    public void setROL_ID(int ROL_ID) {
        this.ROL_ID = ROL_ID;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
}
