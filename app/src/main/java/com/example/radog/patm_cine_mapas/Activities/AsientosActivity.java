package com.example.radog.patm_cine_mapas.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.Adapters.AsientosAdapter;
import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.TDA.TDAAsiento;
import com.example.radog.patm_cine_mapas.Volley.SyncAsientos;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AsientosActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private AsientosAdapter adapter;
    private RecyclerView.LayoutManager adminLayout;
    private DBHelper db;
    private String type;
    List<TDAAsiento> lAsientos;

    @BindView(R.id.recycleList)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asientos);
        ButterKnife.bind(this);

        db = new DBHelper(this);
        db.openDB();

        lAsientos = new ArrayList<>();
        recyclerView.setHasFixedSize(true);

        adminLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(adminLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkConnection();

        getAsientos(); //sin conexion
    }

    /****************************************************************
     * MENÚ AL MANTENER PRESIONADO **************************************************************
     ***************************************************************/
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        adapter.getItemSelected(item, type);
        return super.onContextItemSelected(item);
    }

    /****************************************************************
     * MENÚ para refrescar**************************************************************
     ***************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.function_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmRefresh:
                try {
                    lAsientos.clear();
                    getAsientos();
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(this, "Refresh " + e.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, "No hay funciones disponibles", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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
        String message;
        int color;

        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;

            //sincroniza BD, solo las funciones
            new SyncAsientos(this);
        } else {
            ((MyApplication) getApplicationContext()).setToken(null);
            Toast.makeText(this, "Sorry, you need internet connection for this", Toast.LENGTH_SHORT).show();
            Intent iMain = new Intent(this, MainMenuActivity.class);
            startActivity(iMain);

            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.function_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    /****************************************************************
     * FIN DE DETECCIÓN DE CONEXIÓN WIFI**************************************************************
     ***************************************************************/

    /**
     * HASTA RESOLVER EL PROBLEMA DE SQLITE
     */
    private void getAsientos() {
        TDAAsiento objAsi;
        String url;

        try {
            List<TDAAsiento> lAsi = db.select(
                    "SELECT asiento_id, sala_id, funcion_id, columna, fila FROM " + db.TABLE_SALA_ASIENTOS, new TDAAsiento());

            if (lAsi == null) {
                Toast.makeText(this, "Refresh the activity, please", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < lAsi.size(); i++) {
                objAsi = new TDAAsiento();
                objAsi.setAsiento_id(lAsi.get(i).getAsiento_id());
                objAsi.setSala_id(lAsi.get(i).getSala_id());
                objAsi.setFuncion_id(lAsi.get(i).getFuncion_id());
                objAsi.setColumna(lAsi.get(i).getColumna());
                objAsi.setFila(lAsi.get(i).getFila());

                lAsientos.add(objAsi);
            }
            adapter = new AsientosAdapter(lAsientos, this);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CINE", e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}