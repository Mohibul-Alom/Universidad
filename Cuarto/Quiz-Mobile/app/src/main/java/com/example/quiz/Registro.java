package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        EditText nombre = findViewById(R.id.editNameRegister);
        EditText email = findViewById(R.id.editEmailRegister);
        EditText password = findViewById(R.id.editContraRegister);

        nombre.setHintTextColor(Color.rgb(220,220,220));
        email.setHintTextColor(Color.rgb(220,220,220));
        password.setHintTextColor(Color.rgb(220,220,220));


    }

    public void Registar(View view) {
    }

    public void Inciar_Sesion(View view) {
        Intent inicio = new Intent(this,MainActivity.class);
        startActivity(inicio);

    }
}