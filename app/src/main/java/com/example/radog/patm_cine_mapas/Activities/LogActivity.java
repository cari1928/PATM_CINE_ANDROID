package com.example.radog.patm_cine_mapas.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.radog.patm_cine_mapas.Adapters.LogAdapter;
import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.TDA.TDAPelicula;
import com.example.radog.patm_cine_mapas.TDA.TDASucursal;
import com.example.radog.patm_cine_mapas.Volley.SyncVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogActivity extends AppCompatActivity implements
        Response.Listener<String>, Response.ErrorListener,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private LogAdapter adapter;
    private RecyclerView.LayoutManager adminLayout;
    private DBHelper db;
    private RequestQueue qSolicitudes;
    List<TDAPelicula> lPeliculas;
    private String type;
    private int tipo;

    @BindView(R.id.recycleList)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);
        qSolicitudes = Volley.newRequestQueue(this);

        db = new DBHelper(this);
        db.openDB();

        lPeliculas = new ArrayList<>();
        recyclerView.setHasFixedSize(true);

        getVolleyPel(); //sin conexion

        adminLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(adminLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkConnection(1);
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
                    lPeliculas.clear();
                    getVolleyPel();
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(this, "No hay funciones disponibles", Toast.LENGTH_SHORT).show();
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
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private boolean checkConnection(int type) {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (type == 2) {
            return isConnected;
        }
        showSnack(isConnected);
        return false;
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

        Snackbar snackbar = Snackbar.make(findViewById(R.id.log_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    /****************************************************************
     * FIN DE DETECCIÓN DE CONEXIÓN WIFI**************************************************************
     ***************************************************************/

    /****************************************************************
     * Inicio Volley**************************************************************
     ***************************************************************/
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("VOLLEY-FUNCTION", error.toString());
        error.printStackTrace();
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        TDAPelicula objPel;
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tmp = jsonArray.getJSONObject(i);

                objPel = new TDAPelicula();
                objPel.setPelicula_id(tmp.getInt("compra_id"));
                objPel.setFuncion_id(tmp.getInt("funcion_id"));
                objPel.setFecha(tmp.getString("fecha"));

                JSONArray jsonA = tmp.getJSONArray("pelicula");
                JSONObject jsonO = jsonA.getJSONObject(0);
                objPel.setTitulo(jsonO.getString("titulo"));

                jsonA = tmp.getJSONArray("sala");
                jsonO = jsonA.getJSONObject(0);
                objPel.setNombre(jsonO.getString("nombre"));

                jsonA = tmp.getJSONArray("sucursal");
                jsonO = jsonA.getJSONObject(0);
                objPel.setPais(jsonO.getString("pais"));
                objPel.setCiudad(jsonO.getString("ciudad"));
                objPel.setDireccion(jsonO.getString("direccion"));

                jsonA = tmp.getJSONArray("funcion");
                jsonO = jsonA.getJSONObject(0);
                objPel.setHora(jsonO.getString("hora"));
                objPel.setHora_fin(jsonO.getString("hora_fin"));

                lPeliculas.add(objPel);
            }

            adapter = new LogAdapter(lPeliculas, this);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getVolleyPel() {
        String persona_id = ((MyApplication) this.getApplicationContext()).getPersona_id();
        String token = ((MyApplication) this.getApplicationContext()).getToken();

        /*String URL = Constatns.RUTA_PHP + "/compra/listado/cliente/"
                + persona_id + "/"
                + token;*/

        String URL = "http://192.168.1.67/cineSlim/public/index.php/api/compra/listado/app/34/f972e23e8d11952a49651a8203f8f8c4";
        Log.e("CINE", URL);


        StringRequest reqListComp = new StringRequest(Request.Method.GET, URL, this, this) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", String.format("Basic %s", Base64.encodeToString(
                        String.format("%s:%s", "root", "root").getBytes(),
                        Base64.DEFAULT)));
                return params;
            }
        };

        //HERMOSO!!!!
        reqListComp.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        qSolicitudes.add(reqListComp);
    }

    /****************************************************************
     * FIN DE VOLLEY**************************************************************
     ***************************************************************/

}
