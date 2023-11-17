package com.example.findaseat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class Map extends Fragment {
    GoogleMap gm;
    HashMap<String, Integer> buildingMap = new HashMap<>();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gm = googleMap;

            // Fill the HashMap with pairs
            buildingMap.put("Leavey Library", 1);
            buildingMap.put("Doheny Library", 2);
            buildingMap.put("Leventhal", 3);
            buildingMap.put("Olin Hall", 4);
            buildingMap.put("Annenberg Hall", 5);
            buildingMap.put("IYA", 6);
            buildingMap.put("Ronald Tutor Center", 7);
            buildingMap.put("Taper Hall", 8);
            buildingMap.put("Gould Law School", 9);
            buildingMap.put("Fertitta Hall", 10);

            LatLng usc = new LatLng(34.022415, -118.285530);
            gm.moveCamera(CameraUpdateFactory.newLatLngZoom(usc, 14.0f));

            //1 add marker Leavey Library
            LatLng leavey = new LatLng(34.02192542827415, -118.28290795537238);
            gm.addMarker(new MarkerOptions().position(leavey).title("Leavey Library"));


            //2 add marker Doheny Library
            LatLng doheny = new LatLng(34.02022506540566, -118.28370793526976);
            gm.addMarker(new MarkerOptions().position(doheny).title("Doheny Library"));

            //3 add marker Leventhal
            LatLng leventhal = new LatLng(34.018909105606966, -118.28545831571944);
            gm.addMarker(new MarkerOptions().position(leventhal).title("Leventhal"));

            //4 add marker Olin Hall
            LatLng olin = new LatLng(34.02069874914445, -118.2897066720383);
            gm.addMarker(new MarkerOptions().position(olin).title("Olin Hall"));

            //5 add marker Annenberg Hall
            LatLng annen = new LatLng(34.020841913793554, -118.28697178005817);
            gm.addMarker(new MarkerOptions().position(annen).title("Annenberg Hall"));

            //6 add marker IYA
            LatLng iya = new LatLng(34.01879384095152, -118.288473571618);
            gm.addMarker(new MarkerOptions().position(iya).title("IYA"));

            //7 add marker Ronald Tutor Center
            LatLng rtc = new LatLng(34.020448210438765, -118.28634323470999);
            gm.addMarker(new MarkerOptions().position(rtc).title("Ronald Tutor Center"));

            //8 add marker Taper Hall
            LatLng taper = new LatLng(34.022312833912615, -118.28457094206665);
            gm.addMarker(new MarkerOptions().position(taper).title("Taper Hall"));

            //9 add marker Gould Law School
            LatLng g = new LatLng(34.018744517002034, -118.28428005309773);
            gm.addMarker(new MarkerOptions().position(g).title("Gould Law School"));

            //10 add marker Fertitta
            LatLng f = new LatLng(34.01878282040196, -118.28238919283902);
            gm.addMarker(new MarkerOptions().position(f).title("Fertitta Hall"));

            gm.getUiSettings().setZoomControlsEnabled(true);
            gm.getUiSettings().setCompassEnabled(true);
            gm.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);
            gm.getUiSettings().setScrollGesturesEnabled(true);

            gm.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    //get title of the marker that was clicked (building name)
                    String markerName = marker.getTitle();
                    int id = buildingMap.get(markerName);
                    MainActivity.startBooking(id);
                    return false;
                }
            });
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}