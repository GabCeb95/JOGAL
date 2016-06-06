package com.example.gavs9.sismos.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AlvaroLopez on 5/6/16.
 */
public class Clima {


    public Clima(JSONObject objClima) throws JSONException {
        this.clima = objClima.getString("text");
        this.temperatura = objClima.getInt("temp");
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    private String clima;
    private float temperatura;
}
