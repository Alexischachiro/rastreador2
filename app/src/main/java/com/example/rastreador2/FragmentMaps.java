package com.example.rastreador2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {
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

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        Log.i("En el maps latitud", String.valueOf(lat));
        Log.i("En el maps longitud", String.valueOf(lon));
        LatLng latLng = new LatLng(lat, lon);

        float zoom = 19;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.addMarker(new MarkerOptions().position(latLng));

        UiSettings settings = googleMap.getUiSettings();

        settings.setZoomControlsEnabled(true);
    }
}
