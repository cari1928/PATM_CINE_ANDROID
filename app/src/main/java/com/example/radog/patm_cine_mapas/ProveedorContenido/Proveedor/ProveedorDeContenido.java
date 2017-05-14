package com.example.radog.patm_cine_mapas.ProveedorContenido.Proveedor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;

/**
 * Created by radog on 13/05/2017.
 */

/**
 * Para usarlo solo se requiere mandar la URI
 */
public class ProveedorDeContenido extends ContentProvider {

    //para evitar este rollo = content://AUTHORITY/ROL/#ROL se usa:
    //uri para recoger un registro de una tabla
    private static final int ROL_ONE_REG = 1;
    //uri para recoger todos los registros
    private static final int ROL_ALL_REG = 2;

    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "cine.db";
    private static final int DATABASE_VERSION = 1; //se debe cambiar según la versión correspondiente
    private static final String ROL_TABLE_NAME = "rol"; //nombre de una tabla

    //indicates an invalid content uri
    public static final int INVALID_URI = -1;
    //Defines a helper object that matches content uris to table-specific parameters
    private static final UriMatcher sUriMatcher;
    //Stores the MIME types served by this provider
    private static final SparseArray<String> sMimeType;

    /**
     * Initialice meta data
     */
    static {
        sUriMatcher = new UriMatcher(0);
        sMimeType = new SparseArray<>();

        //urimatcher
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                ROL_TABLE_NAME,
                ROL_ALL_REG
        );
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                ROL_TABLE_NAME + "/#",
                ROL_ONE_REG
        );

        //mimetype
        sMimeType.put(
                ROL_ALL_REG,
                "vnd.android.cursor.dir/vnd." + Contrato.AUTHORITY + "." + ROL_TABLE_NAME
        );
        sMimeType.put(
                ROL_ALL_REG,
                "vnd.android.cursor.item/vnd." + Contrato.AUTHORITY + "." + ROL_TABLE_NAME
        );
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            //para tener integridad referencial
            db.execSQL("PRAGMA foreign_keys=ON;");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //create table on storage
            db.execSQL(
                    "CREATE TABLE " + ROL_TABLE_NAME + "(" +
                            Contrato.Rol.ROL_ID + " INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT," +
                            Contrato.Rol.ROL + " TEXT );"
            );

            inicializarDatos(db);
        }

        private void inicializarDatos(SQLiteDatabase db) {
            db.execSQL(
                    "INSERT INTO " + ROL_TABLE_NAME +
                            "(" + Contrato.Rol._ID + ", " + Contrato.Rol.ROL + ") " +
                            "VALUES (1, 'Cliente')"
            );
        }

        /**
         * Para cuando la versión de la BD es cambiada
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ROL_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
    }

    public void resetDatabase() {
        dbHelper.close();
        dbHelper = new DatabaseHelper(getContext());
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = null;

        switch (sUriMatcher.match(uri)) {
            case ROL_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Rol._ID + " = " + uri.getLastPathSegment();
                qb.setTables(ROL_TABLE_NAME);
                break;
            case ROL_ALL_REG:
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = Contrato.Rol._ID + " ASC";
                qb.setTables(ROL_TABLE_NAME);
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();
        String table = "";

        switch (sUriMatcher.match(uri)) {
            case ROL_ALL_REG:
                table = ROL_TABLE_NAME;
        }

        long rowId = sqlDB.insert(table, "", values);
        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(),
                    rowId
            ).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }

        throw new SQLException("Failed to insert into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        String table = "";

        switch (sUriMatcher.match(uri)) {
            case ROL_ONE_REG:
                //se indica la selección
                if (null == selection) selection = "";
                selection += Contrato.Rol._ID + " = " + uri.getLastPathSegment();
                table = ROL_TABLE_NAME;
                break;
            case ROL_ALL_REG:
                table = ROL_TABLE_NAME;
        }

        //selection = where
        int rows = sqlDB.delete(table, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }

        throw new SQLException("Failed to delete row into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        String table = "";

        switch (sUriMatcher.match(uri)) {
            case ROL_ONE_REG:
                //se indica la selección
                if (null == selection) selection = "";
                selection += Contrato.Rol._ID + " = " + uri.getLastPathSegment();
                table = ROL_TABLE_NAME;
                break;
            case ROL_ALL_REG:
                table = ROL_TABLE_NAME;
        }

        //selection = where
        int rows = sqlDB.update(table, values, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }

        throw new SQLException("Failed to update row into " + uri);
    }
}
