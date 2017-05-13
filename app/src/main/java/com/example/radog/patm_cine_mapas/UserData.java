package com.example.radog.patm_cine_mapas;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by radog on 13/05/2017.
 */

public class UserData implements Parcelable {

    String nombre;
    String contenido;

    public UserData(String nombre, String contenido) {
        this.nombre = nombre;
        this.contenido = contenido;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(contenido);
    }

    public String getNombre() {
        return nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public UserData(Parcel in) {
        nombre = in.readString();
        contenido = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}
