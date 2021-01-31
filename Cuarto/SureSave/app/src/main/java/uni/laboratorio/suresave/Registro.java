package uni.laboratorio.suresave;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    private static final String TAG = "Registro";

    private EditText nombre, email,contraseña;
    private MaterialButton btn_registrar;

    private FirebaseAuth auth;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        nombre = findViewById(R.id.registro_nombre);
        email = findViewById(R.id.registro_email);
        contraseña = findViewById(R.id.registro_contraseña);

        btn_registrar = findViewById(R.id.btn_registrar);


        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();


    }


    public void autenticar(View view) {

        String txt_nombre = nombre.getText().toString();
        String txt_email = email.getText().toString();
        String txt_contraseña = contraseña.getText().toString();

        if (TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_nombre)||TextUtils.isEmpty(txt_contraseña)){
            Toast.makeText(Registro.this,"Rellene todos lo campos",Toast.LENGTH_SHORT).show();
        }else if (txt_contraseña.length()<6){
            Toast.makeText(Registro.this,"La contraseña debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
        }else{
            registerUsuario(txt_nombre,txt_email,txt_contraseña);
        }


    }

    private void registerUsuario(String nombre, String email, String contraseña) {

        auth.createUserWithEmailAndPassword(email,contraseña)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        HashMap<String,Object> datosUsuario = new HashMap<>();
                        datosUsuario.put("id",auth.getCurrentUser().getUid());
                        datosUsuario.put("nombre",nombre);
                        datosUsuario.put("usuario","dafault");
                        datosUsuario.put("email",email);
                        datosUsuario.put("imagenUrl","default");

                        reference.child("Usuarios").child(auth.getCurrentUser().getUid()).setValue(datosUsuario)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(Registro.this, "Actualice el perfil para mejorar la experiencia", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Registro.this,PaginaInicial.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registro.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void iniciarSesion(View view) {
        Intent inicio = new Intent(this, Login.class);
        startActivity(inicio);
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