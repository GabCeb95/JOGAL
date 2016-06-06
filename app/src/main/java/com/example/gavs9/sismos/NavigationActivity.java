package com.example.gavs9.sismos;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.gavs9.sismos.Entities.Reporte;
import com.example.gavs9.sismos.Entities.Sismo;
import com.example.gavs9.sismos.Services.ReporteService;
import com.example.gavs9.sismos.Services.Request;
import com.example.gavs9.sismos.Services.SismoService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NavigationActivity extends Base
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

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

        super.mLocationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        super.mLocationClient.connect();

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
        } else if (id == R.id.nav_sismos) {
            if(getLocation() != null) {
                mMap.clear();

                miUbicacion();

                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(Double.toString(mMap.getMyLocation().getLatitude()), Double.toString(mMap.getMyLocation().getLongitude()));


            }else
            {
                Mensaje("No es posible obtener su ubicación");
            }
        } else if (id == R.id.nav_clima) {
            //Intent intento = new Intent(getApplicationContext(), SismosReportadosActivity.class);
            //startActivity(intento);
        }else if(id == R.id.nav_reportes)
        {
            mMap.clear();
            miUbicacion();
            verReportes();
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
                hiloconexion.execute(Double.toString(latLng.latitude), Double.toString(latLng.longitude));

            }
        });

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; // Revisa si tiene permismos para GPS
        }
        mMap.setMyLocationEnabled(true); // Poner el boton de gps en el mapa
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() { // Accion del boton gps en el mapa

                // Comienza a crear los marcadores desde Geonames
                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(Double.toString(mMap.getMyLocation().getLatitude()), Double.toString(mMap.getMyLocation().getLongitude()));

                if( mMap.getMyLocation() == null )
                    Toast.makeText(getApplicationContext(), "Esperando ubicación", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        //miUbicacionInicial();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Mensaje("Error al conectar API GPS");
    }


    public class ObtenerWebService extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String devuelve = "";
            double latitud = Double.parseDouble(params[0]);
            double longitud = Double.parseDouble(params[1]);

            try {
                    //String cadena = "http://api.geonames.org/earthquakesJSON?formatted=true&north=11.216819&south=8.032975&east=-82.555992&west=-85.950623&username=gabceb95&style=full";

                    String apiGeo = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=es&username=gabceb95&style=full";
                    Request req = new Request();
                    JSONArray resultJSON = req.getGeo(apiGeo);
                        for (int i = 0; i < resultJSON.length(); i++) {
                            JSONObject obj = resultJSON.getJSONObject(i);

                            if(obj.getDouble("east") >= longitud && obj.getDouble("west") <= longitud
                                    && obj.getDouble("north") >= latitud && obj.getDouble("south") <= latitud){

                                String apiEarthquakes = "http://api.geonames.org/earthquakesJSON?formatted=true" +
                                        "&north=" + Double.toString(obj.getDouble("north")) + "&south=" + Double.toString(obj.getDouble("south")) +
                                        "&east=" + Double.toString(obj.getDouble("east")) + "" + "&west=" + Double.toString(obj.getDouble("west")) +
                                        "&username=gabceb95&style=full";

                                return req.getEarthquakes(apiEarthquakes);
                            }
                        }

                        return devuelve;
            } catch (Exception e) {
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
                            .title(("Fecha : " + obj.getString("datetime")))
                            .snippet(("Magnitud : " + obj.getString("depth"))));


                }

                sismosAPP();


            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Buscando sismos...", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        private void sismosAPP()
        {
            try {
                SismoService s = new SismoService();
                ArrayList<Sismo> sismos = s.get();

                for(Sismo sismo:sismos)
                {
                    System.out.println(sismo.getLatitud() + "   " + sismo.getLongitud());
                    LatLng pos1 = new LatLng(Double.parseDouble(sismo.getLatitud()), Double.parseDouble(sismo.getLongitud()));
                    mMap.addMarker(new MarkerOptions()
                            .position(pos1)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.reporte))
                            .anchor(0.0f, 1.0f)
                            .title("Fecha : " + sismo.getFecha() )
                            .snippet(("Magnitud : " + sismo.getMagnitud()) +
                                    ("  " + sismo.getEpicentro())));
                }
            }catch(Exception e) {
                System.out.println("ERROR : " + e.getMessage());
            }

        }
    }


    private void verReportes()
    {
        LatLng latLng= new LatLng(getLocation().getLatitude(),getLocation().getLongitude());
        LatLng milugar = new LatLng(latLng.latitude, latLng.longitude);

        try {
            ReporteService s = new ReporteService();
            ArrayList<Reporte> reportes = s.get();

            for(Reporte reporte:reportes)
            {
                LatLng pos1 = new LatLng(Double.parseDouble(reporte.getLatitud()), Double.parseDouble(reporte.getLongitud()));
                mMap.addMarker(new MarkerOptions()
                        .position(pos1)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.accidente))
                        .anchor(0.0f, 1.0f)
                        .title("Fecha : " + reporte.getFecha() ).snippet((" Tipo : " + reporte.getDescripcion())));
            }
        }catch(Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    private void miUbicacion()
    {
        LatLng latLng= new LatLng(getLocation().getLatitude(),getLocation().getLongitude());
        LatLng milugar = new LatLng(latLng.latitude, latLng.longitude);
        int zoomLevel = 13;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(milugar, zoomLevel));
    }

    private void miUbicacionInicial()
    {
        LatLng latLng= new LatLng(getLocation().getLatitude(),getLocation().getLongitude());
        LatLng milugar = new LatLng(latLng.latitude, latLng.longitude);
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(milugar, zoomLevel));

        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.accidente))
                .anchor(0.0f, 1.0f)
                .title("Aquí estoy!")).setSnippet("LAT: " + latLng.latitude + "LNG: " + latLng.longitude);
    }

}
