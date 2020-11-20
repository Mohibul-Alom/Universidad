package com.example.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListRanking extends ArrayAdapter {

    private List<Usuario>lista_usuario;
    public Context mContext;
    public int layout;

    public ListRanking(@NonNull Context context, int resource, List<Usuario> mLista) {
        super(context, resource, mLista);
        this.lista_usuario = mLista;
        this.mContext = context;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(layout,null);

        }
        int orden = position;
        String nombre = lista_usuario.get(position).getNombre();
        int puntuacion = lista_usuario.get(position).getPuntuacion();


        TextView orden_r = view.findViewById(R.id.orden_ranking);
        TextView nombre_r = view.findViewById(R.id.nombre_rankig);
        TextView puntuacion_r = view.findViewById(R.id.puntuacion_ranking);

        orden_r.setText(String.valueOf(position));
        nombre_r.setText(nombre);
        puntuacion_r.setText(String.valueOf(puntuacion));

        return view;

    }


}
