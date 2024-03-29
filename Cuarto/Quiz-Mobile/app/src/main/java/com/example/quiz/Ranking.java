package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class Ranking extends AppCompatActivity {

    private String nombre;
    private String email;
    private int puntuacion;

    private BaseDeDatos bd;

    private ListView lista;
    private ArrayList<Usuario>lista_usuario;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        bd = new BaseDeDatos(this);

        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        nombre = intent.getExtras().getString("nombre");
        puntuacion = intent.getExtras().getInt("puntuacion");

        lista = (ListView) findViewById(R.id.listView_ranking);
        lista_usuario = ordenarUsuarios(bd.getTodoUsuario());


        mAdapter = new ListRanking(Ranking.this,lista_usuario);


        lista.setAdapter(mAdapter);


    }


    public void volver(View view) {
        Intent volver = new Intent(Ranking.this, Puntuacion.class);
        volver.putExtra("email",email);
        volver.putExtra("nombre",nombre);
        volver.putExtra("puntuacion",puntuacion);
        startActivity(volver);

    }

    public ArrayList<Usuario> ordenarUsuarios(ArrayList<Usuario> listaUsuarios){
        ArrayList <Usuario> lista = listaUsuarios;

        Collections.sort(lista);

        return lista;
    }


}