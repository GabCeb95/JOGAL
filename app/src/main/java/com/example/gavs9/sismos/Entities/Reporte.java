package com.example.gavs9.sismos.Entities;

import org.json.JSONException;
import org.json.JSONObject;

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

    String latitud;
    String longitud;
    String descripcion;
    String fecha;
    String tipo;
    String _id;
}
