package com.example.radog.patm_cine_mapas.Volley;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.radog.patm_cine_mapas.Map.SucursalMapsActivity;
import com.example.radog.patm_cine_mapas.UserData;

import org.json.JSONObject;

import java.util.ArrayList;
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
        etUser.setText(error.toString());
        errorMsg();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject objJSON = new JSONObject(response);
            if (objJSON.getString("status").equals("bitacora")) {
                Toast.makeText(con, "Welcome " + user, Toast.LENGTH_SHORT).show();

                Intent iSucursal = new Intent(con, SucursalMapsActivity.class);
                Bundle data = new Bundle();

                ArrayList<UserData> userData = new ArrayList<>();
                UserData objU = new UserData("username", user);
                userData.add(objU);
                objU = new UserData("pass", pass);
                userData.add(objU);

                data.putParcelableArrayList("USERDATA", userData);
                iSucursal.putExtras(data);
                con.startActivity(iSucursal);

            } else {
                errorMsg();
            }
        } catch (Exception e) {
            etUser.setText(e.toString());
            errorMsg();
        }
    }

    private void errorMsg() {
        Toast.makeText(con, "Error, try later", Toast.LENGTH_SHORT).show();
    }

    private void validaCliente() {
        String URL = "http://192.168.1.67:8082/PATM_CINE/apirest/persona/validar/" + user + "/" + pass;

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
