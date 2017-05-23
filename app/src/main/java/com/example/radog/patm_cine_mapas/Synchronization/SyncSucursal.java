package com.example.radog.patm_cine_mapas.Synchronization;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.TDA.TDASucursal;

import java.util.List;

/**
 * Created by radog on 19/05/2017.
 */

public class SyncSucursal {

    private RequestQueue qSolicitudes;
    private Context con;
    private DBHelper db;
    private List<TDASucursal> lSuc;

    public SyncSucursal(Context con, List<TDASucursal> lSuc) {
        qSolicitudes = Volley.newRequestQueue(con);
        this.con = con;
        this.lSuc = lSuc;

        //PREPARA LA BD LOCAL
        db = new DBHelper(con);
        db.openDB();
        db.cleanDB(); //funciona

        sync();
    }

    private void sync() {
        TDASucursal objSuc;
        for (int i = 0; i < lSuc.size(); i++) {
            objSuc = lSuc.get(i);

            db.insert(new String[]{
                    db.SUCURSAL_ID,
                    db.PAIS,
                    db.CIUDAD,
                    db.DIRECCION,
                    db.LATITUD,
                    db.LONGITUD
            }, new String[]{
                    String.valueOf(objSuc.getSucursal_id()),
                    objSuc.getPais(),
                    objSuc.getCiudad(),
                    objSuc.getDireccion(),
                    String.valueOf(objSuc.getLatitud()),
                    String.valueOf(objSuc.getLongitud())
            }, db.TABLE_SUCURSAL, false);
        }

    }


}
