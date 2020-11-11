package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class finale extends AppCompatActivity {

    private int puntuacion = 0;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finale);


        Intent intent = getIntent();
        nombre = intent.getExtras().getString("nombre");
        puntuacion = intent.getExtras().getInt("puntuacion");

        if (puntuacion < 0){
            puntuacion = 0;
        }
        TextView textView2 = findViewById(R.id.textView19);
        textView2.setText(String.valueOf(puntuacion));

    }


    public void reintentar(View view) {
        puntuacion = 0;
        Intent reintentar = new Intent(finale.this,quiz1.class);
        reintentar.putExtra("nombre",nombre);
        reintentar.putExtra("puntuacion",puntuacion);
        startActivity(reintentar);
    }
}