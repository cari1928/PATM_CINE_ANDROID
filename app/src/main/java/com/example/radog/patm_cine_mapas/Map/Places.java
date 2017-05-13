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

    public void getCinemas(double latmarca, double lonmarca) {
        qSolicitudes = Volley.newRequestQueue(con);

        String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=" + latmarca + "," + lonmarca +
                "&radius=10000" +
                "&type=movie_theater" +
                "&keyword=cine" +
                "&key=AIzaSyAg8JGCUKDz1XyDzSe7dF0VNKglIIGB7jc";

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
        //Toast.makeText(con, response, Toast.LENGTH_SHORT).show();

        try {
            //para eliminar las marcas
            int tamaño = marcas2.size();
            if (tamaño != 0) {
                for (int i = 0; i < tamaño; i++)
                    marcas2.get(i).remove();
            }

            JSONObject objRes = new JSONObject(response);
            JSONArray objArr = objRes.getJSONArray("results");
            for (int i = 0; i < objArr.length(); i++) {
                JSONObject objObj = objArr.getJSONObject(i);
                Bitmap bmIcon = Ion.with(con).load(objObj.getString("icon")).asBitmap().get(); //se obtiene el ícono

                //get lat and lng
                JSONObject objPlace = objObj.getJSONObject("geometry");
                objPlace = objPlace.getJSONObject("location");
                double objLat = objPlace.getDouble("lat");
                double objLon = objPlace.getDouble("lng");

                marcas2.add(mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                objLat,
                                objLon))
                        .title(objObj.getString("name"))
                        .icon(BitmapDescriptorFactory.fromBitmap(bmIcon))));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(con, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
