package com.example.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdapterSpinner extends ArrayAdapter<OpcionesItem> {

    public AdapterSpinner(Context context, ArrayList<OpcionesItem> lista){
        super(context,0,lista);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner,parent,false);
        }


        TextView texto = convertView.findViewById(R.id.text_view_opciones);

        OpcionesItem opcionesItem = getItem(position);
        if (opcionesItem!=null) {
            texto.setText(opcionesItem.getOpcion());
        }
        return convertView;
    }

}
