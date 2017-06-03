package com.example.radog.patm_cine_mapas.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.Map.SucursalMapsActivity;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.Volley.LoginVolley;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String type;
    private DBHelper objDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        type = data.getString("TYPE");

        //conexión y apertura de la BD
        objDBH = new DBHelper(this);
        objDBH.openDB();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkConnection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmProfile:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.ibtnTickets)
    public void ibtnTickets() {
        checkConnection();
        if (((MyApplication) getApplicationContext()).getToken() == null) {
            Toast.makeText(this, "Sorry, you need internet connection for this", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent iMaps = new Intent(this, SucursalMapsActivity.class);
        startActivity(iMaps);
    }

    @OnClick(R.id.ibtnLog)
    public void ibtnLog() {
        Intent iLog = new Intent(this, LogActivity.class);
        startActivity(iLog);
    }

    @OnClick(R.id.ibtnAbout)
    public void ibtnAbout() {
        showAlertDialog();
    }


    @Override
    protected void onStop() {
        super.onStop();
        objDBH.closeDB();
    }

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
            }

        } else {
            ((MyApplication) getApplicationContext()).setToken(null);
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.mainmenu_layout), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    /**
     * To show modal window
     */
    private void showAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_about, null);

        TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        TextView tvPhone = (TextView) view.findViewById(R.id.tvPhone);

        Linkify.addLinks(tvEmail, Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(tvPhone, Linkify.PHONE_NUMBERS);

        alertBuilder.setView(view);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

}
