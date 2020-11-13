package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    BaseDeDatos db;
    Button register;
    EditText nombre, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = new BaseDeDatos(this);

        nombre = findViewById(R.id.editNameRegister);
        email = findViewById(R.id.editEmailRegister);
        password = findViewById(R.id.editContraRegister);

        nombre.setHintTextColor(Color.rgb(220,220,220));
        email.setHintTextColor(Color.rgb(220,220,220));
        password.setHintTextColor(Color.rgb(220,220,220));

        register = findViewById(R.id.btnRegistrar2);

    }

    public void Registar(View view) {
        String textNombre = nombre.getText().toString();
        String textEmail = email.getText().toString();
        String textContra = password.getText().toString();

        if (textEmail.equals("") || textContra.equals("") || textNombre.equals("")){
            Toast.makeText(Registro.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
        }else{

            if (validarEmail(textEmail)==true) {

                Boolean EmailBaseDeDatos = db.checkmail(textEmail);

                if (EmailBaseDeDatos == true) {

                    Boolean insertar = db.insert(textNombre, textEmail, textContra);

                    if (insertar == true) {
                        Toast.makeText(Registro.this, "Registro completado", Toast.LENGTH_SHORT).show();

                        register.setVisibility(View.INVISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Registro.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }, 100);

                    }
                } else {

                    Toast.makeText(Registro.this, "Email ya existe", Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(Registro.this, "Introduzca un email valido", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void Inciar_Sesion(View view) {
        Intent inicio = new Intent(this,MainActivity.class);
        startActivity(inicio);

    }

    //metodo para  ver si el texto insertado es un email o no
    public boolean validarEmail(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}