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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by radog on 19/05/2017.
 * SINCRONIZA PELÍCULAS Y FUNCIONES
 */
public class SyncPelicula implements Response.Listener<String>, Response.ErrorListener {

    private RequestQueue qSolicitudes;
    private Context con;
    private DBHelper db;

    public SyncPelicula(Context con) {
        qSolicitudes = Volley.newRequestQueue(con);
        this.con = con;

        //PREPARA LA BD LOCAL
        db = new DBHelper(con);
        db.openDB();

        syncPeliculas();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("VOLLEY", error.toString());
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            db.delete(db.TABLE_PELICULA, null);
            insPeliculas(jsonArray);

        } catch (Exception e) {
            Log.e("VOLLEY", e.toString());
            errorMsg();
        }

        db.closeDB();
    }

    private void syncPeliculas() {
        String URL = "http://192.168.1.67/cineSlim/public/index.php/api/pelicula/listado";

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

    private void insPeliculas(JSONArray jsonArray) throws JSONException {
        //obtiene la info de las películas
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            db.insert(new String[]{
                    db.PELICULA_ID,
                    db.TITULO,
                    db.DESCRIPCION,
                    db.F_LANZAMIENTO,
                    db.LENGUAJE,
                    db.DURACION,
                    db.POSTER
            }, new String[]{
                    jsonObject.getString(db.PELICULA_ID),
                    jsonObject.getString(db.TITULO),
                    jsonObject.getString(db.DESCRIPCION),
                    jsonObject.getString(db.F_LANZAMIENTO),
                    jsonObject.getString(db.LENGUAJE),
                    jsonObject.getString(db.DURACION),
                    jsonObject.getString(db.POSTER)
            }, db.TABLE_PELICULA, false);
        }
    }
}
