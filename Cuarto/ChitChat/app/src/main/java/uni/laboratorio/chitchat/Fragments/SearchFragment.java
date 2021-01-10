package uni.laboratorio.chitchat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.UserHandle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import uni.laboratorio.chitchat.Adapters.TagAdapter;
import uni.laboratorio.chitchat.Adapters.UsuarioAdapter;
import uni.laboratorio.chitchat.Model.Usuario;
import uni.laboratorio.chitchat.R;


public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Usuario> mUsers;
    private UsuarioAdapter usuarioAdapter;
    private SocialAutoCompleteTextView barraBusqueda;

    private RecyclerView recyclerViewTags;
    private List<String> mHashTags;
    private List<String> mHashTagsCount;

    private TagAdapter tagAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recycle_view_usuarios);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //tags------------------------
        recyclerViewTags = view.findViewById(R.id.recycle_view_tags);
        recyclerViewTags.setHasFixedSize(true);
        recyclerViewTags.setLayoutManager(new LinearLayoutManager(getContext()));

        mHashTags = new ArrayList<>();
        mHashTagsCount = new ArrayList<>();
        tagAdapter = new TagAdapter(getContext(), mHashTags, mHashTagsCount);
        recyclerViewTags.setAdapter(tagAdapter);

        //tags---------------------------

        mUsers = new ArrayList<>();
        usuarioAdapter = new UsuarioAdapter(getContext(), mUsers, true);
        recyclerView.setAdapter(usuarioAdapter);

        barraBusqueda = view.findViewById(R.id.barra_busqueda);

        leerUsuario();

        //tags
        leerTags();
        //tags

        barraBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarUsuario(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

                filtrar(s.toString());

            }
        });


        return view;
    }

    private void leerTags() {

        FirebaseDatabase.getInstance().getReference().child("HashTags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mHashTags.clear();
                mHashTagsCount.clear();

                for (DataSnapshot snapshot3 : snapshot.getChildren()) {
                    mHashTags.add(snapshot3.getKey());
                    mHashTagsCount.add(String.valueOf(snapshot3.getChildrenCount()));
                }

                tagAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void leerUsuario() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (TextUtils.isEmpty(barraBusqueda.getText().toString())) {
                    mUsers.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Usuario usuario = snapshot1.getValue(Usuario.class);
                        mUsers.add(usuario);
                    }

                    usuarioAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void buscarUsuario(String s) {

        Query query = FirebaseDatabase.getInstance().getReference().child("Usuarios")
                .orderByChild("Usuario").startAt(s).endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    Usuario usuario = snapshot2.getValue(Usuario.class);
                    mUsers.add(usuario);
                }

                usuarioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void filtrar(String text){
        List<String> mBuscarTags = new ArrayList<>();
        List<String> mBuscarNumTags = new ArrayList<>();

        for (String s: mHashTags){
            if (s.toLowerCase().contains(text.toLowerCase())){
                mBuscarTags.add(s);
                mBuscarNumTags.add(mHashTagsCount.get(mHashTags.indexOf(s)));
            }
        }
        tagAdapter.filtrar(mBuscarTags,mBuscarNumTags);
    }


}