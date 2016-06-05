package com.example.gavs9.sismos.Services;

import android.os.AsyncTask;


import com.example.gavs9.sismos.Entities.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by AlvaroLopez on 4/6/16.
 */
public class UsuarioService {

    public ArrayList<Usuario> get () throws ExecutionException, InterruptedException {

        ArrayList<Usuario> task = new AsyncTask<String, Integer, ArrayList<Usuario>>() {

        @Override
        protected  synchronized ArrayList<Usuario> doInBackground(String... params) {

            ArrayList<Usuario> usuarios = new ArrayList<>();
            Request<Usuario> req = new Request<>();
            try {

                JSONArray usuariosFromApi = req.get("https://jogal-api.herokuapp.com/api/usuarios");

                for(int i=0;i<usuariosFromApi.length();i++){

                    JSONObject obj = (JSONObject) usuariosFromApi.get(i);
                    Usuario us = new Usuario(obj);
                    usuarios.add(us);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return usuarios;
        }
    }.execute().get();

        return task;
    }

    public Usuario getById (String id) throws ExecutionException, InterruptedException {

        Usuario task = new AsyncTask<String, Integer, Usuario>() {
            @Override
            protected Usuario doInBackground(String... params) {
                Request req = new Request();
                JSONObject usuarioFromApi = req.getById("https://jogal-api.herokuapp.com/api/usuarios/"+params[0]);
                try {
                    return new Usuario(usuarioFromApi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute(id).get();

        return task;
    }

    public void add (HashMap<String,String> hm){

        AsyncTask<HashMap<String,String>, Integer,String> task = new AsyncTask<HashMap<String,String>,Integer,String>() {
            @Override
            protected String doInBackground(HashMap<String,String>... params) {
                Request req = new Request();
                HashMap<String,String> hm = params[0];
                req.post("https://jogal-api.herokuapp.com/api/usuarios",hm);
                return "";
            }
        }.execute(hm);
    }

    public void update(HashMap<String,String> hm, final String id){
        hm.put("id",id);
        AsyncTask<HashMap<String,String>, Integer,String> task = new AsyncTask<HashMap<String,String>,Integer,String>() {
            @Override
            protected String doInBackground(HashMap<String,String>... params) {

                Request req = new Request();
                HashMap<String,String> hm = params[0];
                req.post("https://jogal-api.herokuapp.com/api/usuarios/"+hm.get("id"),hm);
                return "";
            }
        }.execute(hm);
    }

    public void remove(String id){

        AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                Request req = new Request();
                req.delete("https://jogal-api.herokuapp.com/api/usuarios/"+params[0]);
                return"";
            }
        }.execute(id);

    }


    Usuario us;


}
