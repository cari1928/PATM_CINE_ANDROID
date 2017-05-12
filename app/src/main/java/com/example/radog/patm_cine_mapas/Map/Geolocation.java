package com.example.radog.patm_cine_mapas.Map;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by radog on 12/05/2017.
 */

public class Geolocation {

    private boolean banRED = false;
    private boolean banGPS = false;
    private LocationManager adminLoc;
    private Location coordenada; //tiene latitud y longitud
    private double latActual, longActual;
    Context con;

    public Geolocation(Context con) {
        this.con = con;
        adminLoc = (LocationManager) con.getSystemService(LOCATION_SERVICE); //obtiene el admin de servicios por default del teléfono
        banRED = adminLoc.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        banGPS = adminLoc.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (banGPS) {
            getLocationByGPS();
        }
    }

    public void getLocationByGPS() {
        try {
            adminLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, new ListenerLocation());

            //en caso de que actualmente no hay localización
            //tomará la última conocida
            coordenada = adminLoc.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latActual = coordenada.getLatitude();
            longActual = coordenada.getLongitude();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public double getLatActual() {
        return latActual;
    }

    public double getLongActual() {
        return longActual;
    }
}
