package com.example.radog.patm_cine_mapas.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.Constants;
import com.example.radog.patm_cine_mapas.LoginService;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.Volley.SyncVolley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Registration extends AppCompatActivity implements
        Response.Listener<String>, Response.ErrorListener,
        ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPass)
    EditText etPass;
    @BindView(R.id.etAge)
    EditText etAge;
    @BindView(R.id.etCreditCard)
    EditText etCreditCard;

    private JSONObject jsonObject;
    private RequestQueue qSolicitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        checkConnection();
        jsonObject = new JSONObject();

        //pruebas de tiempo de sesión
        //iniLoginService();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        qSolicitudes = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    @OnClick(R.id.btnRegister)
    public void btnRegister() {
        //Toast.makeText(this, etName.getText().toString(), Toast.LENGTH_SHORT).show();

        if (checkEditText(etName) &&
                checkEditText(etLastName) &&
                checkEditText(etEmail) &&
                checkEditText(etUsername) &&
                checkEditText(etPass) &&
                checkEditText(etAge) &&
                checkEditText(etCreditCard)) {
            Toast.makeText(this, "Fill al the fields, please", Toast.LENGTH_SHORT).show();
        } else {
            insCliente();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        etName.setText(error.toString());
        errorMsg();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject objJSON = new JSONObject(response);
            if (objJSON.getString("status").equals("POST")) {
                Toast.makeText(this, "Register completed", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                errorMsg();
            }
        } catch (Exception e) {
            etName.setText(e.toString());
            errorMsg();
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;

        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
            new SyncVolley(this); //sincroniza BD, solo las funciones
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.registration_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    private boolean checkEditText(EditText objE) {
        return objE.getText().toString().equals("");
    }

    private void errorMsg() {
        Toast.makeText(this, "Error, try later", Toast.LENGTH_SHORT).show();
    }

    private void insCliente() {
        try {
            jsonObject.put("nombre", etName.getText().toString());
            jsonObject.put("apellidos", etLastName.getText().toString());
            jsonObject.put("email", etEmail.getText().toString());
            jsonObject.put("username", etUsername.getText().toString());
            jsonObject.put("pass", etPass.getText().toString());
            jsonObject.put("edad", etAge.getText().toString());
            jsonObject.put("tarjeta", etCreditCard.getText().toString());

            String URL = Constants.RUTA_JAVA + "/persona/insertar";

            StringRequest insCte = new StringRequest(Request.Method.POST, URL, this, this) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return jsonObject.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        // not supposed to happen
                        etName.setText(uee.toString());
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
            qSolicitudes.add(insCte);

        } catch (Exception e) {
            etName.setText(e.toString());
        }
    }

    /**
     * Usado para pruebas de sesion de usuario
     *
     * @deprecated
     */
    private void iniLoginService() {
        Intent intent = new Intent(this, LoginService.class);
        startService(intent);
    }
}
