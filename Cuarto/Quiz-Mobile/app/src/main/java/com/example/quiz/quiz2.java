package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class quiz2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private int puntuacion = 0;
    private String nombre;
    private String seleccion = null;
    Button btn1;


    private ListView lista;
    private List<String> significado;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        Intent intent = getIntent();
        nombre = intent.getExtras().getString("nombre");
        puntuacion = intent.getExtras().getInt("puntuacion");

        TextView textView = findViewById(R.id.textView10);
        textView.setText("Pregunta 2: "+nombre);

        btn1 =findViewById(R.id.button6);

        lista = findViewById(R.id.listView2);

        significado = new ArrayList<>();
        significado.add("Narrow File Connection");
        significado.add("Near Fild Comunication");
        significado.add("Near Front Chip");
        significado.add("Nec Field Connection");



        mAdapter = new LsitAdapter(quiz2.this,R.layout.itemrow,significado);

        lista.setAdapter(mAdapter);

        lista.setOnItemClickListener(this);


    }


    public void volver(View view) {
        Intent intentPrincipal = new Intent(this, MainActivity.class);
        startActivity(intentPrincipal);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.seleccion = significado.get(position);
        btn1.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Has seleccionado: "+significado.get(position), Toast.LENGTH_SHORT).show();

    }

    public void siguiente2(View view){
        String corrcta = "Near Fild Comunication";

        if (seleccion.compareTo(corrcta)==0){
            puntuacion += 3;
        }else{
            puntuacion -= 2;
        }

        if (seleccion != null){
            Intent intentquiz3 = new Intent(quiz2.this,quiz3.class);
            intentquiz3.putExtra("nombre",nombre);
            intentquiz3.putExtra("puntuacion",puntuacion);
            startActivity(intentquiz3);
        }else{
            Toast.makeText(this, "Seleccione alguna respuesta", Toast.LENGTH_SHORT).show();
        }

    }
}