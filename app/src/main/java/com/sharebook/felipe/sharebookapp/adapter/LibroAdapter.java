package com.sharebook.felipe.sharebookapp.adapter;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.activity.IntercambiarFragment;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Felipe on 25/04/17.
 */

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.ViewHolder> implements View.OnClickListener{
    private final List<Libro> publicacions;
    private Context context;
    private CardView cardView;

    public LibroAdapter(List<Libro> publicacions) {
        this.publicacions = publicacions;
    }
    @Override
    public LibroAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LibroAdapter.ViewHolder viewHolder, int position) {

        Libro publi = publicacions.get(position);
        viewHolder.name.setText(publi.getName());
        viewHolder.description.setText(publi.getDescription());
        Location myLocation = new Location("Mi ubicacion");
        myLocation.setLatitude(4.782722);
        myLocation.setLongitude(-74.043041);
        Location libroLocation = new Location("Libro ");
        libroLocation.setLatitude(publi.getLatitude());
        libroLocation.setLongitude(publi.getLongitude());
        Float distance = myLocation.distanceTo(libroLocation);
        distance = distance/1000;
        viewHolder.distance.setText(String.format("%.2f", distance)+" Km");
        String base_tmp = "https://sharebookapp.herokuapp.com/libros/"+publi.getId()+"/picture";
        System.out.println("id"+publi.getId()+ "URL"+ publi.getImageUrl());
        Picasso.with(context).load(base_tmp).into(viewHolder.logo);

    }

    @Override
    public int getItemCount() {
        return publicacions.size();
    }

    @Override
    public void onClick(View view) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, distance;
        ImageView logo;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            distance = (TextView) view.findViewById(R.id.distance);
            logo = (ImageView) view.findViewById(R.id.logo);

        }

    }
}

