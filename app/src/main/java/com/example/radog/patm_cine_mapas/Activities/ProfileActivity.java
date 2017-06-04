package com.example.radog.patm_cine_mapas.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.TDA.TDAPersona;
import com.example.radog.patm_cine_mapas.Tools;
import com.example.radog.patm_cine_mapas.Volley.LoginVolley;
import com.example.radog.patm_cine_mapas.Volley.SyncProfile;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etPass)
    EditText etPass;
    @BindView(R.id.etAge)
    EditText etAge;
    @BindView(R.id.etCreditCard)
    EditText etCreditCard;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DBHelper db;
    private RequestQueue qSolicitudes;
    private JSONObject jsonObject;
    private List<TDAPersona> lPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        jsonObject = new JSONObject();
        lPersona = new ArrayList<>();
        checkConnection();
        db = new DBHelper(this);
        db.openDB();
        initComponents();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkConnection();
    }

    @Override
    protected void onStart() {
        super.onStart();
        qSolicitudes = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.btnUpdate)
    public void btnUpdate() {
        if (checkConnection()) {
            TDAPersona tmpPersona = new TDAPersona();
            tmpPersona.setNombre(etName.getText().toString());
            tmpPersona.setApellidos(etLastName.getText().toString());
            tmpPersona.setPass(etPass.getText().toString());
            tmpPersona.setEdad(Integer.parseInt(etAge.getText().toString()));
            tmpPersona.setTarjeta(etCreditCard.getText().toString());
            new SyncProfile(this, tmpPersona);

            actClienteLocal();
        } else {
            actClienteLocal();
            Toast.makeText(this, "You information will be updated when you recover internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    /****************************************************************
     * DETECCIÓN DE CONECCIÓN WIFI **************************************************************
     ***************************************************************/
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkConnection();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
        return isConnected;
    }

    private void showSnack(boolean isConnected) {
        String message, email, pass;
        int color;

        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;

            if (((MyApplication) getApplicationContext()).getToken() == null) {
                email = ((MyApplication) getApplicationContext()).getEmail();
                pass = ((MyApplication) getApplicationContext()).getPass();
                new LoginVolley(this, email, pass, "MainMenu");

                lPersona = db.select("SELECT * FROM persona", new TDAPersona());
                for (TDAPersona tmpP : lPersona) {
                    new SyncProfile(this, tmpP);
                }
            }
        } else {
            ((MyApplication) getApplicationContext()).setToken(null);
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.profile_layout), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    /****************************************************************
     * FIN DE DETECCIÓN DE CONEXIÓN WIFI*****************************
     ***************************************************************/

    private void actClienteLocal() {
        long res;
        Tools objT = new Tools();
        res = db.update(new String[]{
                db.NOMBRE,
                db.APELLIDOS,
                db.PASS,
                db.EDAD,
                db.TARJETA
        }, new String[]{
                etName.getText().toString(),
                etLastName.getText().toString(),
                objT.encriptaDato("MD5", etPass.getText().toString()),
                etAge.getText().toString(),
                etCreditCard.getText().toString()
        }, "email='" + ((MyApplication) getApplicationContext()).getEmail() + "'", db.TABLE_PERSONA);

        if (res == -1) {
            Toast.makeText(this, "We are having problems right now, try later please", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void initComponents() {
        List<TDAPersona> lPersona = db.select(
                "SELECT * FROM persona WHERE email='" + ((MyApplication) getApplicationContext()).getEmail() + "'",
                new TDAPersona()
        );
        if (lPersona == null) {
            Toast.makeText(this, "We are having problems right now, try later please", Toast.LENGTH_SHORT).show();
            Intent iMain = new Intent(this, MainMenuActivity.class);
            startActivity(iMain);
            return;
        }
        etName.setText(lPersona.get(0).getNombre());
        etLastName.setText(lPersona.get(0).getApellidos());
        etPass.setText(lPersona.get(0).getPass());
        etAge.setText(String.valueOf(lPersona.get(0).getEdad()));
        etCreditCard.setText(lPersona.get(0).getTarjeta());
    }
}
