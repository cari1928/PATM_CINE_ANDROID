package com.example.radog.patm_cine_mapas.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.radog.patm_cine_mapas.TDA.TDACategoria;
import com.example.radog.patm_cine_mapas.TDA.TDAColaborador;
import com.example.radog.patm_cine_mapas.TDA.TDAFuncion;
import com.example.radog.patm_cine_mapas.TDA.TDAPelicula;
import com.example.radog.patm_cine_mapas.TDA.TDASala;
import com.example.radog.patm_cine_mapas.TDA.TDASucursal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radog on 18/05/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    //DATABASE - GENERAL INFORMATION
    private static final String DB_NAME = "cine.db";
    private static final int VERSION = 3;

    /**
     * TABLE Persona - STRUCTURE
     */
    public static final String TABLE_PERSONA = "persona"; //table name
    public static final String PERSONA_ID = "persona_id";
    public static final String NOMBRE = "nombre";
    public static final String APELLIDOS = "apellidos";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String PASS = "pass";
    public static final String EDAD = "edad";
    public static final String TARJETA = "tarjeta";

    /**
     * TABLE Bitacora - STRUCTURE
     */
    public static final String TABLE_BITACORA = "bitacora"; //table name
    public static final String BITACORA_ID = "bitacora_id";
    public static final String TOKEN = "token";
    public static final String F_INI = "f_ini";
    public static final String F_FINAL = "f_final";

    /**
     * TABLE Categoria - STRUCTURE
     */
    public static final String TABLE_CATEGORIA = "categoria"; //table name
    public static final String CATEGORIA_ID = "categoria_id";
    public static final String CATEGORIA = "categoria";

    /**
     * TABLE Colaborador - STRUCTURE
     */
    public static final String TABLE_COLABORADOR = "colaborador"; //table name
    public static final String COLABORADOR_ID = "colaborador_id";

    /**
     * TABLE Pelicula - STRUCTURE
     */
    public static final String TABLE_PELICULA = "pelicula"; //table name
    public static final String PELICULA_ID = "pelicula_id";
    public static final String TITULO = "titulo";
    public static final String DESCRIPCION = "descripcion";
    public static final String F_LANZAMIENTO = "f_lanzamiento";
    public static final String LENGUAJE = "lenguaje";
    public static final String DURACION = "duracion";
    public static final String POSTER = "poster";

    /**
     * TABLE Categoria-Pelicula - STRUCTURE
     */
    public static final String TABLE_CATEGORIA_PELICULA = "categoria_pelicula_id"; //table name
    public static final String CATEGORIA_PELICULA_ID = "categoria_pelicula_id";

    /**
     * TABLE Reparto - STRUCTURE
     */
    public static final String TABLE_REPARTO = "reparto"; //table name
    public static final String REPARTO_ID = "reparto_id";
    public static final String PUESTO = "puesto";

    /**
     * TABLE Sucursal - STRUCTURE
     */
    public static final String TABLE_SUCURSAL = "sucursal"; //table name
    public static final String SUCURSAL_ID = "sucursal_id";
    public static final String PAIS = "pais";
    public static final String CIUDAD = "ciudad";
    public static final String DIRECCION = "direccion";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";

    /**
     * TABLE Sala - STRUCTURE
     */
    public static final String TABLE_SALA = "sala"; //table name
    public static final String SALA_ID = "sala_id";
    //public static final String NOMBRE = "nombre";
    //public static final String SALA_SUCURSAL_ID = "sucursal_id";
    public static final String NUMERO_SALA = "numero_sala";

    /**
     * TABLE Funcion - STRUCTURE
     */
    public static final String TABLE_FUNCION = "funcion"; //table name
    public static final String FUNCION_ID = "funcion_id";
    public static final String FECHA = "fecha";
    public static final String HORA = "hora";
    public static final String FECHA_FIN = "fecha_fin";
    public static final String HORA_FIN = "hora_fin";

    /**
     * TABLE Compra - STRUCTURE
     */
    public static final String TABLE_COMPRA = "compra"; //table name
    public static final String COMPRA_ID = "compra_id";
    public static final String CLIENTE_ID = "cliente_id";
    public static final String TOTAL = "total";
    public static final String ENTRADAS = "entradas";
    public static final String TIPO_PAGO = "tipo_pago";

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
            + PELICULA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITULO + " VARCHAR(100), "
            + DESCRIPCION + " TEXT, "
            + F_LANZAMIENTO + " TEXT, "
            + LENGUAJE + " VARCHAR(50), "
            + DURACION + " INTEGER, "
            + POSTER + " VARCHAR(50) );";

    //TO CREATE TABLE Categoria
    private static final String CREATE_CATEGORIA = "CREATE TABLE " + TABLE_CATEGORIA + " ( "
            + CATEGORIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CATEGORIA + " VARCHAR(80) );";

    //TO CREATE TABLE Colaborador
    private static final String CREATE_COLABORADOR = "CREATE TABLE " + TABLE_COLABORADOR + " ( "
            + COLABORADOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOMBRE + " VARCHAR(50), "
            + APELLIDOS + " VARCHAR(80) );";

    //TO CREATE TABLE Categoria-Pelicula
    private static final String CREATE_CATEGORIA_PELICULA = "CREATE TABLE " + TABLE_CATEGORIA_PELICULA + " ( "
            + CATEGORIA_PELICULA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PELICULA_ID + " INTEGER, "
            + CATEGORIA_ID + " INTEGER, "
            + "FOREIGN KEY(" + CATEGORIA_ID + ") REFERENCES " + TABLE_CATEGORIA + "(" + CATEGORIA_ID + "), "
            + "FOREIGN KEY(" + PELICULA_ID + ") REFERENCES " + TABLE_PELICULA + "(" + PELICULA_ID + ")  );";

    //TO CREATE TABLE Colaborador
    private static final String CREATE_REPARTO = "CREATE TABLE " + TABLE_REPARTO + " ( "
            + REPARTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLABORADOR_ID + " INTEGER, "
            + PELICULA_ID + " INTEGER, "
            + PUESTO + " VARCHAR(20), "
            + "FOREIGN KEY(" + COLABORADOR_ID + ") REFERENCES " + TABLE_COLABORADOR + "(" + COLABORADOR_ID + "), "
            + "FOREIGN KEY(" + PELICULA_ID + ") REFERENCES " + TABLE_PELICULA + "(" + PELICULA_ID + ") );";

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

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSONA);
        db.execSQL(CREATE_BITACORA);
        db.execSQL(CREATE_CATEGORIA);
        db.execSQL(CREATE_COLABORADOR);
        db.execSQL(CREATE_PELICULA);
        db.execSQL(CREATE_CATEGORIA_PELICULA);
        db.execSQL(CREATE_REPARTO);
        db.execSQL(CREATE_SUCURSAL);
        db.execSQL(CREATE_SALA);
        db.execSQL(CREATE_FUNCION);
        db.execSQL(CREATE_COMPRA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BITACORA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLABORADOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELICULA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA_PELICULA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPARTO);
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
            objCV.put(fields[i], data[i]);
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
            objCV.put(fields[i], data[i]);
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

    public List<TDACategoria> select(String query, TDACategoria tda) {
        List<TDACategoria> registers = new ArrayList<>();
        TDACategoria tdaCategoria;

        Cursor c = myDB.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                tdaCategoria = new TDACategoria();
                tdaCategoria.setCategoria_id(c.getInt(0));
                tdaCategoria.setCategoria(c.getString(1));

                registers.add(tdaCategoria);
            } while (c.moveToNext());
        } else {
            return null;
        }
        return registers;
    }

    public List<TDAColaborador> select(String query, TDAColaborador tda) {
        List<TDAColaborador> registers = new ArrayList<>();
        TDAColaborador tdaColaborador;

        Cursor c = myDB.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                tdaColaborador = new TDAColaborador();
                tdaColaborador.setColaborador_id(c.getInt(0));
                tdaColaborador.setNombre(c.getString(1));
                tdaColaborador.setApellidos(c.getString(2));
                registers.add(tdaColaborador);
            } while (c.moveToNext());
        } else {
            return null;
        }
        return registers;
    }

    public List<TDAPelicula> select(String query, TDAPelicula tda) {
        List<TDAPelicula> registers = new ArrayList<>();
        TDAPelicula tdaPeli;

        Cursor c = myDB.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                tdaPeli = new TDAPelicula();
                tdaPeli.setPelicula_id(c.getInt(0));
                tdaPeli.setTitulo(c.getString(1));
                tdaPeli.setDescripcion(c.getString(2));
                tdaPeli.setF_lanzamiento(c.getString(3));
                tdaPeli.setLenguaje(c.getString(4));
                tdaPeli.setDuracion(c.getInt(5));
                tdaPeli.setPoster(c.getString(6));

                registers.add(tdaPeli);
            } while (c.moveToNext());
        } else {
            return null;
        }
        return registers;
    }

    public List<TDAFuncion> select(String query, TDAFuncion tda) {
        List<TDAFuncion> registers = new ArrayList<>();
        TDAFuncion tdaFun;

        Cursor c = myDB.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                tdaFun = new TDAFuncion();
                tdaFun.setFuncion_id(c.getInt(0));
                tdaFun.setPelicula_id(c.getInt(1));
                tdaFun.setSala_id(c.getInt(2));
                tdaFun.setFecha(c.getString(3));
                tdaFun.setHora(c.getString(4));
                tdaFun.setFecha_fin(c.getString(5));
                tdaFun.setHora_fin(c.getString(6));

                registers.add(tdaFun);
            } while (c.moveToNext());
        } else {
            return null;
        }
        return registers;
    }

    public List<TDASala> select(String query, TDASala tda) {
        List<TDASala> registers = new ArrayList<>();
        TDASala tdaSala;

        Cursor c = myDB.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                tdaSala = new TDASala();
                tdaSala.setSala_id(c.getInt(0));
                tdaSala.setNombre(c.getString(1));
                tdaSala.setSucursal_id(c.getInt(2));
                tdaSala.setNumero_sala(c.getInt(3));

                registers.add(tdaSala);
            } while (c.moveToNext());
        } else {
            return null;
        }
        return registers;
    }

    public List<TDASucursal> select(String query, TDASucursal tda) {
        List<TDASucursal> registers = new ArrayList<>();
        TDASucursal tdaSucursal;

        Cursor c = myDB.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                tdaSucursal = new TDASucursal();
                tdaSucursal.setSucursal_id(c.getInt(0));
                tdaSucursal.setPais(c.getString(1));
                tdaSucursal.setCiudad(c.getString(2));
                tdaSucursal.setDireccion(c.getString(3));
                tdaSucursal.setLatitud(c.getFloat(4));
                tdaSucursal.setLongitud(c.getFloat(5));

                registers.add(tdaSucursal);
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
