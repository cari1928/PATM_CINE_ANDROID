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
import com.example.radog.patm_cine_mapas.TDA.TDAPersona;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by radog on 03/06/2017.
 */

public class SyncProfile implements Response.Listener<String>, Response.ErrorListener {

    private RequestQueue qSolicitudes;
    private JSONObject jsonObject;
    private Context con;
    private DBHelper db;
    TDAPersona lPersona;

    public SyncProfile(Context con, TDAPersona lPersona) {
        this.con = con;
        this.lPersona = lPersona;
        qSolicitudes = Volley.newRequestQueue(con);
        jsonObject = new JSONObject();
        db = new DBHelper(con);
        db.openDB();
        sync();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(con, "Sorry, we have problems, try later", Toast.LENGTH_SHORT).show();
        error.printStackTrace();
        Log.e("CINE", error.toString());
    }

    @Override
    public void onResponse(String response) {
        try {
            if (!response.contains("PUT")) {
                //Toast.makeText(con, "We are having problems right now, try later please", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Log.e("CINE", "onResponse " + e.toString());
            e.printStackTrace();
            Toast.makeText(con, "onResponse " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void sync() {
        try {
            jsonObject.put("persona_id", ((MyApplication) con.getApplicationContext()).getPersona_id());
            jsonObject.put("nombre", lPersona.getNombre());
            jsonObject.put("apellidos", lPersona.getApellidos());
            jsonObject.put("pass", lPersona.getPass());
            jsonObject.put("edad", String.valueOf(lPersona.getEdad()));
            jsonObject.put("tarjeta", lPersona.getTarjeta());

        } catch (JSONException e) {
            // handle exception (not supposed tohappen)
            Toast.makeText(con, "Sorry, we have problems", Toast.LENGTH_SHORT).show();
            Log.e("CINE", e.toString());
            return;
        }

        String URL = Constants.RUTA_JAVA + "/persona/actualizar";
        StringRequest solActCte = new StringRequest(Request.Method.PUT, URL, this, this) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonObject.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    // not supposed to happen
                    Toast.makeText(con, "Sorry, we have problems", Toast.LENGTH_SHORT).show();
                    Log.e("CINE", uee.toString());
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "root", "root").getBytes(), Base64.DEFAULT)));

                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        qSolicitudes.add(solActCte);
    }
}
