package com.example.radog.patm_cine_mapas.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.LoginService;
import com.example.radog.patm_cine_mapas.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity {

    DBHelper objDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        iniLoginService();

        //conexión y apertura de la BD
        objDBH = new DBHelper(this);
        objDBH.openDB();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
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

    }

    @OnClick(R.id.ibtnLog)
    public void ibtnLog() {

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

    private void iniLoginService() {
        Intent intent = new Intent(this, LoginService.class);
        startService(intent);
    }
}