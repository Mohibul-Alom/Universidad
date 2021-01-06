package uni.laboratorio.chitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void iniciar_sesion(View view) {
        Intent iniciarSesion = new Intent(this,Login.class);
        startActivity(iniciarSesion);
    }
}