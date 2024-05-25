package com.satdroid.weather_app_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
private EditText inputCityname;
private String appid="bde3c02a4f6558a54cebdcee6c307e22";
private String url="https://api.openweathermap.org/data/2.5/weather";
//private String cityname="";
DecimalFormat df=new DecimalFormat("#.##");
private DBhandler dBhandler;
//    https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
private AppCompatButton getWeatherBtn,fav_citybtn,dispfavcites_btn;
private TextView windtv,humiditytv, temptv, citytv,descriptiontv;
private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputCityname=findViewById(R.id.city_input);
        getWeatherBtn=findViewById(R.id.getWeather_btn);
        fav_citybtn=findViewById(R.id.favourite_citybtn);
        dispfavcites_btn=findViewById(R.id.save_Fav_cites_btn);
        progressBar=findViewById(R.id.pgbar);

        initialiseTextviews();
        dBhandler=new DBhandler(MainActivity.this);
        getWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    fetchWeather(inputCityname.getText().toString().trim());
            }
        });

        fav_citybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoFavourite();
            }
        });
        dispfavcites_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  ifavcities=new Intent(MainActivity.this,MyfavCities.class);
                startActivity(ifavcities);
                finish();
            }
        });
        Intent isaved=getIntent();
        String receivedCity=isaved.getStringExtra("CityName");
        if(TextUtils.isEmpty(receivedCity))
        {

        }
        else fetchWeather(receivedCity);

    }




    public void fetchWeather(String  cityname)
    {
        progressBar.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(cityname))
            Toast.makeText(MainActivity.this, "Enter city name", Toast.LENGTH_SHORT).show();
        else {
            String temp_url = url + "?q=" + cityname + "&appid=" + appid;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, temp_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String weatherDescription = jsonObject1.getString("description");
                        JSONObject jsonObjectmain = jsonObject.getJSONObject("main");
                        double temp = jsonObjectmain.getDouble("temp") - 273.15;
                        int humidity = jsonObjectmain.getInt("humidity");
                        JSONObject jsonObjectwuind = jsonObject.getJSONObject("wind");
                        Double windSpeed = jsonObjectwuind.getDouble("speed");
                        String CityName = jsonObject.getString("name");
                        windtv.setText(""+df.format(windSpeed).toString()+" m/s");
                        humiditytv.setText(""+humidity+" %");
                        temptv.setText(""+df.format(temp)+" C");
                        citytv.setText(""+CityName);
                        descriptiontv.setText(""+weatherDescription);
                        progressBar.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }
    }

    public void addtoFavourite()
    {
        String  favCity="";
        favCity=favCity+inputCityname.getText().toString().trim();
        if(TextUtils.isEmpty(favCity))
            Toast.makeText(MainActivity.this, "Enter city name", Toast.LENGTH_SHORT).show();
        else
        {
            dBhandler.addTofavourite(favCity);
            Toast.makeText(MainActivity.this, "City added to favourites", Toast.LENGTH_SHORT).show();
        }
    }
    public void initialiseTextviews()
    {
        windtv=findViewById(R.id.wind_data);
        humiditytv=findViewById(R.id.humidity_data);
        temptv=findViewById(R.id.temperature_data);
        citytv=findViewById(R.id.City_data);
        descriptiontv=findViewById(R.id.Description_data);
    }
}