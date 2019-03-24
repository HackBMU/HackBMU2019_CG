package com.example.sunejas.sihproject.NearbyClinicPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sunejas.sihproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AppLocationService appLocationService;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_maps3 );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        appLocationService = new AppLocationService(
                MapsActivity.this );
        Location gpsLocation = appLocationService
                .getLocation( LocationManager.GPS_PROVIDER );
        if (gpsLocation != null) {
            latitude = gpsLocation.getLatitude();
            longitude = gpsLocation.getLongitude();
            String result = "Latitude: " + gpsLocation.getLatitude() +
                    " Longitude: " + gpsLocation.getLongitude();
            Log.d( "Check1", result + "" );
        } else {
            showSettingsAlert();
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MapsActivity.this );
        alertDialog.setTitle( "SETTINGS" );
        alertDialog.setMessage( "Enable Location Provider! Go to settings menu?" );
        alertDialog.setPositiveButton( "Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                        MapsActivity.this.startActivity( intent );
                    }
                } );
        alertDialog.setNegativeButton( "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );
        alertDialog.show();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng( 13.658704, 79.486411);
        mMap.addMarker( new MarkerOptions().position( sydney ).title( "SVCE" ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.0f ) );

        mMap.addMarker( new MarkerOptions().position( new LatLng( 13.636420, 79.420220) ).title( "Indhira Clinic" ) );
        mMap.addMarker( new MarkerOptions().position( new LatLng( 13.629150, 79.427606 ) ).title( "Sree Hari Theja Medicals and Clinics" ) );
        mMap.addMarker( new MarkerOptions().position( new LatLng( 13.624209, 79.429719 ) ).title( "Apollo Clinic" ) );
        mMap.addMarker( new MarkerOptions().position( new LatLng( 13.636303, 79.417016 ) ).title( "Aesthetics Clinic" ) );
        mMap.addMarker( new MarkerOptions().position( new LatLng( 13.633716, 79.432379 ) ).title( "Partha Dental Clinic" ) );
        mMap.addMarker( new MarkerOptions().position( new LatLng( 13.639767, 79.427586 ) ).title( "MS Hospital" ) );

    }
}
