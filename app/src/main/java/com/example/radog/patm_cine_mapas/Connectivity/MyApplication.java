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
    private static String apellidos;
    private static String email;
    private static String username;
    private static String pass;
    private static int edad;
    private static String tarjeta;
    private static String token;

    //obtenido durante los mapas
    private static double latitud;
    private static double longitud;

    //obtenido durante las funciones
    private static int funcion_id;
    private static String hora;
    private static String hora_fin;
    private static String fecha;
    private static String fecha_fin;

    private static int pelicula_id;
    private static String pelicula_titulo;
    private static int sala_id;
    private static String sala_nombre;
    private static int sucursal_id;
    private static String pais;
    private static String ciudad;
    private static String direccion;

    //obtenido al seleccionar asiento
    private static int asiento_id;
    private static int columna;
    private static String fila;
    private static String tipo_pago = "Tarjeta"; //siempre sera con tarjeta

    private static int num_entradas = 1;
    private static int total = 79 * num_entradas;

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

    public void init() {
        latitud = longitud = 0f;
        funcion_id = pelicula_id = sala_id = sucursal_id = asiento_id = columna = 0;
        hora = hora_fin = fecha = fecha_fin = pelicula_titulo = sala_nombre = pais = ciudad = direccion = fila = null;
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

    public static String getHora() {
        return hora;
    }

    public static void setHora(String hora) {
        MyApplication.hora = hora;
    }

    public static String getHora_fin() {
        return hora_fin;
    }

    public static void setHora_fin(String hora_fin) {
        MyApplication.hora_fin = hora_fin;
    }

    public static String getFecha() {
        return fecha;
    }

    public static void setFecha(String fecha) {
        MyApplication.fecha = fecha;
    }

    public static String getFecha_fin() {
        return fecha_fin;
    }

    public static void setFecha_fin(String fecha_fin) {
        MyApplication.fecha_fin = fecha_fin;
    }

    public static String getTipo_pago() {
        return tipo_pago;
    }

    public static void setTipo_pago(String tipo_pago) {
        MyApplication.tipo_pago = tipo_pago;
    }

    public static int getColumna() {
        return columna;
    }

    public static void setColumna(int columna) {
        MyApplication.columna = columna;
    }

    public static String getFila() {
        return fila;
    }

    public static void setFila(String fila) {
        MyApplication.fila = fila;
    }

    public static int getNum_entradas() {
        return num_entradas;
    }

    public static void setNum_entradas(int num_entradas) {
        MyApplication.num_entradas = num_entradas;
    }

    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        MyApplication.total = total;
    }

    public static String getApellidos() {
        return apellidos;
    }

    public static void setApellidos(String apellidos) {
        MyApplication.apellidos = apellidos;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        MyApplication.email = email;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MyApplication.username = username;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        MyApplication.pass = pass;
    }

    public static int getEdad() {
        return edad;
    }

    public static void setEdad(int edad) {
        MyApplication.edad = edad;
    }

    public static String getTarjeta() {
        return tarjeta;
    }

    public static void setTarjeta(String tarjeta) {
        MyApplication.tarjeta = tarjeta;
    }
}
