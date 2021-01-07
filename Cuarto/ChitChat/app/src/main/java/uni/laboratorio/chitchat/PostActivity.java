package uni.laboratorio.chitchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    ProgressBar progressBar;

    private Uri imagenUri;
    private String imagenUrL;
    private ImageView cerrar,imagen_anadido;
    private TextView post;
    SocialAutoCompleteTextView descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        progressBar = findViewById(R.id.progressBar);
        cerrar = findViewById(R.id.cerrar);
        imagen_anadido = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        descripcion = findViewById(R.id.descripcion);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this,MainActivity.class));
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        CropImage.activity().start(PostActivity.this);
    }

    private void upload() {


        progressBar.getIndeterminateDrawable()
                .setColorFilter(Color.rgb(246,9,119), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        if (imagenUri != null){
            StorageReference filePath = FirebaseStorage.getInstance().getReference("Posts")
                    .child(System.currentTimeMillis()+"."+ getFileExtension(imagenUri));
            StorageTask subir = filePath.putFile(imagenUri);
            subir.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(this, new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri descargarUri = task.getResult();
                    imagenUrL = descargarUri.toString();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                    String postId = ref.push().getKey();

                    HashMap<String,Object> map = new HashMap<>();
                    map.put("postId",postId);
                    map.put("imageurl",imagenUrL);
                    map.put("descripcion",descripcion.getText().toString());
                    map.put("publicador", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    ref.child(postId).setValue(map);

                    DatabaseReference mHashTagRef = FirebaseDatabase.getInstance().getReference().child("HashTags");
                    List<String> hashtags = descripcion.getHashtags();
                    if (!hashtags.isEmpty()){
                        for (String tag:hashtags){
                            map.clear();
                            map.put("tag",tag.toLowerCase());
                            map.put("postid",postId);

                            mHashTagRef.child(tag.toLowerCase()).setValue(map);
                        }
                    }

                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(PostActivity.this,MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "No se ha elegido ninguna imagen", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtension(Uri uri) {

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            imagenUri = result.getUri();
            imagen_anadido.setImageURI(imagenUri);
        }else {
            Toast.makeText(this, "Intenta de nuevo, por favor", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this,MainActivity.class));
            finish();
        }

    }
}