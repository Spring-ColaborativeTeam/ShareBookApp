package com.sharebook.felipe.sharebookapp.adapter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharebook.felipe.sharebookapp.R;
import com.sharebook.felipe.sharebookapp.activity.IntercambiarFragment;
import com.sharebook.felipe.sharebookapp.activity.MenuActivity;
import com.sharebook.felipe.sharebookapp.persistence.dao.model.Libro;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Diego on 25/04/17.
 */

public class SolicitudesPendientesAdaptar extends RecyclerView.Adapter<SolicitudesPendientesAdaptar.ViewHolder> implements View.OnClickListener{
    private final List<List<Libro>> publicacions;
    private Context context;
    private CardView cardView;
    private CardView cardViewAnterior;
    private int focusedItem = -1;
    public Libro libroSelected = null;
    boolean selec = false;


    public SolicitudesPendientesAdaptar(List<List<Libro>> publicacions) {
        this.publicacions = publicacions;
    }
    @Override
    public SolicitudesPendientesAdaptar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_pendientes, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return tryMoveSelection(lm, -1);
                    }
                }

                return false;
            }
        });
    }

    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int tryFocusItem = focusedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (tryFocusItem >= 0 && tryFocusItem < getItemCount()) {
            notifyItemChanged(focusedItem);
            focusedItem = tryFocusItem;
            notifyItemChanged(focusedItem);
            lm.scrollToPosition(focusedItem);

            return true;
        }

        return false;
    }

    @Override
    public void onBindViewHolder(SolicitudesPendientesAdaptar.ViewHolder viewHolder, int position) {

        List<Libro> publi = publicacions.get(position);
        Libro libro1 = publi.get(0);
        Libro libro2 = publi.get(1);
        viewHolder.name.setText(libro1.getName());
        viewHolder.description.setText(libro1.getDescription());
        String base_tmp = "https://sharebookapp.herokuapp.com/libros/"+libro1.getId()+"/picture";
        //System.out.println("id"+publi.getId()+ "URL"+ publi.getImageUrl());
        Picasso.with(context).load(base_tmp).into(viewHolder.logo);
        viewHolder.name2.setText(libro2.getName());
        viewHolder.description2.setText(libro2.getDescription());
        String base_tmp2 = "https://sharebookapp.herokuapp.com/libros/"+libro2.getId()+"/picture";
        //System.out.println("id"+publi.getId()+ "URL"+ publi.getImageUrl());
        Picasso.with(context).load(base_tmp2).into(viewHolder.logo2);
        viewHolder.itemView.setSelected(focusedItem == position);
        //libroSelected = publi;
        if(selec){
            cardView.setCardBackgroundColor(viewHolder.itemView.isSelected() ? Color.LTGRAY : Color.WHITE);
        }
        if(!selec)
            libroSelected = null;
        focusedItem = -1;


    }

    @Override
    public int getItemCount() {
        return publicacions.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, name2, description2;
        ImageView logo, logo2;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            logo = (ImageView) view.findViewById(R.id.logo);
            name2 = (TextView) view.findViewById(R.id.name2);
            description2 = (TextView) view.findViewById(R.id.description2);
            logo2 = (ImageView) view.findViewById(R.id.logo2);
            cardView = (CardView) view.findViewById(R.id.card_view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    notifyItemChanged(focusedItem);
                    focusedItem = getLayoutPosition();
                    notifyItemChanged(focusedItem);
                    selec=true;
                    cardView = (CardView) v.findViewById(R.id.card_view);
                    Log.d("1123", "Vealo "+name.getText());
                }
            });

        }

    }
}

