package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class finale extends AppCompatActivity {

    private int puntuacion = 0;
    private String nombre;
    public static String email;

    private Usuario usu;
    private BaseDeDatos bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finale);

        usu = new Usuario();
        bd = new BaseDeDatos(this);

        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        nombre = intent.getExtras().getString("nombre");
        puntuacion = intent.getExtras().getInt("puntuacion");

        usu = bd.getUsuario(email);

        if (puntuacion < 0){
            puntuacion = 0;
        }
        usu.setPuntuacion(puntuacion);

        TextView textView2 = findViewById(R.id.textView19);
        textView2.setText(String.valueOf(puntuacion));

        bd.actualizar(usu);


    }


    public void reintentar(View view) {
        puntuacion = 0;
        Intent reintentar = new Intent(finale.this,quiz1.class);
        reintentar.putExtra("email",email);
        reintentar.putExtra("nombre",nombre);
        reintentar.putExtra("puntuacion",puntuacion);
        startActivity(reintentar);
    }


    public void Ranking(View view) {
        Intent rankingIntent = new Intent(finale.this,Ranking.class);
        rankingIntent.putExtra("email",email);
        rankingIntent.putExtra("nombre",nombre);
        rankingIntent.putExtra("puntuacion",puntuacion);
        startActivity(rankingIntent);
    }


}