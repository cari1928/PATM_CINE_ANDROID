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
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.Constants;
import com.example.radog.patm_cine_mapas.TDA.TDAAsiento;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radog on 28/05/2017.
 */

public class SyncAsientos implements Response.Listener<String>, Response.ErrorListener {

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

    public SyncAsientos(Context con) {
        qSolicitudes = Volley.newRequestQueue(con);
        this.con = con;

        //PREPARA LA BD LOCAL
        db = new DBHelper(con);
        db.openDB();
        db.cleanDB_P2(); //borra el contenido de las tablas

        List<TDAAsiento> p = db.select("select asiento_id, sala_id, funcion_id, columna, fila from sala_asientos", new TDAAsiento());

        sync(); //comienza sincronización
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("CINE", error.toString());
        error.printStackTrace();
        Toast.makeText(con, "onErrorResponse" + error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        long res;
        try {
            Log.e("CINE", response);
            //Toast.makeText(con, response, Toast.LENGTH_SHORT).show();

            JSONArray jsonResponse = new JSONArray(response);
            JSONObject tmp;
            for (int i = 0; i < jsonResponse.length(); i++) {
                tmp = jsonResponse.getJSONObject(i);

                res = db.insert(new String[]{
                        db.ASIENTO_ID,
                        db.COLUMNA,
                        db.FILA,
                        db.SALA_ID,
                        db.FUNCION_ID
                }, new String[]{
                        tmp.getString(db.ASIENTO_ID),
                        tmp.getString(db.COLUMNA),
                        tmp.getString(db.FILA),
                        "1",
                        "2"
                }, db.TABLE_SALA_ASIENTOS, false);

                if (res == -1) return;
            }

            Log.e("CINE", "SYNC-ASIENTOS BIEN");

        } catch (Exception e) {
            Log.e("CINE", "onResponse " + e.toString());
            e.printStackTrace();
            Toast.makeText(con, "onResponse " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void errorMsg(Exception e) {
        Toast.makeText(con, e.toString(), Toast.LENGTH_SHORT).show();
    }

    private void sync() {
        String URL = Constants.RUTA_PHP + "/sala_asientos/disponiblesApp/"
                + ((MyApplication) con.getApplicationContext()).getFuncion_id() + "/"
                + ((MyApplication) con.getApplicationContext()).getSucursal_id() + "/"
                + ((MyApplication) con.getApplicationContext()).getSala_id() + "/"
                + ((MyApplication) con.getApplicationContext()).getPersona_id() + "/"
                + ((MyApplication) con.getApplicationContext()).getToken();

  /*      String URL = "http://192.168.1.67/cineSlim/public/index.php/api" +
                "/sala_asientos/disponiblesApp/" +
                "16/" +
                "1/" +
                "6/" +
                "34/" +
                "3f35f32b19343fedc6a29dc854e7e096";*/

        Log.e("CINE", URL);

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
