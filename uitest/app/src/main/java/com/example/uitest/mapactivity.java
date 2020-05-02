package com.example.uitest;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class mapactivity extends FragmentActivity implements OnMapReadyCallback {
    String[] carrier = {"All Operators", "Jio", "Airtel","Vodafone"};
    String carriervalue;
    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    Intent intent;
    Spinner spinner;
    private GoogleMap mMap;
    public ArrayList<WeightedLatLng> list;
    public int map_ready= 0, data_ready = 0;
    DatabaseReference Collect_Data;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivity);
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setPrompt("Select Operator");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mapactivity.this, R.layout.style_spinner1, carrier);
        //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        Collect_Data = FirebaseDatabase.getInstance().getReference("users").child("users");

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onClick(View v) {

            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        intent = new Intent(mapactivity.this, mapactivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                      //  intent = new Intent(mapactivity.this, mapactivity.class);
                        //startActivity(intent);
                        break;
                    case 3:
                       // intent = new Intent(mapactivity.this, mapactivity.class);
                        //startActivity(intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }




    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentlocation = location;
                    Toast.makeText(getApplicationContext(), currentlocation.getLatitude() + "" + currentlocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.mMap);
                    mapFragment.getMapAsync(mapactivity.this);

                }
            }

        });
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng point = new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude());
        //mMap.animateCamera(CameraUpdateFactory.newLatLng(point));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 20));
        mMap.addMarker(new MarkerOptions().position(point).title("current location"));
    }

    private void addHeatmap() {
        int[] colors={
                Color.rgb(255,0,0),
                Color.rgb(255,255,0),
                Color.rgb(102,255,0),


        };
        float[] startPoints={0.1f,0.4f,0.7f};
        Gradient gradient=new Gradient(colors,startPoints);
        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .weightedData(list)
                .gradient(gradient)
                .radius(30)
                .opacity(0.9)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        TileOverlay mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    protected void onStart() {
        super.onStart();
        Collect_Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list = new ArrayList<WeightedLatLng>();
                    for (DataSnapshot data_val : dataSnapshot.getChildren()) {
                        String value = String.valueOf(data_val.getValue());
                        try {
                            JSONObject object = new JSONObject(value);
                            double lat = object.getDouble("latitude");

                            double lng = object.getDouble("longitude");
                            double intensity = object.getDouble("tag") + 1;
                            list.add(new WeightedLatLng(new LatLng(lat, lng), intensity));
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                addHeatmap();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}