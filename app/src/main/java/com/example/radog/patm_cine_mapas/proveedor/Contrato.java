package com.example.radog.patm_cine_mapas.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by radog on 14/05/2017.
 */

public class Contrato {

    public static final String AUTHORITY = "com.example.radog.patm_cine_mapas.proveedor.ProveedorDeContenido";

    public static final class Rol implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/Rol");

        // Table column
        public static final String ROL = "rol";
    }
}
