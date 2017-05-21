package com.example.radog.patm_cine_mapas.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.Constatns;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.TDA.TDAPelicula;
import com.example.radog.patm_cine_mapas.Volley.SyncVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Function extends AppCompatActivity implements
        Response.Listener<String>, Response.ErrorListener,
        ConnectivityReceiver.ConnectivityReceiverListener {

    //private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager adminLayout;
    private RequestQueue qSolicitudes;
    List<TDAPelicula> lPeliculas;
    String fecha, hora;
    int value;
    private boolean volley;

    @BindView(R.id.recycleList)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);
        volley = false;

        lPeliculas = new ArrayList<>();
        qSolicitudes = Volley.newRequestQueue(this);

        //recyclerView = (RecyclerView) findViewById(R.id.recycleList);
        recyclerView.setHasFixedSize(true);

        getPeliculas();

        adminLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(adminLayout);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * DETECCIÓN DE CONECCIÓN WIFI
     */
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

    private void showSnack(boolean isConnected) {
        String message;
        int color;

        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;

            //sincroniza BD, solo las funciones
            new SyncVolley(this);
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        volley = isConnected;
        Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    /**
     * FIN DE DETECCIÓN DE CONEXIÓN WIFI
     */

    @Override
    public void onErrorResponse(VolleyError error) {
        //Log.e("Error", error.toString());
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        TDAPelicula objC;
        JSONObject objJSONComp;
        String vFechaHora;

        try {
            JSONObject objJSON = new JSONObject(response);
            JSONArray arrJSON = objJSON.getJSONArray("competenciac");

            for (int i = 0; i < arrJSON.length(); i++) {
                objJSONComp = arrJSON.getJSONObject(i);

                vFechaHora = objJSONComp.getString("fecha_hora");
                if (chDatos(vFechaHora)) {
                    /*objC = new Competencias();
                    objC.setFecha_hora(vFechaHora);
                    objC.setId_equipo1(objJSONComp.getInt("id_equipo1"));
                    objC.setId_equipo2(objJSONComp.getInt("id_equipo2"));
                    objC.setId_institucion_local(objJSONComp.getInt("id_institucion_local"));
                    objC.setId_institucion_visita(objJSONComp.getInt("id_institucion_visita"));
                    objC.setLogotipo_local(objJSONComp.getString("logotipol"));
                    objC.setLogotipo_visita(objJSONComp.getString("logotipov"));
                    objC.setNombre_corto_local(objJSONComp.getString("nombre_corto_local"));
                    objC.setNombre_corto_visita(objJSONComp.getString("nombre_corto_visita"));
                    objC.setRama(objJSONComp.getString("rama"));
                    objC.setSede(objJSONComp.getString("sede"));
                    objC.setValor_equipo1(objJSONComp.getInt("valor_equipo1"));
                    objC.setValor_equipo2(objJSONComp.getInt("valor_equipo2"));*/

                    //lPeliculas.add(objC);
                }

            }

            //adapter = new CompAdapter(lPeliculas, this);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public boolean chDatos(String pFechaHora) {
        String parts[] = pFechaHora.split("T");

        if (parts[0].contains(fecha) && parts[1].contains(hora)) {
            return true;
        }

        return false;
    }

    private void getPeliculas() {
        String URL = Constatns.RUTA_PHP + "/pelicula/listado";

        StringRequest reqListComp = new StringRequest(Request.Method.GET, URL, this, this) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", String.format("Basic %s", Base64.encodeToString(
                        String.format("%s:%s", "intertecs", "1nt3rt3c5").getBytes(),
                        Base64.DEFAULT)));
                return params;
            }
        };
        qSolicitudes.add(reqListComp);
    }
}
