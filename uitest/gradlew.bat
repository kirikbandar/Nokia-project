package com.example.heatmap_retro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        Api api = retrofit.create(Api.class);
        Call<ArrayList<Number>> call = api.getvals();
        call.enqueue(new Callback<ArrayList<Number>>() {
            @Override
            public void onResponse(Call<ArrayList<Number>> call, Response<ArrayList<Number>> response) {

                ArrayList<Number> list = response.body();
                String[] inputStream = new String[list.size()];
                String json = new Scanner(String.valueOf(inputStream)).useDelimiter("\\A").next();
                JSONObject jsonObject = new JSONObject(json);
                for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                    String i = it.next();
                    JSONObject object = jsonObject.getJSONObject(i);
                    double lat = object.getDouble("latitude");
                    Log.i("Heatmap", String.valueOf(lat));
                    double lng = object.getDouble("longitude");
                    double intensity = object.getDouble("tag") + 1;
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng))
                    // .title(String.valueOf(intensity)));
                    list.add(new Number(new LatLng(lat, lng), intensity));
                }

            }
        }
