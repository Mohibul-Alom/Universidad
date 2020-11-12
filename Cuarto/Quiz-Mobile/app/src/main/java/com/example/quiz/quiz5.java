package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class quiz5 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Animation animation;
    private int puntuacion = 0;
    private String nombre;
    private String seleccion = null;
    Button btn1;


    private ListView lista;
    private List<String> opciones;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz5);

        Intent intent = getIntent();
        nombre = intent.getExtras().getString("nombre");
        puntuacion = intent.getExtras().getInt("puntuacion");

        TextView textView = findViewById(R.id.text_quiz5);
        textView.setText("Pregunta 5: "+nombre);

        btn1 =findViewById(R.id.button13);

        lista = findViewById(R.id.listView5);

        opciones = new ArrayList<>();
        opciones.add("Galaxy");
        opciones.add("Siemens");
        opciones.add("Ericsson");
        opciones.add("Xperia");

        mAdapter = new LsitAdapter(quiz5.this,R.layout.itemrow,opciones);

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
            Intent intentquiz5 = new Intent(quiz5.this,finale.class);
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
        btn1.setVisibility(View.VISIBLE);


    }
}