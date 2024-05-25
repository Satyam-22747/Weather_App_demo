package com.satdroid.weather_app_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MyfavCities extends AppCompatActivity {
    DBhandler dBhandler;
    ArrayList<favcitymodal> favcitymodalslist;
    RecyclerView rcvfavCities;
    FavCityAdapter favCityAdapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent iHome=new Intent(MyfavCities.this, MainActivity.class);
        startActivity(iHome);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfav_cities);

        dBhandler=new DBhandler(MyfavCities.this);
        rcvfavCities=findViewById(R.id.rcview_cities);

        favcitymodalslist=dBhandler.favcities();

        if(favcitymodalslist.isEmpty())
        {
            Toast.makeText(MyfavCities.this, "NO fav cities added", Toast.LENGTH_SHORT).show();
        }
        else {
            favCityAdapter=new FavCityAdapter(favcitymodalslist,MyfavCities.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyfavCities.this, RecyclerView.VERTICAL, false);
            rcvfavCities.setLayoutManager(linearLayoutManager);
            rcvfavCities.setAdapter(favCityAdapter);
        }


    }
}