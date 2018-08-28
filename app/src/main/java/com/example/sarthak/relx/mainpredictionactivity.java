package com.example.sarthak.relx;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class mainpredictionactivity extends FragmentActivity implements OnMapReadyCallback {

    Spinner s1;
    Spinner s2;
    Spinner s3;
    JSONObject jsonObject;
    JSONArray jsonArray;
    JSONObject distance;
    JSONObject duration;
    LatLng start;
    LatLng end;
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpredictionactivity);
        String[] services={"Cab","Bus","Metro","Auto","Walk"};
        ArrayAdapter<String> adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,services);
        s1=(Spinner)findViewById(R.id.spinner1);
        s2=(Spinner)findViewById(R.id.spinner2);
        s3=(Spinner)findViewById(R.id.spinner3);
        s1.setAdapter(adapter);
        s2.setAdapter(adapter);
        s3.setAdapter(adapter);
        Intent i=getIntent();
        jsonObject=null;
        try {
            jsonObject=new JSONObject(i.getStringExtra("Response"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray=null;
        try {
            jsonArray=jsonObject.getJSONArray("legs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        distance=null;
        try {
            distance=jsonArray.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        duration=null;
        try {
            duration=jsonArray.getJSONObject(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        start=new LatLng(getIntent().getDoubleExtra("startlat",0.0),getIntent().getDoubleExtra("startlon",0.0));
        end=new LatLng(getIntent().getDoubleExtra("endlat",0.0),getIntent().getDoubleExtra("endlon",0.0));
        SupportMapFragment smf=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        smf.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.addMarker(new MarkerOptions().position(start).title("Start"));
        map.addMarker(new MarkerOptions().position(end).title("End"));

    }
    public void next3(View view)
    {
        Double dis=0.0;
        try {
            dis= Double.valueOf(distance.getInt("value")) /1000;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        double fare = dis*6;
        Intent i=new Intent(this,QRcode.class);
        i.putExtra("fare",fare);
        startActivity(i);

    }
}
