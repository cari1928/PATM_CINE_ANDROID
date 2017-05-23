package com.example.radog.patm_cine_mapas.Volley;

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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by radog on 20/05/2017.
 */

public class SyncVolley implements Response.Listener<String>, Response.ErrorListener {

    /*
    List<TDASucursal> lSuc = db.select("SELECT * FROM sucursal", new TDASucursal());
    List<TDASala> lSal = db.select("SELECT * FROM sala", new TDASala());
    List<TDAPelicula> lPeli = db.select("SELECT * FROM pelicula", new TDAPelicula());
    List<TDAFuncion> lFun = db.select("SELECT * FROM funcion", new TDAFuncion());
    List<TDACategoria> lCat = db.select("SELECT * FROM categoria", new TDACategoria());
    List<TDAColaborador> lCol = db.select("SELECT * FROM colaborador", new TDAColaborador());
    List<String> lCatPeli = db.select("SELECT * FROM categoria_pelicula", 3);
    List<String> lRep = db.select("SELECT * FROM reparto", 3);
            Log.e("VOLLEY-SUC",lFun.toString());
    */

    private RequestQueue qSolicitudes;
    private Context con;
    private DBHelper db;

    public SyncVolley(Context con) {
        qSolicitudes = Volley.newRequestQueue(con);
        this.con = con;

        //PREPARA LA BD LOCAL
        db = new DBHelper(con);
        db.openDB();
        db.cleanDB(); //borra el contenido de las tablas

        sync(); //comienza sincronización

        db.select("SELECT * FROM pelicula", 3);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("VOLLEY-SYNC", error.toString());
        errorMsg(error);
    }

