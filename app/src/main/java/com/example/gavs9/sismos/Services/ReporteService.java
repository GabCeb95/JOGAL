package com.example.gavs9.sismos.Services;

import android.os.AsyncTask;

import com.example.gavs9.sismos.Entities.Reporte;
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
public class ReporteService {

    public ArrayList<Reporte> get() throws ExecutionException, InterruptedException {

        ArrayList<Reporte> task = new AsyncTask<String, Integer, ArrayList<Reporte>>() {

        @Override
        protected ArrayList<Reporte> doInBackground(String... params) {

            ArrayList<Reporte> reportes = new ArrayList<>();
            Request<Sismo> req = new Request<>();
            try {

                JSONArray reportesFromApi = req.get("https://jogal-api.herokuapp.com/api/reportes");

                for(int i=0;i<reportesFromApi.length();i++){

                    JSONObject obj = (JSONObject) reportesFromApi.get(i);
                    Reporte rep = new Reporte(obj);
                    reportes.add(rep);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return reportes;
        }

        @Override
        protected void onPostExecute(ArrayList<Reporte> aVoid) {
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }.execute().get();
        return task;
    }

    public Reporte getById (String id) throws ExecutionException, InterruptedException {

        Reporte task = new AsyncTask<String, Integer, Reporte>() {
            @Override
            protected Reporte doInBackground(String... params) {
                Request req = new Request();
                String api ="https://jogal-api.herokuapp.com/api/reportes/"+params[0];
                JSONObject reporteFromApi = req.getById(api);

                try {
                    return new Reporte(reporteFromApi);
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
                req.post("https://jogal-api.herokuapp.com/api/reportes/",hm);
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
                req.post("https://jogal-api.herokuapp.com/api/reportes/"+hm.get("id"),hm);
                return "";
            }
        }.execute(hm);

    }

    public void remove(String id){

        AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                Request req = new Request();
                req.delete("https://jogal-api.herokuapp.com/api/reportes/"+params[0]);
                return"";
            }
        }.execute(id);

    }

    ArrayList<Reporte> r;
    Reporte re;
}
