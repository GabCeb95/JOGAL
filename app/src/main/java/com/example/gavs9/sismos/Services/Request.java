package com.example.gavs9.sismos.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AlvaroLopez on 4/6/16.
 */
public class Request<T> {


    public JSONArray get(String apiUrl) throws MalformedURLException {

        JSONArray arr = null;
        HttpURLConnection connection =null;
        URL url = null;
        try {
            url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            connection.setRequestMethod("GET");
            int respuesta = connection.getResponseCode();

            StringBuilder result = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK){

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONArray respuestaJSON = new JSONArray(result.toString());
                arr = respuestaJSON;
                return arr;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public JSONArray getGeo(String apiUrl)throws MalformedURLException {

        JSONArray arr = null;
        HttpURLConnection connection =null;
        URL url = null;
        try {
            url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            connection.setRequestMethod("GET");
            int respuesta = connection.getResponseCode();

            StringBuilder result = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK){

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject respuestaJSON = new JSONObject(result.toString());
                JSONArray resultJSON = respuestaJSON.getJSONArray("geonames");
                return resultJSON;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arr;

    }

    public String getEarthquakes(String apiUrl)throws MalformedURLException {

        String arr = null;
        HttpURLConnection connection =null;
        URL url = null;
        try {
            url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            connection.setRequestMethod("GET");
            int respuesta = connection.getResponseCode();

            StringBuilder result = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK){

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;

    }

    public JSONObject getById(String apiUrl){
        HttpURLConnection connection =null;
        URL url = null;
        try {
            url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            connection.setRequestMethod("GET");
            int respuesta = connection.getResponseCode();

            StringBuilder result = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK){

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JSONObject respuestaJSON = new JSONObject(result.toString());
                return respuestaJSON;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void post(String apiUrl, HashMap<String, String> hm){

        HttpURLConnection connection =null;
        URL url = null;
        try {

            url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(getQueryData(hm));
            bw.flush();
            bw.close();

            if(!(connection.getResponseCode() == HttpURLConnection.HTTP_CREATED)){
                //error
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String apiUrl, HashMap<String, String> hm){

        HttpURLConnection connection =null;
        URL url = null;
        try {

            url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");

            OutputStream os = connection.getOutputStream();
            BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            hm.remove("id");
            bw.write(getQueryData(hm));
            bw.flush();
            bw.close();

            if(!(connection.getResponseCode() == HttpURLConnection.HTTP_CREATED)){
                //error
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void delete(String apiUrl){

        HttpURLConnection connection =null;
        URL url = null;
        try {
            url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            connection.setDoOutput(true);
            connection.setRequestMethod("DELETE");

            if(!(connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT)){
                //error
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getQueryData(HashMap<String,String> data) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String,String> entry:data.entrySet()){
            if(first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }

        return result.toString();
    }

}
