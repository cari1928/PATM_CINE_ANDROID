package com.example.radog.patm_cine_mapas.Map;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by radog on 12/05/2017.
 */

public class Geocode implements Response.Listener<String>, Response.ErrorListener {

    public String ubicacionActual;
    private RequestQueue qSolicitudes;
    private GoogleMap mMap;
    private LatLng latlng;
    private Context con;

    public Geocode(Context con, GoogleMap mMap, LatLng latlng) {
        this.con = con;
        this.mMap = mMap;
        this.latlng = latlng;
        ubicacionActual = "";
    }

    public void getPlace(double lat, double lon) {
        qSolicitudes = Volley.newRequestQueue(con);
        //String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&key=AIzaSyCrH5aKpoAJ3oBK48YRA0QJYBe_2FC0jJA";
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                + lat + "," + lon + "&key=AIzaSyAg8JGCUKDz1XyDzSe7dF0VNKglIIGB7jc";

        StringRequest solGETCte = new StringRequest(Request.Method.GET, url, this, this) {
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
        Toast.makeText(con, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject objResponse = new JSONObject(response);
            JSONArray arrResult = objResponse.getJSONArray("results");
            JSONObject objForAdd = arrResult.getJSONObject(0);

            ubicacionActual = objForAdd.getString("formatted_address");
            mMap.addMarker(new MarkerOptions().position(latlng).title(ubicacionActual));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
