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

    Button btnrastrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        btnrastrear = (Button)findViewById(R.id.rastreo);
        btnrastrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GPS.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public static class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {

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
            LatLng latLng = new LatLng(lat, lon);

            float zoom = 19;

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

            googleMap.getUiSettings().setZoomControlsEnabled(true);

            googleMap.addMarker(new MarkerOptions().position(latLng));

            UiSettings settings = googleMap.getUiSettings();

            settings.setZoomControlsEnabled(true);
        }
    }
}
