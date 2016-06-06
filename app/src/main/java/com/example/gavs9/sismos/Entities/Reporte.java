package com.example.gavs9.sismos.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by AlvaroLopez on 4/6/16.
 */
public class Reporte {

    public Reporte(String _id,String latitud, String longitud, String descripcion, String fecha, String tipo) {
        this._id = _id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Reporte(String latitud, String longitud, String descripcion, String tipo) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public Reporte() {}

    public Reporte(JSONObject obj) throws JSONException {
        this._id = obj.getString("_id");
        this.latitud = obj.getString("lat");
        this.longitud = obj.getString("lng");
        this.fecha = obj.getString("fecha");
        this.descripcion = obj.getString("descripcion");
        this.tipo = obj.getString("tipo");
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hm.put("descripcion",descripcion);
        hm.put("tipo",tipo);
        return hm;
    }

    String latitud;
    String longitud;
    String descripcion;
    String fecha;
    String tipo;
    String _id;
}
