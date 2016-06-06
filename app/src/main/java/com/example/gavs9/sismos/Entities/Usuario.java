package com.example.gavs9.sismos.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by AlvaroLopez on 4/6/16.
 */
public class Usuario {


    public Usuario(String _id,String username, String password, String nombre) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }

    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }

    public Usuario(){}

    public Usuario(JSONObject obj) throws JSONException {
        this._id = obj.getString("_id");
        this.username = obj.getString("usuario");
        this.password = obj.getString("password");
        this.nombre = obj.getString("nombre");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<String,String> getHashMap(){
        HashMap<String,String> hm = new HashMap<>();
        hm.put("usuario",username);
        hm.put("password",password);
        hm.put("nombre",nombre);
        return hm;
    }

    String username;
    String password;
    String nombre;
    String _id;


}
