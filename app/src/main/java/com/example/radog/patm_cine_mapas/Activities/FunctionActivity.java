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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.radog.patm_cine_mapas.Adapters.FunctionAdapter;
import com.example.radog.patm_cine_mapas.BD.DBHelper;
import com.example.radog.patm_cine_mapas.Connectivity.ConnectivityReceiver;
import com.example.radog.patm_cine_mapas.Connectivity.MyApplication;
import com.example.radog.patm_cine_mapas.Constatns;
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

public class FunctionActivity extends AppCompatActivity implements
        Response.Listener<String>, Response.ErrorListener,
        ConnectivityReceiver.ConnectivityReceiverListener {

    private FunctionAdapter adapter;
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
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);
        qSolicitudes = Volley.newRequestQueue(this);

        db = new DBHelper(this);
        db.openDB();

        lPeliculas = new ArrayList<>();
        recyclerView.setHasFixedSize(true);

        Bundle data = getIntent().getExtras();
        type = data.getString("TYPE");
        if (type.equals("Login")) {
            tipo = 1;
        } else {
            tipo = 2;
        }

        getPeliculas(); //sin conexion

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
                    getPeliculas();
                    adapter.notifyDataSetChanged();

                    //si es mandado llamar desde otro punto que no sea el onCreate..
                    if (checkConnection(2)) { //si esta conectado, entonces... sincroniza
                        new SyncVolley(this);
                    }
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

            //sincroniza BD, solo las funciones
            if (isConnected) {
                new SyncVolley(this);
            }
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

            adapter = new FunctionAdapter(lPeliculas, this, tipo);
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
        //int tipo = 2;
        String url;

        try {
/*            List<TDASucursal> lSuc = db.select("SELECT * FROM sucursal", new TDASucursal());
            List<TDASala> lSal = db.select("SELECT * FROM sala", new TDASala());
            List<TDAPelicula> lPeli = db.select("SELECT * FROM pelicula", new TDAPelicula());
            List<TDAFuncion> lFun = db.select("SELECT * FROM funcion", new TDAFuncion());
            List<TDACategoria> lCat = db.select("SELECT * FROM categoria", new TDACategoria());
            List<TDAColaborador> lCol = db.select("SELECT * FROM colaborador", new TDAColaborador());
            List<String> lCatPeli = db.select("SELECT * FROM categoria_pelicula", 3);
            List<String> lRep = db.select("SELECT * FROM reparto", 3);*/

            if (type.equals("Login")) {
                //para que la persona que no se ha logueado vea todas las películas disponibles sin clasificación por función o sucursal
                url = "SELECT * FROM pelicula ORDER BY titulo";
            } else {
                url = "SELECT * FROM sucursal " +
                        "WHERE latitud=" + ((MyApplication) this.getApplication()).getLatitud() +
                        " AND longitud=" + ((MyApplication) this.getApplication()).getLongitud();

                /*url = "SELECT * FROM sucursal " +
                        "WHERE sucursal_id=1";*/
                List<TDASucursal> lSuc = db.select(url, new TDASucursal());

                if (lSuc == null) {
                    return;
                }

                ((MyApplication) this.getApplication()).setSucursal_id(lSuc.get(0).getSucursal_id());
                ((MyApplication) this.getApplication()).setPais(lSuc.get(0).getPais());
                ((MyApplication) this.getApplication()).setCiudad(lSuc.get(0).getCiudad());
                ((MyApplication) this.getApplication()).setDireccion(lSuc.get(0).getDireccion());

                //para que el cliente vea las funciones disponibles en una sucursal específica
                url = "select f.pelicula_id, titulo, descripcion, f_lanzamiento, lenguaje, duracion, poster, funcion_id, f.sala_id, " +
                        "fecha, hora, fecha_fin, hora_fin, nombre\n" +
                        "from funcion f\n" +
                        "inner join pelicula p on p.pelicula_id = f.pelicula_id\n" +
                        "inner join sala s on s.sala_id = f.sala_id\n" +
                        "where f.sala_id in (select sala_id from sala where sucursal_id=" + lSuc.get(0).getSucursal_id() + ")\n" +
                        "order by titulo";
            }

            List<TDAPelicula> lPeli = db.select(url, new TDAPelicula(), tipo);

            if (lPeli == null) {
                Toast.makeText(this, "Refresque la Actividad", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < lPeli.size(); i++) {
                objPel = new TDAPelicula();

                objPel.setFuncion_id(lPeli.get(i).getFuncion_id());
                objPel.setPelicula_id(lPeli.get(i).getPelicula_id());
                objPel.setTitulo(lPeli.get(i).getTitulo());
                objPel.setLenguaje(lPeli.get(i).getLenguaje());
                objPel.setDuracion(lPeli.get(i).getDuracion());
                objPel.setPoster(lPeli.get(i).getPoster());

                if (tipo == 2) {
                    objPel.setFuncion_id(lPeli.get(i).getFuncion_id());
                    objPel.setSala_id(lPeli.get(i).getSala_id());
                    objPel.setFecha(lPeli.get(i).getFecha());
                    objPel.setHora(lPeli.get(i).getHora());
                    objPel.setFecha_fin(lPeli.get(i).getFecha_fin());
                    objPel.setHora_fin(lPeli.get(i).getHora_fin());
                    objPel.setNombre(lPeli.get(i).getNombre());
                }
                lPeliculas.add(objPel);
            }
            adapter = new FunctionAdapter(lPeliculas, this, tipo);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Actualice u obtenga conexión a internet para sincronizar", Toast.LENGTH_SHORT).show();
        }
    }

}
