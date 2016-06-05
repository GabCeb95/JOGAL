package com.example.gavs9.sismos;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NavigationActivity extends Base
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {

    ObtenerWebService hiloconexion;
    private GoogleMap mMap;
    AlertDialog alert = null;
    private LocationManager locManager;
    private LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), ReporteActivity.class);
                startActivity(intento);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        // *****************************************++++

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mapa) {
            // Handle the camera action
        } else if (id == R.id.nav_sismos_oficiales) {
            Intent intento = new Intent(getApplicationContext(), SismosOficialesActivity.class);
            startActivity(intento);
        } else if (id == R.id.nav_sismos_reportados) {
            Intent intento = new Intent(getApplicationContext(), SismosReportadosActivity.class);
            startActivity(intento);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                // Comienza a crear los marcadores desde Geonames
                hiloconexion = new ObtenerWebService();
                //hiloconexion.execute("paises");
                hiloconexion.execute(Double.toString(latLng.latitude),Double.toString(latLng.longitude));

            }
        });


    }


    public class ObtenerWebService extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String devuelve = "";
            double latitud = Double.parseDouble(params[0]);
            double longitud = Double.parseDouble(params[1]);

            try {
                    //String cadena = "http://api.geonames.org/earthquakesJSON?formatted=true&north=11.216819&south=8.032975&east=-82.555992&west=-85.950623&username=gabceb95&style=full";

                    String cadena = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=es&username=gabceb95&style=full";
                    URL url = null; // Url de donde queremos obtener información

                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");
                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK ) {
                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                        String line = "";
                        while ((line = reader.readLine()) != null) {

                            if (line != null)
                                result.append(line);
                        }

                        JSONObject respuestaJSON = new JSONObject(result.toString());
                        JSONArray resultJSON = respuestaJSON.getJSONArray("geonames");
                        for (int i = 0; i < resultJSON.length(); i++) {
                            JSONObject obj = resultJSON.getJSONObject(i);

                            if(obj.getDouble("east") >= longitud && obj.getDouble("west") <= longitud
                                    && obj.getDouble("north") >= latitud && obj.getDouble("south") <= latitud){
                                System.out.println(obj.getString("countryName"));

                                //**********************

                                String cadena2 = "http://api.geonames.org/earthquakesJSON?formatted=true" +
                                        "&north=" + Double.toString(obj.getDouble("north")) + "&south=" + Double.toString(obj.getDouble("south")) +
                                        "&east=" + Double.toString(obj.getDouble("east")) + "" + "&west=" + Double.toString(obj.getDouble("west")) +
                                        "&username=gabceb95&style=full";

                                URL url2 = null; // Url de donde queremos obtener información

                                url2 = new URL(cadena2);
                                HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection(); //Abrir la conexión
                                connection2.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                        " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                                //connection.setHeader("content-type", "application/json");
                                int respuesta2 = connection2.getResponseCode();
                                StringBuilder result2 = new StringBuilder();

                                if (respuesta2 == HttpURLConnection.HTTP_OK ) {
                                    InputStream in2 = new BufferedInputStream(connection2.getInputStream());  // preparo la cadena de entrada
                                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));  // la introduzco en un BufferedReader
                                    String line2 = "";
                                    while ((line2 = reader2.readLine()) != null) {

                                        if (line2 != null)
                                            result2.append(line2);
                                    }
                                }

                                return result2.toString();
                                //**********************

                            }
                        }

                        return devuelve;
                    }

           }catch (Exception e) {
                   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();}

            return devuelve;
        }

        @Override
        protected void onCancelled(String aVoid) {
            //super.onCancelled(aVoid);
        }

        @Override
        protected void onPostExecute(String aVoid) {
            try {
                //Toast.makeText(getApplicationContext(), aVoid, Toast.LENGTH_SHORT).show();

                mMap.clear();

                JSONObject respuestaJSON = new JSONObject(aVoid);
                //Accedemos al vector de resultados
                JSONArray resultJSON = respuestaJSON.getJSONArray("earthquakes");   // earthquakes es el nombre del campo en el JSON
                //JSONArray resultJSON2 = respuestaJSON.getJSONArray("geonames");

                for (int i = 0; i < resultJSON.length(); i++) {
                    System.out.println(resultJSON.getJSONObject(i).getString("datetime"));
                    JSONObject obj = resultJSON.getJSONObject(i);
                    LatLng pos = new LatLng(obj.getDouble("lat"), obj.getDouble("lng"));
                    mMap.addMarker(new MarkerOptions()
                            .position(pos)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.sismo))
                            .anchor(0.0f, 1.0f)
                            .title(obj.getString("datetime")));
                }
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }



    // ******************************* GPS

    public void Mensaje(String msg){Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

}
