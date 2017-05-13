package com.example.radog.patm_cine_mapas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.radog.patm_cine_mapas.Map.SucursalMapsActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.etPass)
    EditText etPass;

    private RequestQueue qSolicitudes;
    private String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        qSolicitudes = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.btnLogin)
    public void btnLogin() {
        Tools objT = new Tools();
        user = etUser.getText().toString();
        pass = objT.encriptaDato("MD5", etPass.getText().toString());

        if (user.equals("") || pass.equals("")) {
            Toast.makeText(this, "Input the required information", Toast.LENGTH_SHORT).show();
        } else {
            validaCliente();
        }
    }

    @OnClick(R.id.btnRegister)
    public void btnRegister() {
        Intent iRegister = new Intent(this, Registration.class);
        startActivity(iRegister);
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
                Toast.makeText(this, "Welcome " + user, Toast.LENGTH_SHORT).show();

                Intent iSucursal = new Intent(this, SucursalMapsActivity.class);
                Bundle data = new Bundle();

                ArrayList<UserData> userData = new ArrayList<>();
                UserData objU = new UserData("username", user);
                userData.add(objU);
                objU = new UserData("pass", pass);
                userData.add(objU);

                data.putParcelableArrayList("USERDATA", userData);
                iSucursal.putExtras(data);
                startActivity(iSucursal);

            } else {
                errorMsg();
            }
        } catch (Exception e) {
            etUser.setText(e.toString());
            errorMsg();
        }
    }

    private void errorMsg() {
        Toast.makeText(this, "Error, try later", Toast.LENGTH_SHORT).show();
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
