package com.example.gavs9.sismos.Services;

import android.os.AsyncTask;
import android.view.View;

import com.example.gavs9.sismos.Entities.Clima;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by AlvaroLopez on 5/6/16.
 */
public class WeatherService {


    public Clima getWeather() throws ExecutionException, InterruptedException {

        Clima clima = new AsyncTask<String, Integer, Clima>() {

            @Override
            protected synchronized Clima doInBackground(String... params) {
                String endpoint = "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20%3D%2026809172%20and%20u%3D%22c%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    JSONObject data = new JSONObject(result.toString());
                    JSONObject queryResults = data.optJSONObject("query");
                    int count = queryResults.optInt("count");
                    if (count == 0) {
                        //error
                        return null;
                    }
                    JSONObject objClima = queryResults.optJSONObject("results").optJSONObject("channel").optJSONObject("item").optJSONObject("condition");
                    Clima clima = new Clima(objClima);
                    return clima;
                } catch (Exception e) {
                    //error
                }
                return null;
            }
            }.execute().get();
            return clima;
    }

}


