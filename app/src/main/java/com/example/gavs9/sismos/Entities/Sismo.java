package com.example.gavs9.sismos.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by AlvaroLopez on 4/6/16.
 */
public class Sismo {


    public Sismo(String _id,String latitud, String longitud, String magnitud, String epicentro, String fecha) {
        this._id = _id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.magnitud = magnitud;
        this.epicentro = epicentro;
        this.fecha = fecha;
    }

    public Sismo(String latitud, String longitud, String magnitud, String epicentro) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.magnitud = magnitud;
        this.epicentro = epicentro;
    }

    public Sismo() {}

    public Sismo(JSONObject obj) throws JSONException {
        this._id = obj.getString("_id");
        this.latitud = obj.getString("lat");
        this.longitud = obj.getString("lng");
        this.fecha = obj.getString("fecha");
        this.magnitud = obj.getString("magnitud");
    }

     public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(String magnitud) {
        this.magnitud = magnitud;
    }

    public String getEpicentro() {
        return epicentro;
    }

    public void setEpicentro(String epicenctr) {
        this.epicentro = epicenctr;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public HashMap<String,String> getHashMap(){
        HashMap<String,String> hm = new HashMap<>();
        hm.put("lat",latitud);
        hm.put("lng",longitud);
        hm.put("descripcion",magnitud);
        hm.put("tipo",epicentro);
        return hm;
    }

    String latitud;
    String longitud;
    String magnitud;
    String epicentro;
    String fecha;
    String _id;
}
