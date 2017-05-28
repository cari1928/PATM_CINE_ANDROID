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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.LoginService;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.Tools;
import com.example.radog.patm_cine_mapas.Volley.LoginVolley;
import com.example.radog.patm_cine_mapas.Volley.SyncVolley;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity implements
        ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.etPass)
    EditText etPass;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String user, pass;
    private DBHelper db;
    private SyncVolley syncVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        db = new DBHelper(this);
        db.openDB();

        closeService();

        setSupportActionBar(toolbar);
        checkConnection(true); //para la sincronización de la bd
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        closeService();
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
                Intent iFunction = new Intent(this, FunctionActivity.class);

                Bundle data = new Bundle();
                data.putString("TYPE", "Login");
                iFunction.putExtras(data);

                startActivity(iFunction);
                break;

            case R.id.itmAbout:
                showAlertDialog();
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
            checkConnection(true); //para la sincronización de la bd
            new LoginVolley(this, etUser, user, pass);
        }
    }

    /**
     * BLOCKS THE BACK BUTTON
     * Para una mejor funcionalidad en cuanto al tiempo que durará su sesión
     * También usado para pruebas
     */
    @Override
    public void onBackPressed() {
/*
        List<TDASucursal> lSuc = db.select("SELECT * FROM sucursal", new TDASucursal());
        List<TDASala> lSal = db.select("SELECT * FROM sala", new TDASala());
        List<TDAPelicula> lPeli = db.select("SELECT * FROM pelicula", new TDAPelicula());
        List<TDAFuncion> lFun = db.select("SELECT * FROM funcion", new TDAFuncion());
        List<TDACategoria> lCat = db.select("SELECT * FROM categoria", new TDACategoria());
        List<TDAColaborador> lCol = db.select("SELECT * FROM colaborador", new TDAColaborador());
        List<String> lCatPeli = db.select("SELECT * FROM categoria_pelicula", 3);
        List<String> lRep = db.select("SELECT * FROM reparto", 3);
*/
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
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
            syncVolley = new SyncVolley(this); //sincroniza BD, solo las funciones
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
            //db.select("SELECT persona_id FROM persona WHERE username='" + user + "' AND pass='" + pass + "'", 5);
        }
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

    private void closeService() {
        Intent intent = new Intent(this, LoginService.class);
        stopService(intent);
    }

}
