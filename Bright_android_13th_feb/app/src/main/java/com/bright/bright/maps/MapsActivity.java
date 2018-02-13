package com.bright.bright.maps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.bright.bright.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private int businessToShow;
    private LatLng businesLat_Long;
    private String mapTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        businesLat_Long = new LatLng(-33.882742, 151.210938);
        mapTitle = "Sydney";

        /*
        switch (businessToShow) {
            case 0:
                businesLat_Long = new LatLng(-33.882742, 151.210938);
                mapTitle = "Reuben Hills";
                break;
            case 1:
                businesLat_Long = new LatLng(-33.732814, 151.219541);
                mapTitle = "Belrose Hotel";
                break;
            case 2:
                businesLat_Long = new LatLng(-33.796074, 151.285676);
                mapTitle = "Ora";
                break;
        }
        */


        mMap.addMarker(new MarkerOptions().position(businesLat_Long).title(mapTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(businesLat_Long));
    }
}
