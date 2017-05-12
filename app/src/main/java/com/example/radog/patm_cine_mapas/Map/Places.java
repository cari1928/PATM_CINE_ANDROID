package com.example.radog.patm_cine_mapas.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radog on 12/05/2017.
 */

public class Places implements Response.Listener<String>, Response.ErrorListener {

    private Marker marcas[];
    private GoogleMap mapa;
    private JSONArray lugares;
    private JSONObject results, item, lugar;
    private RequestQueue qSolicitudes;
    private Context con;
    private List<Marker> marcas2;

    public Places(Context con, GoogleMap mapa, List<Marker> marcas2) {
        this.con = con;
        this.mapa = mapa;
        this.marcas2 = marcas2;
    }

    public void getNokia(double latmarca, double lonmarca) {
        qSolicitudes = Volley.newRequestQueue(con);

        String URL = "http://demo.places.nlp.nokia.com/places/v1/discover/explore?at="
                + latmarca + "," + lonmarca + "&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg&tf=plain&pretty=true";

        StringRequest solGETCte = new StringRequest(Request.Method.GET, URL, this, this) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        qSolicitudes.add(solGETCte);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(con, error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        try {
            int tamaño = marcas2.size();
            if (tamaño != 0) {
                for (int i = 0; i < tamaño; i++)
                    marcas2.get(i).remove();
            }

            results = new JSONObject(response);
            item = results.getJSONObject("results");
            lugares = item.getJSONArray("items");

            for (int i = 0; i < lugares.length(); i++) {
                lugar = lugares.getJSONObject(i);

                Bitmap bmImg = Ion.with(con).load(lugar.getString("icon")).asBitmap().get();

                marcas2.add(mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                lugar.getJSONArray("position").getDouble(0),
                                lugar.getJSONArray("position").getDouble(1)))
                        .title(lugar.getString("title"))
                        .icon(BitmapDescriptorFactory.fromBitmap(bmImg))));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(con, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void borrarMarcadores() {
        for (int i = 0; i < marcas.length; i++)
            marcas[i].remove();
    }
}
