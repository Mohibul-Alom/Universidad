package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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



    }

    public void Jugar(View view) {
        this.view = view;
        Intent intent = new Intent(this, quiz1.class);
        EditText editText = (EditText) findViewById(R.id.editName);
        String mensaje = editText.getText().toString();
        intent.putExtra("nombre", mensaje);
        startActivity(intent);
    }
}