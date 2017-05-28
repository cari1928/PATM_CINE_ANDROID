package com.example.radog.patm_cine_mapas.Connectivity;

import android.app.Application;

/**
 * Created by radog on 18/05/2017.
 * Called whenever app is launched
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;

    // obtenido durante el login
    private static String persona_id;
    private static String persona_nombre;
    private static String token;

    //obtenido durante los mapas
    private static double latitud;
    private static double longitud;

    //obtenido durante las funciones
    private static int funcion_id;
    private static int pelicula_id;
    private static String pelicula_titulo;
    private static int sala_id;
    private static String sala_nombre;
    private static int sucursal_id;
    private static String pais;
    private static String ciudad;
    private static String direccion;

    private static int asiento_id;

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

    public int getFuncion_id() {
        return funcion_id;
    }

    public void setFuncion_id(int funcion_id) {
        this.funcion_id = funcion_id;
    }

    public int getAsiento_id() {
        return asiento_id;
    }

    public void setAsiento_id(int asiento_id) {
        this.asiento_id = asiento_id;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }

    public static String getPersona_nombre() {
        return persona_nombre;
    }

    public static void setPersona_nombre(String persona_nombre) {
        MyApplication.persona_nombre = persona_nombre;
    }

    public static int getPelicula_id() {
        return pelicula_id;
    }

    public static void setPelicula_id(int pelicula_id) {
        MyApplication.pelicula_id = pelicula_id;
    }

    public static String getPelicula_titulo() {
        return pelicula_titulo;
    }

    public static void setPelicula_titulo(String pelicula_titulo) {
        MyApplication.pelicula_titulo = pelicula_titulo;
    }

    public static String getSala_nombre() {
        return sala_nombre;
    }

    public static void setSala_nombre(String sala_nombre) {
        MyApplication.sala_nombre = sala_nombre;
    }

    public static int getSucursal_id() {
        return sucursal_id;
    }

    public static void setSucursal_id(int sucursal_id) {
        MyApplication.sucursal_id = sucursal_id;
    }

    public static String getPais() {
        return pais;
    }

    public static void setPais(String pais) {
        MyApplication.pais = pais;
    }

    public static String getCiudad() {
        return ciudad;
    }

    public static void setCiudad(String ciudad) {
        MyApplication.ciudad = ciudad;
    }

    public static String getDireccion() {
        return direccion;
    }

    public static void setDireccion(String direccion) {
        MyApplication.direccion = direccion;
    }
}
