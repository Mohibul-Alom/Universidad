package uni.laboratorio.suresave;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";

    private EditText usuario, contraseña;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.login_email);
        contraseña = findViewById(R.id.login_contraseña);

        auth = FirebaseAuth.getInstance();


    }

    public void autenticar(View view) {
        String txt_usuario = usuario.getText().toString();
        String txt_contraseña = contraseña.getText().toString();

        if (TextUtils.isEmpty(txt_usuario)||TextUtils.isEmpty(txt_contraseña)){
            Toast.makeText(Login.this, "Rellen todos lo campos", Toast.LENGTH_SHORT).show();
        }else {
            iniciarSesion(txt_usuario,txt_contraseña );
        }

    }

    private void iniciarSesion(String usuario, String contraseña) {

        auth.signInWithEmailAndPassword(usuario,contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "Actualice el perfil para mejorar la experiencia", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,PaginaInicial.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void registrar(View view) {
        Intent registro = new Intent(this, Registro.class);
        startActivity(registro);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent salir = new Intent(this, MainActivity.class);
        startActivity(salir);
    }

    public void salir(View view) {
        onBackPressed();
    }


}