    @Override
    public void onResponse(String response) {
        long res;
        Log.e("VOLLEY-RESPONSE", response);
        try {
            if (response.isEmpty()) return;

            JSONObject objJSON = new JSONObject(response);
            JSONArray jaPeli = objJSON.getJSONArray("peliculas");
            JSONArray jaFun = objJSON.getJSONArray("funciones");
            JSONArray jaSuc = objJSON.getJSONArray("sucursales");
            JSONArray jaSal = objJSON.getJSONArray("salas");
            JSONArray jaCat = objJSON.getJSONArray("categorias");
            JSONArray jaCol = objJSON.getJSONArray("colaboradores");
            JSONArray jaCatPeli = objJSON.getJSONArray("cat_pelis");
            JSONArray jaRep = objJSON.getJSONArray("repartos");
            JSONObject tmp;

            for (int i = 0; i < jaSuc.length(); i++) {
                tmp = jaSuc.getJSONObject(i);
                res = db.insert(new String[]{
                        db.SUCURSAL_ID,
                        db.PAIS,
                        db.CIUDAD,
                        db.DIRECCION,
                        db.LATITUD,
                        db.LONGITUD
                }, new String[]{
                        tmp.getString(db.SUCURSAL_ID),
                        tmp.getString(db.PAIS),
                        tmp.getString(db.CIUDAD),
                        tmp.getString(db.DIRECCION),
                        tmp.getString(db.LATITUD),
                        tmp.getString(db.LONGITUD)
                }, db.TABLE_SUCURSAL, false);

                if (res == -1) return;
            }

            for (int i = 0; i < jaSal.length(); i++) {
                tmp = jaSal.getJSONObject(i);
                res = db.insert(new String[]{
                        db.SALA_ID,
                        db.NOMBRE,
                        db.SUCURSAL_ID,
                        db.NUMERO_SALA
                }, new String[]{
                        tmp.getString(db.SALA_ID),
                        tmp.getString(db.NOMBRE),
                        tmp.getString(db.SUCURSAL_ID),
                        tmp.getString(db.NUMERO_SALA)
                }, db.TABLE_SALA, true);

                if (res == -1) return;
            }

            for (int i = 0; i < jaPeli.length(); i++) {
                tmp = jaPeli.getJSONObject(i);
                res = db.insert(new String[]{
                        db.PELICULA_ID,
                        db.TITULO,
                        db.DESCRIPCION,
                        db.F_LANZAMIENTO,
                        db.LENGUAJE,
                        db.DURACION,
                        db.POSTER
                }, new String[]{
                        tmp.getString(db.PELICULA_ID),
                        tmp.getString(db.TITULO),
                        tmp.getString(db.DESCRIPCION),
                        tmp.getString(db.F_LANZAMIENTO),
                        tmp.getString(db.LENGUAJE),
                        tmp.getString(db.DURACION),
                        tmp.getString(db.POSTER)
                }, db.TABLE_PELICULA, true);

                if (res == -1) return;
            }

            for (int i = 0; i < jaFun.length(); i++) {
                tmp = jaFun.getJSONObject(i);
                res = db.insert(new String[]{
                        db.FUNCION_ID,
                        db.PELICULA_ID,
                        db.SALA_ID,
                        db.FECHA,
                        db.HORA,
                        db.FECHA_FIN,
                        db.HORA_FIN
                }, new String[]{
                        tmp.getString(db.FUNCION_ID),
                        tmp.getString(db.PELICULA_ID),
                        tmp.getString(db.SALA_ID),
                        tmp.getString(db.FECHA),
                        tmp.getString(db.HORA),
                        tmp.getString(db.FECHA_FIN),
                        tmp.getString(db.HORA_FIN)
                }, db.TABLE_FUNCION, true);

                if (res == -1) return;
            }

            for (int i = 0; i < jaCat.length(); i++) {
                tmp = jaCat.getJSONObject(i);
                res = db.insert(new String[]{
                        db.CATEGORIA_ID,
                        db.CATEGORIA
                }, new String[]{
                        tmp.getString(db.CATEGORIA_ID),
                        tmp.getString(db.CATEGORIA)
                }, db.TABLE_CATEGORIA, false);

                if (res == -1) return;
            }

            for (int i = 0; i < jaCol.length(); i++) {
                tmp = jaCol.getJSONObject(i);
                res = db.insert(new String[]{
                        db.COLABORADOR_ID,
                        db.NOMBRE,
                        db.APELLIDOS
                }, new String[]{
                        tmp.getString(db.COLABORADOR_ID),
                        tmp.getString(db.NOMBRE),
                        tmp.getString(db.APELLIDOS)
                }, db.TABLE_COLABORADOR, false);

                if (res == -1) return;
            }

            for (int i = 0; i < jaCatPeli.length(); i++) {
                tmp = jaCatPeli.getJSONObject(i);
                res = db.insert(new String[]{
                        db.CATEGORIA_ID,
                        db.PELICULA_ID,
                        db.CATEGORIA_PELICULA_ID
                }, new String[]{
                        tmp.getString(db.CATEGORIA_ID),
                        tmp.getString(db.PELICULA_ID),
                        tmp.getString(db.CATEGORIA_PELICULA_ID)
                }, db.TABLE_CATEGORIA_PELICULA, true);

                if (res == -1) return;
            }

            for (int i = 0; i < jaRep.length(); i++) {
                tmp = jaRep.getJSONObject(i);
                res = db.insert(new String[]{
                        db.COLABORADOR_ID,
                        db.PELICULA_ID,
                        db.PUESTO,
                        db.REPARTO_ID
                }, new String[]{
                        tmp.getString(db.COLABORADOR_ID),
                        tmp.getString(db.PELICULA_ID),
                        tmp.getString(db.PUESTO),
                        tmp.getString(db.REPARTO_ID)
                }, db.TABLE_REPARTO, true);

                if (res == -1) return;
            }

            db.closeDB();
            Log.e("VOLLEY-SYNC", "bien");

        } catch (Exception e) {
            Log.e("VOLLEY-SYNC", e.toString());
            errorMsg(e);
        }
    }

    private void errorMsg(Exception e) {
        Toast.makeText(con, e.toString(), Toast.LENGTH_SHORT).show();
    }

    private void sync() {
        String URL = Constatns.RUTA_PHP + "/especial/listado/app";

        StringRequest request = new StringRequest(Request.Method.GET, URL, this, this) {

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
        qSolicitudes.add(request);
    }
}
