package uni.laboratorio.chitchat.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import uni.laboratorio.chitchat.Login;
import uni.laboratorio.chitchat.Model.Post;
import uni.laboratorio.chitchat.Model.Usuario;
import uni.laboratorio.chitchat.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> mPosts;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item,parent,false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Post post = mPosts.get(position);
        Picasso.get().load(post.getImageurl()).into(holder.post_imagen);
        holder.descripcion.setText(post.getDescripcion());

        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(post.getPublicador()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);

                usuario.setImageUrl(""+snapshot.child("imageurl").getValue());

                String URL = ""+snapshot.child("imageurl").getValue();

                Log.i("PostAdapter", "onDataChange: "+usuario.getImageUrl());
                Log.i("PostAdapter", "onDataChange URL: "+URL);

                if (usuario.getImageUrl().equals("default")||usuario.getImageUrl().isEmpty()){
                    holder.perfil_imagen.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Picasso.get().load(usuario.getImageUrl()).into(holder.perfil_imagen);
                }


                holder.usuario_home.setText(usuario.getUsuario());
                holder.author.setText(usuario.getNombreCompleto());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView perfil_imagen,post_imagen,like,comment,save,more;

        public TextView usuario_home, num_likes,author,num_comentarios;

        SocialTextView descripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            perfil_imagen = itemView.findViewById(R.id.perfil_imagen);
            post_imagen = itemView.findViewById(R.id.post_imagen);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            save = itemView.findViewById(R.id.save);
            more = itemView.findViewById(R.id.more);

            usuario_home = itemView.findViewById(R.id.usuario_home);
            num_likes = itemView.findViewById(R.id.num_likes);
            author = itemView.findViewById(R.id.num_likes);
            num_comentarios = itemView.findViewById(R.id.num_comentarios);

            descripcion = itemView.findViewById(R.id.descripcion_home);




        }
    }


}
