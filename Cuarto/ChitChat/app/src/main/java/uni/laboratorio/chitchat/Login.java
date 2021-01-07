package uni.laboratorio.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    private EditText correo,password;
    private TextView entrar, titulo2;

    private FirebaseAuth mAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar_inicio);
        correo = findViewById(R.id.correo_inicio_sesion);
        password = findViewById(R.id.contra_inicio_sesion);
        entrar = findViewById(R.id.entrar);
        titulo2 = findViewById(R.id.titulo2);

        mAuth = FirebaseAuth.getInstance();

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_correo = correo.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_correo)||TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Login.this, "Rellen todos lo campos", Toast.LENGTH_SHORT).show();
                }else {
                    iniciarSesion(txt_correo,txt_password);
                }
            }
        });
    }

    private void iniciarSesion(String Correo, String Password) {

        entrar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(Color.rgb(246,9,119), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

         mAuth.signInWithEmailAndPassword(Correo,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){
                     progressBar.setVisibility(View.INVISIBLE);
                     Toast.makeText(Login.this, "Actualice el perfil para mejorar la experiencia", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(Login.this, MainActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);
                     finish();
                 }
             }
         }).addOnFailureListener( new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 progressBar.setVisibility(View.INVISIBLE);
                 Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });

    }

    public void crear_cuenta(View view) {

        Intent crearCuenta = new Intent(this,Registro.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(crearCuenta);

    }
}