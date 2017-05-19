package com.example.radog.patm_cine_mapas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.radog.patm_cine_mapas.Map.SucursalMapsActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity implements
        Response.Listener<String>, Response.ErrorListener,
        ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.etPass)
    EditText etPass;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RequestQueue qSolicitudes;
    private String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        checkConnection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmAddEvent:
                Intent intNewEvent = new Intent(this, NewEvent.class);
                startActivity(intNewEvent);
                break;
            case R.id.itmAbout:
                Intent intAbout = new Intent(this, About.class);
                startActivity(intAbout);
        }

        return super.onOptionsItemSelected(item);
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
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.login_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

}
