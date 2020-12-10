package com.merobookstore.bookstore.ui.aboutus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.merobookstore.bookstore.R;

public class AboutUsFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private TextView about;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_aboutus, container, false);

        //AIzaSyDhFsR4QOvDQ9DW0UXHPxGoaablWWIE214

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);

        about = root.findViewById(R.id.aboutText);
        about.setText("Our Largest Collection Ever on General Books, Rare Books, " +
                "Author Signed and Autographed Books, Maps, Postcards, Posters, " +
                "special section of Our Publications...");
        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        CameraUpdate center, zoom;
        center = CameraUpdateFactory.newLatLng(new LatLng(27.628441, 85.303576));
        zoom = CameraUpdateFactory.zoomTo(10);


        mMap.addMarker(new MarkerOptions().position(new LatLng(27.628441, 85.303576)).title("Book Store"));
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}