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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.Constatns;
import com.example.radog.patm_cine_mapas.FunctionAdapter;
import com.example.radog.patm_cine_mapas.R;
import com.example.radog.patm_cine_mapas.TDA.TDAFuncion;
import com.example.radog.patm_cine_mapas.TDA.TDAPelicula;

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
    private FunctionAdapter adapter;
    private RecyclerView.LayoutManager adminLayout;
    private DBHelper db;
    private RequestQueue qSolicitudes;
    List<TDAPelicula> lPeliculas;

    @BindView(R.id.recycleList)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);
        checkConnection();
        qSolicitudes = Volley.newRequestQueue(this);
        db = new DBHelper(this);
        db.openDB();

        lPeliculas = new ArrayList<>();
        //recyclerView = (RecyclerView) findViewById(R.id.recycleList);
        recyclerView.setHasFixedSize(true);

        //getPeliculas();
        getVolleyPel();

        adminLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(adminLayout);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /****************************************************************
     * MENÚ AL MANTENER PRESIONADO **************************************************************
     ***************************************************************/
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        adapter.getItemSelected(item, "login");
        return super.onContextItemSelected(item);
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

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;

        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;

            //sincroniza BD, solo las funciones
            //new SyncVolley(this);
        } else {
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

    /****************************************************************
     * Inicio Volley**************************************************************
     ***************************************************************/
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("VOLLEY-FUNCTION", error.toString());
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
                objPel.setPelicula_id(tmp.getInt("pelicula_id"));
                objPel.setTitulo(tmp.getString("titulo"));
                objPel.setLenguaje(tmp.getString("lenguaje"));
                objPel.setDuracion(tmp.getInt("duracion"));
                objPel.setPoster(tmp.getString("poster"));

                lPeliculas.add(objPel);
            }

            adapter = new FunctionAdapter(lPeliculas, this);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getVolleyPel() {
        String URL = Constatns.RUTA_PHP + "/pelicula/listado";
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
        qSolicitudes.add(reqListComp);
    }

    /****************************************************************
     * FIN DE VOLLEY**************************************************************
     ***************************************************************/

    /**
     * HASTA RESOLVER EL PROBLEMA DE SQLITE
     */
    private void getPeliculas() {
        TDAPelicula objPel;
        int totalFields = 7;

        try {
            /*List<String> listPel = db.select(
                    "SELECT DISTINCT f.pelicula_id, titulo, fecha, fecha_fin, lenguaje, duracion, poster " +
                            "    FROM funcion f " +
                            "    INNER JOIN pelicula ON f.pelicula_id = pelicula.pelicula_id " +
                            "    ORDER BY titulo", totalFields);*/

            List<TDAPelicula> lPeli = db.select("SELECT * FROM pelicula", new TDAPelicula());
            List<TDAFuncion> lFun = db.select("SELECT * FROM funcion", new TDAFuncion());
            List<String> listPel = db.select(
                    "SELECT * FROM pelicula INNER JOIN funcion on funcion.pelicula_id = pelicula.pelicula_id", 3);

            for (int i = 0; i < listPel.size(); i++) {
                objPel = new TDAPelicula();

                objPel.setPelicula_id(Integer.parseInt(listPel.get(i)));
                objPel.setTitulo(listPel.get(++i));
                objPel.setFecha(listPel.get(++i));
                objPel.setFecha_fin(listPel.get(++i));
                objPel.setLenguaje(listPel.get(++i));
                objPel.setDuracion(Integer.parseInt(listPel.get(++i)));
                objPel.setPoster(listPel.get(++i));

                lPeliculas.add(objPel);
            }

            adapter = new FunctionAdapter(lPeliculas, this);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
