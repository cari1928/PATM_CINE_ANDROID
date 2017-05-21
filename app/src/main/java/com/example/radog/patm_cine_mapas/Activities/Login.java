package com.example.radog.patm_cine_mapas.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.Tools;
import com.example.radog.patm_cine_mapas.Volley.LoginVolley;
import com.example.radog.patm_cine_mapas.Volley.SyncVolley;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.etPass)
    EditText etPass;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        checkConnection(true); //para la sincronización de la bd
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmFunction:
                Intent iFunction = new Intent(this, Function.class);
                startActivity(iFunction);
                break;
        }

        return super.onOptionsItemSelected(item);
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
            //TODO loguear
        }
    }

    @OnClick(R.id.btnRegister)
    public void btnRegister() {
        Intent iRegister = new Intent(this, Registration.class);
        startActivity(iRegister);
    }

    private void checkConnection(boolean sync) {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (sync) {
            showSnack(isConnected);
        } else {
            volley(isConnected);
        }
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

        Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    private void volley(boolean isConnected) {
        LoginVolley loginVolley;
        if (isConnected) {
            loginVolley = new LoginVolley(this, etUser, user, pass);
        } else {
            //valida de forma local
        }
    }

}
