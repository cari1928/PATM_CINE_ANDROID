package com.example.radog.patm_cine_mapas.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.example.radog.patm_cine_mapas.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class SucursalMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Marker marcador = null;
    double latMarca, lonMarca;
    private Places objOL;
    private List<Marker> marcas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursal_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        marcas = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double latitud, longitud;
        mMap.setOnMapClickListener(this);
        Geolocation objGeo = new Geolocation(this);

        //obtiene posición actual
        latitud = objGeo.getLatActual();
        longitud = objGeo.getLongActual();

        Toast.makeText(this, "latitud: " + latitud + "\nLongitud: " + longitud, Toast.LENGTH_SHORT).show();

        LatLng aquiEstoy = new LatLng(latitud, longitud);

        Geocode objG = new Geocode(this, mMap, aquiEstoy);
        objG.getPlace(latitud, longitud);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        latMarca = latLng.latitude;
        lonMarca = latLng.longitude;
        setMarca();

        objOL = new Places(this, mMap, marcas);
        //objOL.getNokia(latMarca, lonMarca);
        objOL.getCinemas(latMarca, lonMarca);
    }

    private void setMarca() {
        LatLng coordenada = new LatLng(latMarca, lonMarca);
        CameraPosition camara = new CameraPosition.Builder().target(coordenada).zoom(15).build();
        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camara);
        mMap.animateCamera(camUpd);

        if (marcador != null) {
            marcador.remove();
        }

        marcador = mMap.addMarker(new MarkerOptions().position(coordenada).title("Ubicación Seleccionada").icon(BitmapDescriptorFactory.fromResource(R.mipmap.location)));
    }
}
