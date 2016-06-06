package com.example.gavs9.sismos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gavs9.sismos.Entities.Clima;
import com.example.gavs9.sismos.Services.WeatherService;

import java.util.concurrent.ExecutionException;

public class ClimaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clima);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        try {
            establecerClima();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


public void establecerClima() throws ExecutionException, InterruptedException {


        WeatherService ws = new WeatherService();
        Clima clima = ws.getWeather();

        TextView txClima = (TextView) findViewById(R.id.txtClima);
        TextView txTemp = (TextView) findViewById(R.id.txtTemp);
        ImageView imgClima = (ImageView) findViewById(R.id.imgClima);

        String cli = clima.getClima().toLowerCase();
        txClima.setText(clima.getClima());
        txTemp.setText(Float.toString(clima.getTemperatura()));

        if (cli.contains("cloud")){
            imgClima.setImageResource(R.drawable.cloud);}
        else
        if (cli.contains("sun")){
            imgClima.setImageResource(R.drawable.sun);}
        else
        if (cli.contains("wind")){
            imgClima.setImageResource(R.drawable.wind);}
        else
        if (cli.contains("thun")){
            imgClima.setImageResource(R.drawable.thunder);}
        else
        if (cli.contains("rain")){
            imgClima.setImageResource(R.drawable.rain);}
        else
            imgClima.setImageResource(R.drawable.normal);
    }
  }