package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class quiz1 extends AppCompatActivity {

    RadioButton r1,r2,r3,r4;
    private int puntuacion = 0;
    private static String nombre;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        Intent intent = getIntent();
        nombre = intent.getExtras().getString("nombre");

        TextView textView = findViewById(R.id.textView5);
        textView.setText("Pregunta 1: "+nombre);

        r1 = (RadioButton)findViewById(R.id.radioButton);
        r2 = (RadioButton)findViewById(R.id.radioButton2);
        r3 = (RadioButton)findViewById(R.id.radioButton3);
        r4 = (RadioButton)findViewById(R.id.radioButton4);

        btn1 = findViewById(R.id.button);
    }


    public void siguiente(View view) {
        if (view.getId()==R.id.button){
            validar();

        }

        if (r1.isChecked()==true||r2.isChecked()==true||
            r3.isChecked()==true||r4.isChecked()==true){
            Intent intentquiz2 = new Intent(this, quiz2.class);
            intentquiz2.putExtra("nombre",nombre);
            intentquiz2.putExtra("puntuacion",puntuacion);
            startActivity(intentquiz2);

        }

    }

    public void validar(){
        if (r4.isChecked()==true){
            puntuacion = 3;
        }else{
            puntuacion -=2;
        }


    }

    public void volver(View view) {
        Intent intentPrincipal = new Intent(this, MainActivity.class);
        startActivity(intentPrincipal);

    }
}