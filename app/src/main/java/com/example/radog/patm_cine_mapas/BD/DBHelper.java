package com.example.radog.patm_cine_mapas.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radog on 18/05/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    //DATABASE - GENERAL INFORMATION
    private static final String DB_NAME = "cine.db";
    private static final int VERSION = 1;

    /**
     * TABLE Persona - STRUCTURE
     */
    private static final String TABLE_PERSONA = "persona";
    //COLUMN NAMES
    private static final String PERSONA_ID = "persona_id";
    private static final String NOMBRE = "nombre";
    private static final String APELLIDOS = "apellidos";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASS = "pass";
    private static final String EDAD = "edad";
    private static final String TARJETA = "tarjeta";

    /**
     * TABLE Bitacora - STRUCTURE
     */
    private static final String TABLE_BITACORA = "bitacora";
    //COLUMN NAMES
    private static final String BITACORA_ID = "bitacora_id";
    //private static final String BITACORA_PERSONA_ID = "persona_id";
    private static final String TOKEN = "token";
    private static final String F_INI = "f_ini";
    private static final String F_FINAL = "f_final";

    /**
     * TABLE Pelicula - STRUCTURE
     */
    private static final String TABLE_PELICULA = "pelicula";
    //COLUMN NAMES
    private static final String CATEGORIA = "categoria";
    private static final String CATEGORIA_ID = "categoria_id";
    private static final String DESCRIPCION = "descripcion";
    private static final String DURACION = "duracion";
    private static final String F_LANZAMIENTO = "f_lanzamiento";
    private static final String LENGUAJE = "lenguaje";
    private static final String PELICULA_ID = "pelicula_id";
    private static final String POSTER = "poster";
    private static final String TITULO = "titulo";

    /**
     * TABLE Sucursal - STRUCTURE
     */
    private static final String TABLE_SUCURSAL = "sucursal";
    //COLUMN NAMES
    private static final String SUCURSAL_ID = "sucursal_id";
    private static final String PAIS = "pais";
    private static final String CIUDAD = "ciudad";
    private static final String DIRECCION = "direccion";
    private static final String LATITUD = "latitud";
    private static final String LONGITUD = "longitud";

    /**
     * TABLE Sala - STRUCTURE
     */
    private static final String TABLE_SALA = "sala";
    //COLUMN NAMES
    private static final String SALA_ID = "sala_id";
    //private static final String NOMBRE = "nombre";
    //private static final String SALA_SUCURSAL_ID = "sucursal_id";
    private static final String NUMERO_SALA = "numero_sala";

    /**
     * TABLE Funcion - STRUCTURE
     */
    private static final String TABLE_FUNCION = "funcion";
    //COLUMN NAMES
    private static final String FUNCION_ID = "funcion_id";
    //private static final String FUNCION_PELICULA_ID = "pelicula_id";
    //private static final String SALA_ID = "sala_id";
    private static final String FECHA = "fecha";
    private static final String HORA = "hora";
    private static final String FECHA_FIN = "fecha_fin";
    private static final String HORA_FIN = "hora_fin";

    /**
     * TABLE Compra - STRUCTURE
     */
    private static final String TABLE_COMPRA = "compra";
    //COLUMN NAMES
    private static final String COMPRA_ID = "compra_id";
    private static final String CLIENTE_ID = "cliente_id";
    //private static final String FUNCION_ID = "funcion_id";
    //private static final String FECHA = "fecha";
    private static final String TOTAL = "total";
    private static final String ENTRADAS = "entradas";
    private static final String TIPO_PAGO = "tipo_pago";

    //TO CREATE TABLE Persona
    private static final String CREATE_PERSONA = "CREATE TABLE " + TABLE_PERSONA + " ( "
            + PERSONA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NOMBRE + " VARCHAR(50), "
            + APELLIDOS + " VARCHAR(80), "
            + EMAIL + " VARCHAR(50), "
            + USERNAME + " VARCHAR(50), "
            + PASS + " VARCHAR(32), "
            + EDAD + " INTEGER, "
            + TARJETA + " VARCHAR(18) );";

    //TO CREATE TABLE Bitacora
    private static final String CREATE_BITACORA = "CREATE TABLE " + TABLE_BITACORA + " ( "
            + BITACORA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PERSONA_ID + " INTEGER, "
            + TOKEN + " VARCHAR(32), "
            + F_INI + " TEXT, "
            + F_FINAL + " TEXT, "
            + "FOREIGN KEY(" + PERSONA_ID + ") REFERENCES " + TABLE_PERSONA + "(" + PERSONA_ID + ") );";

    //TO CREATE TABLE Pelicula
    private static final String CREATE_PELICULA = "CREATE TABLE " + TABLE_PELICULA + " ( "
            + CATEGORIA + " VARCHAR(80),"
            + CATEGORIA_ID + " INTEGER, "
            + DESCRIPCION + " TEXT, "
            + DURACION + " INTEGER, "
            + F_LANZAMIENTO + " TEXT, "
            + LENGUAJE + " VARCHAR(50), "
            + PELICULA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + POSTER + " VARCHAR(50), "
            + TITULO + " VARCHAR(100) );";

    //TO CREATE TABLE Sucursal
    private static final String CREATE_SUCURSAL = "CREATE TABLE " + TABLE_SUCURSAL + " ( "
            + SUCURSAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PAIS + " VARCHAR(50), "
            + CIUDAD + " VARCHAR(50), "
            + DIRECCION + " VARCHAR(150), "
            + LATITUD + " FLOAT, "
            + LONGITUD + " FLOAT );";

    //TO CREATE TABLE Sala
    private static final String CREATE_SALA = "CREATE TABLE " + TABLE_SALA + " ( "
            + SALA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOMBRE + " VARCHAR(50), "
            + SUCURSAL_ID + " INTEGER, "
            + NUMERO_SALA + " INTEGER, "
            + "FOREIGN KEY(" + SUCURSAL_ID + ") REFERENCES " + TABLE_SUCURSAL + "(" + SUCURSAL_ID + ") );";

    //TO CREATE TABLE Funcion
    private static final String CREATE_FUNCION = "CREATE TABLE " + TABLE_FUNCION + " ( "
            + FUNCION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PELICULA_ID + " INTEGER, "
            + SALA_ID + " INTEGER, "
            + FECHA + " TEXT, "
            + HORA + " TEXT, "
            + FECHA_FIN + " TEXT, "
            + HORA_FIN + " TEXT, "
            + "FOREIGN KEY(" + PELICULA_ID + ") REFERENCES " + TABLE_PELICULA + "(" + PELICULA_ID + "), "
            + "FOREIGN KEY(" + SALA_ID + ") REFERENCES " + TABLE_SALA + "(" + SALA_ID + ") );";

    //TO CREATE TABLE Compra
    private static final String CREATE_COMPRA = "CREATE TABLE " + TABLE_COMPRA + " ( "
            + COMPRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CLIENTE_ID + " INTEGER, "
            + FUNCION_ID + " INTEGER, "
            + FECHA + " TEXT, "
            + TOTAL + " FLOAT, "
            + ENTRADAS + " INTEGER, "
            + TIPO_PAGO + " VARCHAR(20), "
            + "FOREIGN KEY(" + CLIENTE_ID + ") REFERENCES " + TABLE_PERSONA + "(" + PERSONA_ID + "), "
            + "FOREIGN KEY(" + FUNCION_ID + ") REFERENCES " + TABLE_FUNCION + "(" + FUNCION_ID + ") );";

    SQLiteDatabase myDB;

    /**
     * Persona
     * Bitacora
     * Pelicula
     * Sucursal
     * Sala
     * Funcion
     * Compra
     */

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSONA);
        db.execSQL(CREATE_BITACORA);
        db.execSQL(CREATE_PELICULA);
        db.execSQL(CREATE_SUCURSAL);
        db.execSQL(CREATE_SALA);
        db.execSQL(CREATE_FUNCION);
        db.execSQL(CREATE_COMPRA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BITACORA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELICULA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUCURSAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUNCION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPRA);
        onCreate(db);
    }

    public void openDB() {
        myDB = getWritableDatabase();
    }

    public void closeDB() {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long insert(String[] fields, String[] data, String table, boolean foreignK) {
        ContentValues objCV = new ContentValues();
        long res;

        for (int i = 0; i < fields.length; i++) {
            objCV.put(fields[i], data[0]);
        }

        if (foreignK) myDB.execSQL("PRAGMA foreign_keys=ON;");

        res = myDB.insert(table, null, objCV);

        if (foreignK) myDB.execSQL("PRAGMA foreign_keys=OFF;");

        return res;
    }

    public long update(String[] fields, String[] data, String where, String table) {
        ContentValues objCV = new ContentValues();
        //String where;

        for (int i = 0; i < fields.length; i++) {
            objCV.put(fields[i], data[0]);
        }

        //where = ID_EVENT + "=" + idEvent;

        return myDB.update(table, objCV, where, null);
    }

    public List<String> select(String query, int numCol) {
        List<String> registers = new ArrayList<>();

        Cursor c = myDB.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                for (int i = 0; i < numCol; i++) {
                    registers.add(c.getString(i));
                }
            } while (c.moveToNext());
        } else {
            return null;
        }

        return registers;
    }

    public long delete(String table, String where) {
        //String where = ID_EVENT + "=" + idEvent;
        return myDB.delete(table, where, null);
    }

}
