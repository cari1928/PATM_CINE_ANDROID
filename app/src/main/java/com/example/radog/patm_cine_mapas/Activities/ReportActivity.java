package com.example.radog.patm_cine_mapas.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.etUsuario)
    EditText etUsuario;
    @BindView(R.id.etPelicula)
    EditText etPelicula;
    @BindView(R.id.etSala)
    EditText etSala;
    @BindView(R.id.etHora)
    EditText etHora;
    @BindView(R.id.etEntradas)
    EditText etEntradas;
    @BindView(R.id.etTotal)
    EditText etTotal;
    @BindView(R.id.etTipoPago)
    EditText etTipoPago;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        etUsuario.setText("Usuario: " + ((MyApplication) this.getApplication()).getPersona_nombre());
        etPelicula.setText("Película: " + ((MyApplication) this.getApplication()).getPelicula_titulo());
        etSala.setText("Sala: " + ((MyApplication) this.getApplication()).getSala_nombre());
        etHora.setText("Horario: " + ((MyApplication) this.getApplication()).getHora()
                + " - " + ((MyApplication) this.getApplication()).getHora_fin());
        etEntradas.setText("Asiento: " + ((MyApplication) this.getApplication()).getFila()
                + "" + ((MyApplication) this.getApplication()).getColumna());
        etTotal.setText("Total: " + ((MyApplication) this.getApplication()).getTotal());
        etTipoPago.setText("Tipo de Pago: " + ((MyApplication) this.getApplication()).getTipo_pago());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkConnection(1);
    }

    @OnClick(R.id.btnComprar)
    public void btnComprar() {
        if (checkConnection(2)) {

        } else {
            Toast.makeText(this, "No es posible realizar la compra si no cuenta con conexión a Internet", Toast.LENGTH_SHORT).show();
        }
    }

    /****************************************************************
     * DETECCIÓN DE CONECCIÓN WIFI **************************************************************
     ***************************************************************/
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

    private boolean checkConnection(int type) {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (type == 2) {
            return isConnected;
        }
        showSnack(isConnected);
        return isConnected;
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

        Snackbar snackbar = Snackbar.make(findViewById(R.id.report_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    /****************************************************************
     * FIN DE DETECCIÓN DE CONEXIÓN WIFI**************************************************************
     ***************************************************************/
}
