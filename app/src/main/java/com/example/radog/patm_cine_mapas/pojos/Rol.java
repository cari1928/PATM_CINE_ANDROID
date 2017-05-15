package com.example.radog.patm_cine_mapas.pojos;

import com.example.radog.patm_cine_mapas.constantes.G;

/**
 * Created by radog on 14/05/2017.
 */

public class Rol {

    private int ID;
    private String rol;

    public Rol() {
        this.ID = G.SIN_VALOR_INT;
        this.rol = G.SIN_VALOR_STRING;
    }

    public Rol(int ID, String rol) {
        this.ID = ID;
        this.rol = rol;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
