package com.example.quiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListRanking extends BaseAdapter {

    private ArrayList<Usuario>lista_usuario;
    private Context mContext;
    private int layout;

    public ListRanking( Context context, ArrayList<Usuario>mLista) {
        this.lista_usuario = mLista;
        this.mContext = context;
        //this.layout = resource;

    }


    public int getCount() {
        return lista_usuario.size();
    }


    public Object getItem(int position) {
        return lista_usuario.get(position);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.filas_ranking,parent,false);
        }


        Usuario tempUsu = (Usuario) getItem(position);

        TextView orden_r = view.findViewById(R.id.orden_ranking);
        TextView nombre_r = view.findViewById(R.id.nombre_ranking);
        TextView puntuacion_r = view.findViewById(R.id.puntuacion_ranking);

        orden_r.setText(String.valueOf(position+1));
        nombre_r.setText(tempUsu.getNombre());
        puntuacion_r.setText(String.valueOf(tempUsu.getPuntuacion()));


        return view;
    }


}
