package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class quiz1 extends AppCompatActivity {

    RadioButton r1,r2,r3,r4;
    RadioGroup grupo;
    private int puntuacion = 0;
    private static String nombre;
    private ImageView imageNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);



        Intent intent = getIntent();
        nombre = intent.getExtras().getString("nombre");

        TextView textView = findViewById(R.id.textView5);
        textView.setText("Pregunta 1: "+nombre);

        grupo = findViewById(R.id.radiogroup1);


        r1 = (RadioButton)findViewById(R.id.radioButton);
        r2 = (RadioButton)findViewById(R.id.radioButton2);
        r3 = (RadioButton)findViewById(R.id.radioButton3);
        r4 = (RadioButton)findViewById(R.id.radioButton4);

        imageNext = findViewById(R.id.siguiente1);
        ImageView imagePrevious = findViewById(R.id.anterior1);

        imageNext.setVisibility(View.INVISIBLE);


        grupo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                imageNext.setVisibility(View.VISIBLE);
                Toast.makeText(quiz1.this,"Se ha pulsado",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void siguiente(View view) {
        if (view.getId()==R.id.siguiente1){
            if (r4.isChecked()==true){
                puntuacion = 3;

            }else{
                puntuacion -=2;
            }

        }

        if (r1.isChecked()==true||r2.isChecked()==true||
            r3.isChecked()==true||r4.isChecked()==true){
            Intent intentquiz2 = new Intent(this, quiz2.class);
            intentquiz2.putExtra("nombre",nombre);
            intentquiz2.putExtra("puntuacion",puntuacion);
            startActivity(intentquiz2);

        }

    }


    public void volver(View view) {
        Intent intentPrincipal = new Intent(this, MainActivity.class);
        startActivity(intentPrincipal);
        finish();
    }

    public void RadioGroup(View view) {
        imageNext.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}