package com.example.radog.patm_cine_mapas.Synchronization;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.Constatns;
import com.example.radog.patm_cine_mapas.TDA.TDASucursal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radog on 19/05/2017.
 */

public class SyncSucursal implements Response.Listener<String>, Response.ErrorListener {

    private RequestQueue qSolicitudes;
    private Context con;
    private DBHelper db;

    public SyncSucursal(Context con) {
        qSolicitudes = Volley.newRequestQueue(con);
        this.con = con;

        //PREPARA LA BD LOCAL
        db = new DBHelper(con);
        db.openDB();
        db.cleanDB(); //funciona

        sync();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("VOLLEY-SUCURSAL", error.toString());
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            insSucursales(jsonArray);

        } catch (Exception e) {
            Log.e("VOLLEY-SUCURSAL", e.toString());
            errorMsg();
        }

        List<TDASucursal> lSuc = db.select("SELECT * FROM sucursal", new TDASucursal());
        Log.e("VOLLEY-SUC", lSuc.toString());
    }

    private void sync() {
        //String URL = "http://192.168.1.67/cineSlim/public/index.php/api/sucursal/listado/app";
        String URL = Constatns.RUTA_PHP + "/sucursal/listado/app";

        StringRequest srURL = new StringRequest(Request.Method.GET, URL, this, this) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //HEADERS =  encabezados para la petición
                Map<String, String> headers = new HashMap<String, String>();
                headers.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "root", "root").getBytes(), Base64.DEFAULT)));

                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        qSolicitudes.add(srURL);
    }

    private void errorMsg() {
        Toast.makeText(con, "Error, try later", Toast.LENGTH_SHORT).show();
    }

    private void insSucursales(JSONArray jsonArray) throws JSONException {
        //obtiene la info de las películas
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            db.insert(new String[]{
                    db.SUCURSAL_ID,
                    db.PAIS,
                    db.CIUDAD,
                    db.DIRECCION,
                    db.LATITUD,
                    db.LONGITUD
            }, new String[]{
                    jsonObject.getString(db.SUCURSAL_ID),
                    jsonObject.getString(db.PAIS),
                    jsonObject.getString(db.CIUDAD),
                    jsonObject.getString(db.DIRECCION),
                    jsonObject.getString(db.LATITUD),
                    jsonObject.getString(db.LONGITUD)
            }, db.TABLE_SUCURSAL, false);
        }
    }

}
