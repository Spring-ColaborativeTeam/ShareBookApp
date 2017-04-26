package com.sharebook.felipe.sharebookapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Felipe on 25/04/17.
 */

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.ViewHolder>{
    private final List<Libro> publicacions;
    private Context context;

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
        Picasso.with(context).load(publi.getImageUrl()).into(viewHolder.logo);
    }

    @Override
    public int getItemCount() {
        return publicacions.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description;
        ImageView logo;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            logo = (ImageView) view.findViewById(R.id.logo);
        }
    }
}

