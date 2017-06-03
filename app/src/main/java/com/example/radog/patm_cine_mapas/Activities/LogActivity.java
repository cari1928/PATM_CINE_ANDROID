package com.example.radog.patm_cine_mapas.Activities;

import android.content.Intent;
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
import com.example.radog.patm_cine_mapas.Constants;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.TDA.TDACompra;
import com.example.radog.patm_cine_mapas.TDA.TDAFuncion;
import com.example.radog.patm_cine_mapas.TDA.TDAPelicula;
import com.example.radog.patm_cine_mapas.TDA.TDAPersona;
import com.example.radog.patm_cine_mapas.TDA.TDASala;
import com.example.radog.patm_cine_mapas.TDA.TDASucursal;
import com.example.radog.patm_cine_mapas.Volley.LoginVolley;

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
                    if (checkConnection(2)) {
                        lPeliculas = new ArrayList<>();
                        getVolleyPel();
                        adapter.notifyDataSetChanged();
                    } else {
                        lPeliculas = new ArrayList<>();
                        getLocalPel();
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
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
            getVolleyPel();
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
            ((MyApplication) getApplicationContext()).setToken(null);
            getLocalPel();
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

    private void getLocalPel() {
        String persona_id = ((MyApplication) getApplicationContext()).getPersona_id();
        TDAPelicula tmpPelicula;

        List<TDAPersona> lPersonas = db.select("SELECT * FROM persona WHERE persona_id=" + persona_id, new TDAPersona());
        if (lPersonas != null) {
            List<TDACompra> lCompras = db.select("SELECT * FROM compra WHERE cliente_id=" + lPersonas.get(0).getPersona_id(), new TDACompra());
            if (lCompras != null) {
                for (TDACompra compra : lCompras) {
                    List<TDAFuncion> lFunciones = db.select("SELECT * FROM funcion WHERE funcion_id=" + compra.getFuncion_id(), new TDAFuncion());
                    if (lFunciones != null) {
                        List<TDAPelicula> lPelis = db.select("SELECT * FROM pelicula WHERE pelicula_id=" + lFunciones.get(0).getPelicula_id(), new TDAPelicula(), 1);
                        List<TDASala> lSalas = db.select("SELECT * FROM sala WHERE sala_id=" + lFunciones.get(0).getSala_id(), new TDASala());
                        if (lPelis != null && lSalas != null) {
                            List<TDASucursal> lSucursales = db.select("SELECT * FROM sucursal WHERE sucursal_id=" + lSalas.get(0).getSucursal_id(), new TDASucursal());
                            if (lSucursales != null) {
                                tmpPelicula = new TDAPelicula();
                                tmpPelicula.setPelicula_id(lPelis.get(0).getPelicula_id());
                                tmpPelicula.setFuncion_id(lFunciones.get(0).getFuncion_id());
                                tmpPelicula.setFecha(compra.getFecha());
                                tmpPelicula.setTitulo(lPelis.get(0).getTitulo());
                                tmpPelicula.setNombre(lSalas.get(0).getNombre());
                                tmpPelicula.setPais(lSucursales.get(0).getPais());
                                tmpPelicula.setCiudad(lSucursales.get(0).getCiudad());
                                tmpPelicula.setDireccion(lSucursales.get(0).getDireccion());
                                tmpPelicula.setHora(lFunciones.get(0).getHora());
                                tmpPelicula.setHora_fin(lFunciones.get(0).getHora_fin());

                                lPeliculas.add(tmpPelicula);
                            }
                        }
                    }
                }
                adapter = new LogAdapter(lPeliculas, this);
                recyclerView.setAdapter(adapter);
            }
        }
    }

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
            if (response.contains("token no valido")) {
                Toast.makeText(this, "Sorry, your time has finished", Toast.LENGTH_SHORT).show();
                Intent iLogin = new Intent(this, Login.class);
                startActivity(iLogin);
                return;
            }

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
        String URL = Constants.RUTA_PHP + "/compra/listado/app/"
                + ((MyApplication) this.getApplicationContext()).getPersona_id() + "/"
                + ((MyApplication) this.getApplicationContext()).getToken();
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
