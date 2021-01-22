package uni.laboratorio.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import uni.laboratorio.chitchat.Model.Usuario;

public class CommentActivity extends AppCompatActivity {

    private EditText addComment;
    private CircleImageView imagePerfil;
    private TextView post;

    private String postId;
    private String authorID;

    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addComment = findViewById(R.id.add_comment);
        imagePerfil = findViewById(R.id.imagen_perfil);
        post = findViewById(R.id.post);
        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        authorID = intent.getStringExtra("authorId");
        fuser = FirebaseAuth.getInstance().getCurrentUser();


        getUserImage();


    }

    private void getUserImage() {

        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(fuser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                if (usuario.getImageUrl().equals("default")){
                    imagePerfil.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Picasso.get().load(usuario.getImageUrl()).into(imagePerfil);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}