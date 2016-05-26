package com.pkm.proyek.seismoalpha.maps;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pkm.proyek.seismoalpha.laporan.tim.InputActivity;
import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.pelapor.Pelapor;
import com.pkm.proyek.seismoalpha.rehabrekon.RehabRekonActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PickLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static LatLng position;
    public static String positionAddress;
    Geocoder geocoder;
    List<Address> addresses;
    TextView displayLokasi;
    Marker posisi;
    ProgressBar loading;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);

        bundle=getIntent().getExtras();
        loading=(ProgressBar)findViewById(R.id.loading);
        displayLokasi=(TextView)findViewById(R.id.position);
        geocoder = new Geocoder(this, Locale.getDefault());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUNDLE", String.valueOf(bundle.getBoolean("fromRehab")));
                if(bundle.getBoolean("fromRehab")){
                    startActivity(new Intent(getApplicationContext(),RehabRekonActivity.class));
                }else{
                    startActivity(new Intent(getApplicationContext(),InputActivity.class));
                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        loadingStart();
        if (position==null){
            position= Pelapor.akunIni.getLokasi();
        }
        Log.d("POSISI",position.toString());
        showLokasi(position);

        posisi=mMap.addMarker(new MarkerOptions().position(position).title("Lokasiku"));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                position=latLng;
                posisi.setPosition(latLng);
                showLokasi(latLng);


            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,10));
        loadingStop();
    }

    public String getAddressName(LatLng latLng){

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            addresses=new ArrayList<>();
            addresses.add(new Address(Locale.getDefault()));
            addresses.get(0).setAddressLine(0,"Geocoding Gagal");
            addresses.get(0).setLocality("");
            addresses.get(0).setAdminArea("");
            e.getStackTrace();
        }
        //country = addresses.get(0).getCountryName();
        //postalCode = addresses.get(0).getPostalCode();
        positionAddress=addresses.get(0).getAddressLine(0)+ ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
        return positionAddress;
    }

    public void showLokasi(LatLng latLng){
        //Mainkan Data disini
        String displayLoc=
                latLng.latitude+","+latLng.longitude+"\n"+
                        getAddressName(latLng);
        //loadingStop();
        displayLokasi.setText(displayLoc);


    }

    private void loadingStart(){
        loading.setVisibility(View.VISIBLE);
        displayLokasi.setVisibility(View.GONE);
    }
    private void loadingStop(){
        loading.setVisibility(View.GONE);
        displayLokasi.setVisibility(View.VISIBLE);
    }

}
