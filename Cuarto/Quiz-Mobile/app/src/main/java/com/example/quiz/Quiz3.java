package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Quiz3 extends AppCompatActivity {

    private int puntuacion;
    private String nombre;
    private String opcion;
    public static String email;

    private Spinner mSpinner;
    private ArrayList<OpcionesItem>mList;
    private AdapterSpinner mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz3);

        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        nombre = intent.getExtras().getString("nombre");
        puntuacion = intent.getExtras().getInt("puntuacion");

        TextView textView = findViewById(R.id.textView13);
        textView.setText("Pregunta 3: "+nombre);


        mList = new ArrayList<>();
        mList.add(new OpcionesItem("QuickCharge"));
        mList.add(new OpcionesItem("Pump Express"));
        mList.add(new OpcionesItem("HelioCharge"));
        mList.add(new OpcionesItem("UltraFast Charging"));


        mSpinner = findViewById(R.id.spinner);

        mAdapter = new AdapterSpinner(this,mList);
        mSpinner.setAdapter(mAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OpcionesItem seleccionado = (OpcionesItem) parent.getItemAtPosition(position);
                opcion = seleccionado.getOpcion();
                //Toast.makeText(quiz3.this,"Has seleccionado: "+opcion,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void volver(View view) {
        Intent intentPrincipal = new Intent(this, MainActivity.class);
        startActivity(intentPrincipal);

    }


    public void siguiente3(View view) {
        String corrcta = "Pump Express";

        if (opcion.compareTo(corrcta)==0){
            puntuacion += 3;
        }else{
            puntuacion -= 2;
        }

        if (opcion != null){
            Intent intentquiz3 = new Intent(Quiz3.this, Quiz4.class);
            intentquiz3.putExtra("email",email);
            intentquiz3.putExtra("nombre",nombre);
            intentquiz3.putExtra("puntuacion",puntuacion);
            startActivity(intentquiz3);
        }else{
            Toast.makeText(this, "Seleccione alguna respuesta", Toast.LENGTH_SHORT).show();
        }

    }
}