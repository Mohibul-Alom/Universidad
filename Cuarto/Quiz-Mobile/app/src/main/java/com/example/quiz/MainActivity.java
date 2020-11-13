package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    BaseDeDatos db;
    private static String nombre;
    private View view;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences datos = getSharedPreferences("datos",MODE_PRIVATE);
        boolean primerInicio = datos.getBoolean("primerInicio",true);

        if (primerInicio){
            pantalla_nuevo_usuario();
        }

        db = new BaseDeDatos(this);


        email = findViewById(R.id.editName);
        password = findViewById(R.id.editContra);

        email.setHintTextColor(Color.rgb(220,220,220));
        password.setHintTextColor(Color.rgb(220,220,220));



    }

    public void Inciar_Sesion(View view) {

        String textEmail = email.getText().toString();
        String textContra = password.getText().toString();

        if (textEmail.equals("")||textContra.equals("")){
            Toast.makeText(this, "Rellene los campos", Toast.LENGTH_SHORT).show();
        }else{

            
            Boolean verificarUsuario = db.checkEmailPass(textEmail,textContra);

            //si se ha introducido correctamente los datos de inicio de seision
            if (verificarUsuario==true){
                Intent intent = new Intent(this, quiz1.class);
                //EditText editText = (EditText) findViewById(R.id.editName);
                //String mensaje = editText.getText().toString();

                String mensaje = db.obtenerNombre(textEmail);
                intent.putExtra("nombre", mensaje);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Email y/o Contaseña incorrecto", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public void Registar(View view) {
        Intent registro = new Intent(this,Registro.class);
        startActivity(registro);

    }

    public void pantalla_nuevo_usuario(){
        startActivity(new Intent(this,TutorialAdapter.class));
    }

}