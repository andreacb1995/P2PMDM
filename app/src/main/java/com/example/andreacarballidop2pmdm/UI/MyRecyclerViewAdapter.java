package com.example.andreacarballidop2pmdm.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreacarballidop2pmdm.R;
import com.example.andreacarballidop2pmdm.core.Entrenamiento;
import com.example.andreacarballidop2pmdm.db.database.EntrenamientoLab;

import java.lang.reflect.Field;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

import static com.example.andreacarballidop2pmdm.UI.AccionesEntrenamiento.*;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Entrenamiento> items;
    private AccionesEntrenamiento accionesEntrenamiento;

    MyRecyclerViewAdapter(List<Entrenamiento> items,AccionesEntrenamiento accionesEntrenamiento){
        this.items = items;
        this.accionesEntrenamiento=accionesEntrenamiento;
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final AccionesEntrenamiento accionesEntrenamiento;
        // Campos respectivos de un item
        CardView cv;
        public static TextView fecha;
        public static TextView tiempo;
        public static TextView distancia;
        public static ImageView imagen;

        public ViewHolder(View v,AccionesEntrenamiento accionesEntrenamiento) {
            super(v);

            cv= (CardView) v.findViewById(R.id.cv);
            fecha = (TextView) v.findViewById(R.id.textvFecha);
            tiempo = (TextView) v.findViewById(R.id.textvTiempo);
            distancia = (TextView) v.findViewById(R.id.textvDistancia);
            imagen= (ImageView)v.findViewById(R.id.imagen);
            this.accionesEntrenamiento = accionesEntrenamiento;


        }

        public void mostrarEntrenamiento(final Entrenamiento entrenamiento, final Context context){

            fecha.setText(entrenamiento.getFormatoFecha());
            tiempo.setText(entrenamiento.getTiempo());
            distancia.setText(entrenamiento.getm());

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accionesEntrenamiento.mostrar(entrenamiento);
            }

            });

            cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popup = new PopupMenu(cv.getContext(), itemView);

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mostrare:
                                    accionesEntrenamiento.mostrar(entrenamiento);
                                    return true;
                                case R.id.modificare:
                                    accionesEntrenamiento.modificar(entrenamiento);
                                    return true;
                                case R.id.eliminare:
                                    accionesEntrenamiento.eliminar(entrenamiento);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    // here you can inflate your menu
                    popup.inflate(R.menu.context_menu);
                    popup.setGravity(Gravity.RIGHT);
                    popup.show();
                    return false;
                }

            });



        }
    }

    public MyRecyclerViewAdapter(MainActivity mainActivity, List<Entrenamiento> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entrenamiento_card, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v,accionesEntrenamiento);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tiempo.setText(items.get(i).getTiempo());
        viewHolder.distancia.setText(String.valueOf(items.get(i).getm()));
        viewHolder.fecha.setText(items.get(i).getFormatoFecha());
        Entrenamiento entrenamiento= items.get(i);
        viewHolder.mostrarEntrenamiento(entrenamiento,context);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}