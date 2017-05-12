package com.sharebook.felipe.sharebookapp.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.adapter.IntercambiarLibroAdapter;
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

import static com.facebook.FacebookSdk.getApplicationContext;

public class MapsActivity extends Fragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    private RetrofiNetwork network;
    private ExecutorService executorService;
    private List<Libro> libros;
    private SharedPreferences pref;
    //DataBarSingleton dbs = DataBarSingleton.getInstance();
    GoogleMap mGoogleMap;
    IntercambiarFragment intFra;
    MapView mapView;
    View view;
    private List<Libro> librosMarkers = new LinkedList<Libro>();
    LibroAdapter libroAdapter;
    Libro libro;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

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

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(view.getContext());
        mGoogleMap = googleMap;
        LatLng location = new LatLng(4.782722, -74.043041);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Log.d("1", "aunque seaaaaaaaaaaa");
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);}
      /*  LocationManager locManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        List<String> providersList = locManager.getAllProviders();
        LocationProvider provider = locManager.getProvider(providersList.get(0));
        int precision = provider.getAccuracy();
        Criteria req = new Criteria();
        req.setAccuracy(Criteria.ACCURACY_FINE);*/

        CameraPosition Bogota = CameraPosition.builder().target(location).zoom(16).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Bogota));
        mGoogleMap.setOnMarkerClickListener(this);
        pref = getApplicationContext().getSharedPreferences("userDetails", 0);
        traerLibrosRetrofit();

    }



    private void addMarkers(GoogleMap mGoogleMap){

        if(librosMarkers != null) {

            for (int i = 0; i < librosMarkers.size(); i++) {

                LatLng location = new LatLng(librosMarkers.get(i).getLatitude(),librosMarkers.get(i).getLongitude());
                MarkerOptions m = new MarkerOptions().position(location).title(librosMarkers.get(i).getName()).visible(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.book2));

                Marker l =mGoogleMap.addMarker(m);
                l.setTag(librosMarkers.get(i));
                l.showInfoWindow();
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
             libro = (Libro) marker.getTag();
            AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
            LayoutInflater factory = LayoutInflater.from(getActivity());
            final View view = factory.inflate(R.layout.dialog_marker_layout, null);
            final TextView textNombre = (TextView) view.findViewById(R.id.nombreVentana);
            textNombre.setText(libro.getName());
            final TextView textEdi = (TextView) view.findViewById(R.id.ediVentana);
            textEdi.setText(libro.getDescription());
            final TextView textAutor = (TextView) view.findViewById(R.id.autorVentana);
            textAutor.setText(libro.getAutor());
            final ImageView imageLibro = (ImageView) view.findViewById(R.id.imagenLibro);
            String base_tmp = "https://sharebookapp.herokuapp.com/libros/"+libro.getId()+"/picture";
            Picasso.with(view.getContext()).load(base_tmp).into(imageLibro);
            Button buttonOne = (Button) view.findViewById(R.id.button5);
            buttonOne.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    Log.d("1234234", "Vealo --------> "+libro.getName());
                    fragmentManager = getFragmentManager();
                    intFra = new IntercambiarFragment();
                    intFra.setLibroSelect(libro);
                    transaction = fragmentManager.beginTransaction();
                    transaction.add(R.id.mainFrame, intFra);
                    transaction.commit();
                }
            });
            alertadd.setView(view);

            alertadd.show();
            return true;

    }

    public void traerLibrosRetrofit(){
        network = new RetrofiNetwork(pref.getString("username", null));
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


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }





}