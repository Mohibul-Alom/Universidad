package uni.laboratorio.chitchat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import uni.laboratorio.chitchat.Model.Usuario;
import uni.laboratorio.chitchat.R;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder>{

    private Context context;
    private List<Usuario> mUsuarios;
    private boolean esFragmento;

    private FirebaseUser firebaseUser;

    public UsuarioAdapter(Context context, List<Usuario> mUsuarios, boolean esFragmento) {
        this.context = context;
        this.mUsuarios = mUsuarios;
        this.esFragmento = esFragmento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usuario_item,parent,false);
        return new UsuarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Usuario usuario = mUsuarios.get(position);
        holder.btnSeguir.setVisibility(View.VISIBLE);

        holder.nombreUsuario.setText(usuario.getUsuario());
        holder.nombreCompleto.setText(usuario.getNombreCompleto());

        Picasso.get().load(usuario.getImageUrl()).placeholder(R.mipmap.ic_launcher).into(holder.imagenPerfil);

        esSeguido(usuario.getId(),holder.btnSeguir);

        if (usuario.getId().equals(firebaseUser.getUid())){
            holder.btnSeguir.setVisibility(View.GONE);
        }

        holder.btnSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnSeguir.getText().toString().equals("Seguir")){
                    FirebaseDatabase.getInstance().getReference().child("Seguir")
                            .child((firebaseUser.getUid())).child("Siguiendo").child(usuario.getId()).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Seguir")
                            .child(usuario.getId()).child("Seguidores").child(firebaseUser.getUid()).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Seguir")
                            .child((firebaseUser.getUid())).child("Siguiendo").child(usuario.getId()).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Seguir")
                            .child(usuario.getId()).child("Seguidores").child(firebaseUser.getUid()).removeValue();
                }
            }
        });

    }

    private void esSeguido(final String id, Button btnSeguir) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Seguir")
                .child(firebaseUser.getUid()).child("Siguiendo");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(id).exists()){
                    btnSeguir.setText("Siguiendo");
                }else {
                    btnSeguir.setText("Seguir");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView imagenPerfil;
        public TextView nombreUsuario;
        public TextView nombreCompleto;
        public Button btnSeguir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenPerfil = itemView.findViewById(R.id.imagen_perfil);
            nombreUsuario = itemView.findViewById(R.id.nombreUsuario);
            nombreCompleto = itemView.findViewById(R.id.nombre_completo);
            btnSeguir = itemView.findViewById(R.id.btn_seguir);



        }
    }

}
