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

public class Quiz5 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Animation animation;
    private int puntuacion = 0;
    private String nombre;
    public static String email;
    private String seleccion = null;
    private ImageView imageNext;


    private ListView lista;
    private List<String> opciones;
    private android.widget.ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz5);

        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        nombre = intent.getExtras().getString("nombre");
        puntuacion = intent.getExtras().getInt("puntuacion");

        TextView textView = findViewById(R.id.text_quiz5);
        textView.setText("Pregunta 5: "+nombre);

        imageNext = findViewById(R.id.siguiente5);
        imageNext.setVisibility(View.INVISIBLE);


        lista = findViewById(R.id.listView5);

        opciones = new ArrayList<>();
        opciones.add("Galaxy");
        opciones.add("Siemens");
        opciones.add("Ericsson");
        opciones.add("Xperia");

        mAdapter = new ListAdapter(Quiz5.this,R.layout.itemrow,opciones);

        lista.setAdapter(mAdapter);

        lista.setOnItemClickListener(this);


    }

    public void volver(View view) {
        Intent intentPrincipal = new Intent(this, MainActivity.class);
        startActivity(intentPrincipal);

    }

    public void siguiente5(View view){
        String corrcta = "Ericsson";

        if (seleccion.compareTo(corrcta)==0){
            puntuacion += 3;
        }else{
            puntuacion -= 2;
        }

        if (seleccion != null){
            Intent intentquiz5 = new Intent(Quiz5.this, Puntuacion.class);
            intentquiz5.putExtra("email",email);
            intentquiz5.putExtra("nombre",nombre);
            intentquiz5.putExtra("puntuacion",puntuacion);
            startActivity(intentquiz5);
        }else{
            Toast.makeText(this, "Seleccione alguna respuesta", Toast.LENGTH_SHORT).show();
        }

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        animation = new AlphaAnimation(0.5f, 1.2f);
        animation.setDuration(2500);
        view.startAnimation(animation);
        this.seleccion = opciones.get(position);
        imageNext.setVisibility(View.VISIBLE);


    }
}