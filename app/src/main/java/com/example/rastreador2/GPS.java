package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GPS extends AppCompatActivity {


    Button btnrastrear, btnregistar, btnbase,btnusuario, btningresaroper,btnestatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        conexionSQ conn= new conexionSQ(this, "bd_usuarios", null, 1);

        btnrastrear = (Button)findViewById(R.id.rastreo);
        btnrastrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GPS.this, SeleccionarHerramientaRastrear.class);
                startActivity(intent);
            }
        });
        btnestatus = (Button)findViewById(R.id.estatus);
        btnestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GPS.this, detallesOperadorHerrameintas.class);
                startActivity(intent);
            }
        });
        btningresaroper = (Button)findViewById(R.id.iniciodeUso);
        btningresaroper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GPS.this, seleccionarOperador.class);
                startActivity(intent);
            }
        });

        btnbase = (Button)findViewById(R.id.basededatos);
        btnbase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GPS.this, basesdedatos.class);
                startActivity(intent);
            }
        });

        btnusuario = (Button)findViewById(R.id.ingresarusuario);
        btnusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GPS.this, IngresarUsuario.class);
                startActivity(intent);
            }
        });

        btnregistar = (Button)findViewById(R.id.ingresarherramienta);
        btnregistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GPS.this, RegistrarUsuarios.class);
                startActivity(intent);
            }
        });
    }

    public static class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {

        private GoogleMap mMap;
        double lat, lon;
        public FragmentMaps() { }


        @Override
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            View rootView = super.onCreateView(layoutInflater, viewGroup, bundle);

            if(getArguments() != null) {
                this.lat = getArguments().getDouble("lat");
                this.lon = getArguments().getDouble("lon");
            }

            getMapAsync(this);

            return rootView;
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {

//            mMap = googleMap;
//            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//            //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//
//            LatLng latLng = new LatLng(lat, lon);
//
//            float zoom = 19;
//
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//
//            googleMap.getUiSettings().setZoomControlsEnabled(true);
//
//            googleMap.addMarker(new MarkerOptions().position(latLng));
//
//            UiSettings settings = googleMap.getUiSettings();
//
//            settings.setZoomControlsEnabled(true);

        }
    }
}
