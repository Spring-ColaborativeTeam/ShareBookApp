package com.sharebook.felipe.sharebookapp.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.internal.zzf;
import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.adapter.LibroAdapter;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.NetworkException;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RequestCallBack;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.RetrofiNetwork;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapsActivity extends Fragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private  RetrofiNetwork network;
    private  ExecutorService executorService;
    private List<Libro> libros;
    //DataBarSingleton dbs = DataBarSingleton.getInstance();
    GoogleMap mGoogleMap;
    MapView mapView;
    View view;
    private List<Libro> librosMarkers = new LinkedList<Libro>();
    LibroAdapter libroAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_menu, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(view.getContext());
        mGoogleMap = googleMap;
        LatLng location = new LatLng(4.7826755,-74.0447828);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Log.d("1","aunque seaaaaaaaaaaa");

        CameraPosition Bogota = CameraPosition.builder().target(location).zoom(17).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Bogota));
        mGoogleMap.setOnMarkerClickListener(this);
        traerLibrosRetrofit();
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }*/

    }

    private void addMarkers(GoogleMap mGoogleMap){

        if(librosMarkers != null) {

            for (int i = 0; i < librosMarkers.size(); i++) {
                Log.d("lolo","Encontreeeeeeeeeeeee libro");
                LatLng location = new LatLng(4.7826755+i,-74.0447828);
                MarkerOptions m = new MarkerOptions().position(location).title(librosMarkers.get(i).getName()).visible(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.book2));

                Marker l =mGoogleMap.addMarker(m);
                l.setTag(librosMarkers.get(i));
                l.showInfoWindow();
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
            Libro libro = (Libro) marker.getTag();
            AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
            LayoutInflater factory = LayoutInflater.from(getActivity());
            final View view = factory.inflate(R.layout.dialog_marker_layout, null);
            final TextView textNombre = (TextView) view.findViewById(R.id.nombreVentana);
            textNombre.setText(libro.getName());
            final TextView textEdi = (TextView) view.findViewById(R.id.ediVentana);
            textEdi.setText(libro.getDescription());
            final ImageView imageLibro = (ImageView) view.findViewById(R.id.imagenLibro);
            String base_tmp = "https://sharebookapp.herokuapp.com/libros/"+libro.getId()+"/picture";
            Picasso.with(view.getContext()).load(base_tmp).into(imageLibro);
            alertadd.setView(view);

            alertadd.show();
            return true;

    }

    public void traerLibrosRetrofit(){
        network = new RetrofiNetwork();
        executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                network.getLibros(new RequestCallBack<List<Libro>>() {
                    @Override
                    public void onSuccess(List<Libro>  response) {
                        Log.d("sdasd","bien  "+response.size());
                        libros = response;
                        librosMarkers = libros;

                    }

                    @Override
                    public void onFailed(NetworkException e) {
                        Log.d("loadssalo","pailaaaaaaa");
                        libros = null;
                    }
                });
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (libros != null) {
                            addMarkers(mGoogleMap);
                        }
                    }
                });
            }
        });
    }




    public Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return BitmapFactory.decodeResource(context.getResources(), drawableId);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }


}