package com.example.radog.patm_cine_mapas.Volley;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.example.radog.patm_cine_mapas.Constants;
import com.example.radog.patm_cine_mapas.LoginService;
import com.example.radog.patm_cine_mapas.TDA.TDAPersona;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radog on 19/05/2017.
 */

public class LoginVolley implements Response.Listener<String>, Response.ErrorListener {

    private RequestQueue qSolicitudes;
    private Context con;
    private TextView etUser;
    private String email, pass;
    private DBHelper db;

    public LoginVolley(Context con, TextView etUser, String email, String pass) {
        qSolicitudes = Volley.newRequestQueue(con);
        this.con = con;
        this.etUser = etUser;
        this.email = email;
        this.pass = pass;

        db = new DBHelper(con);
        db.openDB();

        validaCliente();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(con, error.toString(), Toast.LENGTH_SHORT).show();
        errorMsg();
    }

    @Override
    public void onResponse(String response) {
        long res;
        List<TDAPersona> lPersonas;
        boolean flag = false;
        try {
            JSONObject objJSON = new JSONObject(response);
            if (objJSON.getString("status").equals("bitacora")) {

                //similar a un $_SESSION
                ((MyApplication) con.getApplicationContext()).setPersona_id(objJSON.getString(db.PERSONA_ID));
                ((MyApplication) con.getApplicationContext()).setToken(objJSON.getString("token"));
                ((MyApplication) con.getApplicationContext()).setPersona_nombre(objJSON.getString(db.NOMBRE));
                ((MyApplication) con.getApplicationContext()).setApellidos(objJSON.getString(db.APELLIDOS));
                ((MyApplication) con.getApplicationContext()).setEmail(email);
                ((MyApplication) con.getApplicationContext()).setUsername(objJSON.getString(db.USERNAME));
                ((MyApplication) con.getApplicationContext()).setPass(pass);
                ((MyApplication) con.getApplicationContext()).setEdad(objJSON.getInt(db.EDAD));
                ((MyApplication) con.getApplicationContext()).setTarjeta(objJSON.getString(db.TARJETA));

                lPersonas = db.select("SELECT * FROM persona", new TDAPersona());
                for (TDAPersona persona : lPersonas) {
                    if (persona.getEmail().equals(email) && persona.getPass().equals(pass)) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    res = db.insert(new String[]{
                            db.PERSONA_ID,
                            db.NOMBRE,
                            db.APELLIDOS,
                            db.EMAIL,
                            db.USERNAME,
                            db.PASS,
                            db.EDAD,
                            db.TARJETA
                    }, new String[]{
                            objJSON.getString(db.PERSONA_ID),
                            objJSON.getString(db.NOMBRE),
                            objJSON.getString(db.APELLIDOS),
                            objJSON.getString(db.EMAIL),
                            objJSON.getString(db.USERNAME),
                            objJSON.getString(db.PASS),
                            objJSON.getString(db.EDAD),
                            objJSON.getString(db.TARJETA)
                    }, db.TABLE_PERSONA, false);

                    if (res == -1) {
                        Log.e("CINE", "LOGIN-ERROR-INSERTAR-PERSONA-BD-LOCAL");
                        return;
                    }
                }

                iniLoginService();

                Intent iMainMenu = new Intent(con, MainMenuActivity.class);
                con.startActivity(iMainMenu);

            } else {
                errorMsg();
                Log.e("CINE", "LOGIN - BITACORA ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg();
        }
    }

    private void errorMsg() {
        Toast.makeText(con, "Error, try again", Toast.LENGTH_SHORT).show();
    }

    private void validaCliente() {
        String URL = Constants.RUTA_JAVA + "/persona/validar/" + email + "/" + pass;

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

    private void iniLoginService() {
        Intent intent = new Intent(con, LoginService.class);
        con.startService(intent);
    }
}
