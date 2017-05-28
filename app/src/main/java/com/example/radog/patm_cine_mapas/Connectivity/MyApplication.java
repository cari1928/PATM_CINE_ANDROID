package com.example.radog.patm_cine_mapas.Connectivity;

import android.app.Application;

/**
 * Created by radog on 18/05/2017.
 * Called whenever app is launched
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static String persona_id;
    private static String token;
    private static double latitud;
    private static double longitud;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public static MyApplication getmInstance() {
        return mInstance;
    }

    public static void setmInstance(MyApplication mInstance) {
        MyApplication.mInstance = mInstance;
    }

    public static String getPersona_id() {
        return persona_id;
    }

    public static void setPersona_id(String persona_id) {
        MyApplication.persona_id = persona_id;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MyApplication.token = token;
    }

    public static double getLatitud() {
        return latitud;
    }

    public static void setLatitud(double latitud) {
        MyApplication.latitud = latitud;
    }

    public static double getLongitud() {
        return longitud;
    }

    public static void setLongitud(double longitud) {
        MyApplication.longitud = longitud;
    }
}
