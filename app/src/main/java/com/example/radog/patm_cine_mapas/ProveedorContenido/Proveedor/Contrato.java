package com.example.radog.patm_cine_mapas.ProveedorContenido.Proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by radog on 13/05/2017.
 */

public class Contrato {

    //identificación del proveedor de contenido
    //quién es la autoridad? = ruta del proveedor de contenido
    public static final String AUTHORITY = "com.example.radog.patm_cine_mapas.ProveedorContenido.Proveedor.ProveedorDeContenido";


    /**
     * TABLA ROL
     */
    public static final class Rol implements BaseColumns {

        //uri para poder acceder a un rol
        public static final Uri CONTENT_URI = Uri.parse("conten://" + AUTHORITY + "/Rol");

        //table column - campos de ésa tabla
        public static final String ROL_ID = "rol_id";
        public static final String ROL = "rol";
    }

}
