package uni.laboratorio.chitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    TextView titulo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titulo2 = findViewById(R.id.titulo2);


    }

    public void crear_cuenta(View view) {

        Intent crearCuenta = new Intent(this,Registro.class);
        startActivity(crearCuenta);

    }
}