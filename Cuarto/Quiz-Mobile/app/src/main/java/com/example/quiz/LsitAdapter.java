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

public class LsitAdapter extends ArrayAdapter {

    List<String> significado;
    public Context mContext;
    public int resourceLayout;

    public LsitAdapter(@NonNull Context context, int resource, List<String> mLista) {
        super(context, resource, mLista);
        this.significado = mLista;
        this.mContext = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(resourceLayout,null);

        }

        String mensaje = significado.get(position);
        TextView frase1 = view.findViewById(R.id.textoListView1);
        frase1.setText(mensaje);

        return view;

    }
}
