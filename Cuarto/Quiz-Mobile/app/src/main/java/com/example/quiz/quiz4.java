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

public class quiz4 extends AppCompatActivity {

    private int puntuacion;
    private String nombre;
    public static String email;

    private String opcion;


    Spinner mSpinner;
    private ArrayList<OpcionesItem> mList;
    private AdapterSpinner mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz4);

        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        nombre = intent.getExtras().getString("nombre");
        puntuacion = intent.getExtras().getInt("puntuacion");

        TextView textView = findViewById(R.id.text_quiz4);
        textView.setText("Pregunta 4: " + nombre);


        mList = new ArrayList<>();
        mList.add(new OpcionesItem("NEC"));
        mList.add(new OpcionesItem("TCL"));
        mList.add(new OpcionesItem("Foxconn"));
        mList.add(new OpcionesItem("Sharp"));

        mSpinner = findViewById(R.id.spinner2);

        mAdapter = new AdapterSpinner(this, mList);
        mSpinner.setAdapter(mAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OpcionesItem seleccionado = (OpcionesItem) parent.getItemAtPosition(position);
                opcion = seleccionado.getOpcion();
                //Toast.makeText(quiz4.this,"Has seleccionado: "+opcion,Toast.LENGTH_SHORT).show();

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

    public void siguiente4(View view){
        String corrcta = "TCL";

        if (opcion.compareTo(corrcta)==0){
            puntuacion += 3;
        }else{
            puntuacion -= 2;
        }

        if (opcion != null){
            Intent intentquiz4 = new Intent(quiz4.this,quiz5.class);
            intentquiz4.putExtra("email",email);
            intentquiz4.putExtra("nombre",nombre);
            intentquiz4.putExtra("puntuacion",puntuacion);
            startActivity(intentquiz4);
        }else{
            Toast.makeText(this, "Seleccione alguna respuesta", Toast.LENGTH_SHORT).show();
        }
    }


}