package uni.laboratorio.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    private EditText usuario,correo,password;
    private TextView crear;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    ProgressDialog pd;


    private static final String TAG = "Registro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        usuario = findViewById(R.id.usuario_registro);
        correo = findViewById(R.id.email_registro);
        password = findViewById(R.id.contra_registro);
        crear = findViewById(R.id.crear_registro);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(this);


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_usuario = usuario.getText().toString();
                String txt_correo = correo.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_usuario)||TextUtils.isEmpty(txt_correo)||TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Registro.this,"Rellene todos lo campos",Toast.LENGTH_SHORT).show();
                }else if (txt_password.length()<6){
                    Toast.makeText(Registro.this,"La contraseña debe tener al menos 5 caracteres",Toast.LENGTH_SHORT).show();
                }else{
                    registerUsuario(txt_usuario,txt_correo,txt_password);
                }
            }
        });


    }

    private void registerUsuario(String Usuario, String Correo, String Password) {

        pd.setMessage("Cargando...");
        pd.onStart();

        mAuth.createUserWithEmailAndPassword(Correo,Password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        HashMap<String,Object>map = new HashMap<>();
                        map.put("Correo",Correo);
                        map.put("Usuario",Usuario);
                        map.put("id",mAuth.getCurrentUser().getUid());

                        reference.child("Usuarios").child(mAuth.getCurrentUser().getUid()).setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Log.d(TAG, "onComplete() called with: task = [" + task + "]");
                                            Toast.makeText(Registro.this, "Actualice", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Registro.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registro.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
        });
    }

/*
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(Correo, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Registro de usuario: exito");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {

                            Log.w(TAG, "Registro de usuario: fallo", task.getException());
                            Toast.makeText(Registro.this, "Ha falla la identificación.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        Intent intent = new Intent(Registro.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }


 */

    public void iniciar_sesion(View view) {
        Intent iniciarSesion = new Intent(this,Login.class);
        startActivity(iniciarSesion);
    }
}