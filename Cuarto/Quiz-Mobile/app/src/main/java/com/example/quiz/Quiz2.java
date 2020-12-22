package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Quiz2 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Animation animation;

    private int puntuacion = 0;
    private String nombre;
    public static String email;

    private String seleccion = null;
    private ImageView imageNext;


    private ListView lista;
    private List<String> significado;
    private android.widget.ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        nombre = intent.getExtras().getString("nombre");
        puntuacion = intent.getExtras().getInt("puntuacion");

        TextView textView = findViewById(R.id.textView10);
        textView.setText("Pregunta 2: " + nombre);

        imageNext = findViewById(R.id.siguiente2);
        imageNext.setVisibility(View.INVISIBLE);

        lista = findViewById(R.id.listView2);

        significado = new ArrayList<>();
        significado.add("Narrow File Connection");
        significado.add("Near Fild Comunication");
        significado.add("Near Front Chip");
        significado.add("Nec Field Connection");


        mAdapter = new ListAdapter(Quiz2.this, R.layout.itemrow, significado);

        lista.setAdapter(mAdapter);

        lista.setOnItemClickListener(this);


    }


    public void volver(View view) {
        Intent intentPrincipal = new Intent(this, MainActivity.class);
        startActivity(intentPrincipal);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        animation = new AlphaAnimation(0.5f, 1.2f);

        animation.setDuration(2500);

        view.startAnimation(animation);
        this.seleccion = significado.get(position);
        imageNext.setVisibility(View.VISIBLE);

    }

    public void siguiente2(View view) {
        String corrcta = "Near Fild Comunication";

        if (seleccion.compareTo(corrcta) == 0) {
            puntuacion += 3;
        } else {
            puntuacion -= 2;
        }

        if (seleccion != null) {
            Intent intentquiz3 = new Intent(Quiz2.this, Quiz3.class);
            intentquiz3.putExtra("email",email);
            intentquiz3.putExtra("nombre", nombre);
            intentquiz3.putExtra("puntuacion", puntuacion);
            startActivity(intentquiz3);
        } else {
            Toast.makeText(this, "Seleccione alguna respuesta", Toast.LENGTH_SHORT).show();
        }

    }
}