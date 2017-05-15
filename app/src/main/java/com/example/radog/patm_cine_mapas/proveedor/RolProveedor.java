package com.example.radog.patm_cine_mapas.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.radog.patm_cine_mapas.constantes.G;
import com.example.radog.patm_cine_mapas.pojos.Bitacora;
import com.example.radog.patm_cine_mapas.pojos.Rol;

/**
 * Created by radog on 14/05/2017.
 */

public class RolProveedor {

    /**
     * @param resolver ContentResolver para saber de donde viene o cual es el proveedor de contenido
     * @param rol      Rol que contiene los datos correspondientes
     */
    public static void insertRecord(ContentResolver resolver, Rol rol) {
        Uri uri = Contrato.Rol.CONTENT_URI;

        //para ir guardando los valores, como un bundle
        ContentValues values = new ContentValues();
        //clave, valor
        values.put(Contrato.Rol.ROL, rol.getRol());

        //permite hacer una inserción en el proveedor de contenido
        resolver.insert(uri, values);
    }

    public static void insertRecordLog(ContentResolver resolver, Rol rol) {
        Bitacora log = new Bitacora();
        log.setROL_ID(rol.getID());
        log.setOperacion(G.INSERT_OPERATION);

        //resolver.insert()
    }

    public static void deleteRecord(ContentResolver resolver, int rolId) {
        Uri uri = Uri.parse(Contrato.Rol.CONTENT_URI + "/" + rolId); //content://.../Rol/#
        resolver.delete(uri, null, null);
    }

    public static void updateRecord(ContentResolver resolver, Rol rol) {
        Uri uri = Uri.parse(Contrato.Rol.CONTENT_URI + "/" + rol.getID()); //content://.../Rol/#

        //valores a actualizar
        ContentValues values = new ContentValues();
        values.put(Contrato.Rol.ROL, rol.getRol());

        resolver.update(uri, values, null, null);
    }

    public static Rol readRecord(ContentResolver resolver, int rolId) {
        Uri uri = Uri.parse(Contrato.Rol.CONTENT_URI + "/" + rolId); //content://.../Rol/#
        String[] projection = {Contrato.Rol.ROL};

        Cursor cursor = resolver.query(uri, projection, null, null, null);
        //al menos hay uno
        if (cursor.moveToFirst()) {
            Rol rol = new Rol();
            rol.setID(rolId);
            rol.setRol(cursor.getString(cursor.getColumnIndex(Contrato.Rol.ROL)));
            return rol;
        }

        return null;
    }
}
