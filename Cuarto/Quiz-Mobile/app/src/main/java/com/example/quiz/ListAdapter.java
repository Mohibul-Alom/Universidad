package com.example.quiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private List<String> significado;
    public Context mContext;
    public int resourceLayout;

    public ListAdapter(@NonNull Context context, int resource, List<String> mLista) {
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

        Log.i("ListAdapter","posicion "+position);

        String mensaje = significado.get(position);
        TextView frase1 = view.findViewById(R.id.textoListView1);
        frase1.setText(mensaje);

        return view;

    }
}
