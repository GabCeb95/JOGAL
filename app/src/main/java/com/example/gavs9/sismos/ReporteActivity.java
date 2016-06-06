package com.example.gavs9.sismos;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.gavs9.sismos.Entities.Reporte;
import com.example.gavs9.sismos.Entities.Sismo;
import com.example.gavs9.sismos.Services.ReporteService;
import com.example.gavs9.sismos.Services.SismoService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class ReporteActivity extends Base implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.mLocationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        super.mLocationClient.connect();
        configureComponentes();
    }


    private void configureComponentes() {

        configureSpinner();

        switchSismo = (Switch) findViewById(R.id.switchSismo);

        switchSismo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                   clearElements();
                }else{
                   showElements();
                }

            }
        });

        btnCrear = (Button) findViewById(R.id.btnCrear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearReporte();
            }
        });

        txtView = (TextView) findViewById(R.id.txtTipo);
        descripcion = (EditText) findViewById(R.id.descripcion);
        descripcion.setHint("Descripcion de reporte");
    }


    private void configureSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);

        final List<String> tipos = new ArrayList<String>();
        tipos.add("Sentido");
        tipos.add("Caida objetos");
        tipos.add("Emergencia");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, tipos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new
                                                  AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view,
                                                                                 int position, long id) {
                                                          selected = tipos.get(position);
                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {
                                                      }
                                                  });
        spinner.setAdapter(adapter);

    }


    public void crearReporte() {
        //current = getLocation();
        if (switchSismo.isChecked()) {
            crearReporteSismo();
        } else {
            crearReporteDanno();
        }
    }


    public void crearReporteSismo() {
        SismoService sismoService = new SismoService();
        EditText et = (EditText) findViewById(R.id.descripcion);

        Sismo sismo = new Sismo("10.043822","-83.988932", "0.0", "Por verificar");
        //Sismo sismo = new Sismo(getLocation().getLatitude() + "", getLocation().getLongitude() + "", "0.0", "Por verificar");
        sismoService.add(sismo.getHashMap());
    }

    public void crearReporteDanno(){
        ReporteService rs = new ReporteService();
        EditText et = (EditText) findViewById(R.id.descripcion);
        String descripcion = et.getText().toString();
        Reporte reporte = new Reporte("10.043822","-83.988932",descripcion,selected);
        //Reporte reporte = new Reporte(getLocation().getLatitude()+"",getLocation().getLongitude()+"",descripcion,selected);
        rs.add(reporte.getHashMap());
    }

    private void clearElements(){
        descripcion.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        txtView.setVisibility(View.INVISIBLE);
    }

    private void showElements(){
        descripcion.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        txtView.setVisibility(View.VISIBLE);
    }

    private Button btnCrear;
    private EditText descripcion;
    private Spinner spinner;
    private TextView txtView;
    private Switch switchSismo;
    private String selected;

    private Location current;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
