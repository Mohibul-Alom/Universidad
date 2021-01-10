package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BaseDeDatos db;
    private static String nombre;
    private EditText email, password;
    private SharedPreferences onBoardingScreen;
    private Usuario u1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musica_fondo();
        u1 = new Usuario();

        onBoardingScreen = getSharedPreferences("onBoardScreen",MODE_PRIVATE);

        Boolean esPrimeraVez = onBoardingScreen.getBoolean("primeraVez",true);

        if (esPrimeraVez){
            SharedPreferences.Editor editor = onBoardingScreen.edit();
            editor.putBoolean("primeraVez",false);
            editor.commit();
            primerInicio();
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

                //conseguimos el usuario de la base de datos
                u1 = db.getUsuario(textEmail);


                Intent intent = new Intent(this, Quiz1.class);
                intent.putExtra("nombre", u1.getNombre());
                intent.putExtra("email",u1.getEmail());
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

    public void primerInicio(){
        Intent primer = new Intent(this,OnBoarding.class);
        startActivity(primer);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void musica_fondo(){

        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.soho_at_peace);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }


}

