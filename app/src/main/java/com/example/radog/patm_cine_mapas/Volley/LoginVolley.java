package com.example.radog.patm_cine_mapas.Volley;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.widget.TextView;
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
import com.example.radog.patm_cine_mapas.Constatns;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by radog on 19/05/2017.
 */

public class LoginVolley implements Response.Listener<String>, Response.ErrorListener {

    private RequestQueue qSolicitudes;
    private Context con;
    private TextView etUser;
    private String user, pass;
    private DBHelper db;

    public LoginVolley(Context con, TextView etUser, String user, String pass) {
        qSolicitudes = Volley.newRequestQueue(con);
        this.con = con;
        this.etUser = etUser;
        this.user = user;
        this.pass = pass;

        validaCliente();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(con, error.toString(), Toast.LENGTH_SHORT).show();
        errorMsg();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject objJSON = new JSONObject(response);
            if (objJSON.getString("status").equals("bitacora")) {
                Toast.makeText(con, "Welcome " + user, Toast.LENGTH_SHORT).show();

                ((MyApplication) con.getApplicationContext()).setPersona_id(objJSON.getString("persona_id"));
                ((MyApplication) con.getApplicationContext()).setPersona_nombre(user);
                ((MyApplication) con.getApplicationContext()).setToken(objJSON.getString("token"));


                Intent iMainMenu = new Intent(con, MainMenuActivity.class);
                con.startActivity(iMainMenu);

            } else {
                errorMsg();
            }
        } catch (Exception e) {
            //etUser.setText(e.toString());
            errorMsg();
            //Toast.makeText(con, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void errorMsg() {
        Toast.makeText(con, "Error, try later please", Toast.LENGTH_SHORT).show();
    }

    private void validaCliente() {
        String URL = Constatns.RUTA_JAVA + "/persona/validar/" + user + "/" + pass;

        StringRequest solInsCte = new StringRequest(Request.Method.GET, URL, this, this) {

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
        qSolicitudes.add(solInsCte);
    }
}
