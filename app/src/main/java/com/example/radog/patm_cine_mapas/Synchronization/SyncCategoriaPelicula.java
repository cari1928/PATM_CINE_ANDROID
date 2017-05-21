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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by radog on 20/05/2017.
 */

public class SyncCategoriaPelicula implements Response.Listener<String>, Response.ErrorListener {

    private RequestQueue qSolicitudes;
    private Context con;
    private DBHelper db;

    public SyncCategoriaPelicula(Context con) {
        qSolicitudes = Volley.newRequestQueue(con);
        this.con = con;

        //PREPARA LA BD LOCAL
        db = new DBHelper(con);
        db.openDB();

        sync();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("VOLLEY-CATPELI", error.toString());
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject obJson = new JSONObject(response);
            JSONArray jsonArray = obJson.getJSONArray("categoria_pelicula");
            db.delete(db.TABLE_CATEGORIA_PELICULA, null);
            insCatPeliculas(jsonArray);

        } catch (Exception e) {
            Log.e("VOLLEY-CATPELI", e.toString());
            errorMsg();
        }
        db.closeDB();
    }

    private void sync() {
        //String URL = "http://192.168.1.67/cineSlim/public/index.php/api/categoria/listado";
        String URL = Constatns.RUTA_JAVA + "/categoria_pelicula/listado/app";

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

    private void insCatPeliculas(JSONArray jsonArray) throws JSONException {
        //obtiene info de las peliculas
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject joCategoria = jsonObject.getJSONObject("categoria_id");

            db.insert(new String[]{
                    db.CATEGORIA_ID,
                    db.PELICULA_ID,
                    db.CATEGORIA_PELICULA_ID
            }, new String[]{
                    joCategoria.getString(db.CATEGORIA_ID),
                    jsonObject.getString(db.PELICULA_ID),
                    jsonObject.getString(db.CATEGORIA_PELICULA_ID)
            }, db.TABLE_CATEGORIA_PELICULA, true);
        }
    }

}
