package com.example.radog.patm_cine_mapas.Volley;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.radog.patm_cine_mapas.Activities.MainMenuActivity;
import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.Constants;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by radog on 28/05/2017.
 */

public class SyncAsientoReservado implements Response.Listener<String>, Response.ErrorListener {

    private RequestQueue qSolicitudes;
    private JSONObject jsonObject;
    private Context con;

    public SyncAsientoReservado(Context con) {
        qSolicitudes = Volley.newRequestQueue(con);
        this.con = con;
        jsonObject = new JSONObject();

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
        try {
            ((MyApplication) con.getApplicationContext()).init(); //inicializa las variables
            Intent iMainMenu = new Intent(con, MainMenuActivity.class);
            Bundle data = new Bundle();
            data.putString("TYPE", "Sucursal");

            Toast.makeText(con, "Your purchase has been completed!", Toast.LENGTH_SHORT).show();

            iMainMenu.putExtras(data);
            con.startActivity(iMainMenu);

        } catch (Exception e) {
            Log.e("CINE", "onResponse " + e.toString());
            e.printStackTrace();
            Toast.makeText(con, "onResponse " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void sync() {
        String persona_id = ((MyApplication) con.getApplicationContext()).getPersona_id();
        String token = ((MyApplication) con.getApplicationContext()).getToken();

        try {
            jsonObject.put("cliente_id", persona_id);
            jsonObject.put("asiento_id", String.valueOf(((MyApplication) con.getApplicationContext()).getAsiento_id()));
            jsonObject.put("sala_id", String.valueOf(((MyApplication) con.getApplicationContext()).getSala_id()));
            jsonObject.put("funcion_id", String.valueOf(((MyApplication) con.getApplicationContext()).getFuncion_id()));

        } catch (Exception e) {
            Log.e("CINE", "SYNC: " + e.toString());
            Toast.makeText(con, e.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        String URL = Constants.RUTA_PHP + "/asientos_reservados/add/" + persona_id + "/" + token;
        Log.e("CINE", "COMPRA: " + URL);

        StringRequest request = new StringRequest(Request.Method.POST, URL, this, this) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonObject.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    // not supposed to happen
                    Toast.makeText(con, uee.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("CINE", "SYNC-REQUEST: " + uee.toString());
                    return null;
                }
            }

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
