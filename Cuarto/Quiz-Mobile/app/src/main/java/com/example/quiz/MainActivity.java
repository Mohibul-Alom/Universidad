package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private static String nombre;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email = findViewById(R.id.editName);
        EditText contraseña = findViewById(R.id.editContra);

        email.setHintTextColor(Color.rgb(220,220,220));
        contraseña.setHintTextColor(Color.rgb(220,220,220));



    }

    public void Inciar_Sesion(View view) {
        this.view = view;
        Intent intent = new Intent(this, quiz1.class);
        EditText editText = (EditText) findViewById(R.id.editName);
        String mensaje = editText.getText().toString();
        intent.putExtra("nombre", mensaje);
        startActivity(intent);
    }

    public void Registar(View view) {


    }
}