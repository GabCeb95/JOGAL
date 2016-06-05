package com.example.gavs9.sismos.Services;

import android.os.AsyncTask;

import com.example.gavs9.sismos.Entities.Sismo;

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


public class SismoService {

    public ArrayList<Sismo> get () throws ExecutionException, InterruptedException {


        ArrayList<Sismo> task = new AsyncTask<String, Integer, ArrayList<Sismo>>() {
            @Override
            protected ArrayList<Sismo> doInBackground(String... params) {
                ArrayList<Sismo> sismos = new ArrayList<>();
                Request<Sismo> req = new Request<>();
                try {
                    JSONArray sismosFromApi = req.get("https://jogal-api.herokuapp.com/api/reportes");
                    for(int i=0;i<sismosFromApi.length();i++){
                        JSONObject obj = (JSONObject) sismosFromApi.get(i);
                        sismos.add(new Sismo(obj));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return sismos;
            }

            @Override
            protected void onPostExecute(ArrayList<Sismo> aVoid) {

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.execute().get();
      return task;
    }

    public Sismo getById (String id) throws ExecutionException, InterruptedException {

        Sismo task = new AsyncTask<String, Integer, Sismo>() {
            @Override
            protected Sismo doInBackground(String... params) {
                Request req = new Request();
                JSONObject sismoFromApi = req.getById("https://jogal-api.herokuapp.com/api/reportes/"+params[0]);
                try {
                    return new Sismo(sismoFromApi);
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
                req.post("https://jogal-api.herokuapp.com/api/sismos",hm);
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
                req.post("https://jogal-api.herokuapp.com/api/sismos/"+hm.get("id"),hm);
                return "";
            }
        }.execute(hm);

    }

    public void remove(String id){

        AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                Request req = new Request();
                req.delete("https://jogal-api.herokuapp.com/api/sismos/"+params[0]);
                return"";
            }
        }.execute(id);

    }

}
