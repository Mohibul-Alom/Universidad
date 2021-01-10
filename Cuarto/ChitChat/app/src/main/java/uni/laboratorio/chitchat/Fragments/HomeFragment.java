package uni.laboratorio.chitchat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uni.laboratorio.chitchat.Adapters.PostAdapter;
import uni.laboratorio.chitchat.Model.Post;
import uni.laboratorio.chitchat.R;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private List<String> siguiendoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewPosts = view.findViewById(R.id.recycle_view_posts_home);
        recyclerViewPosts.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerViewPosts.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);

        recyclerViewPosts.setAdapter(postAdapter);

        siguiendoList = new ArrayList<>();

        verficarSiguiendoUsuario();

        return view;
    }

    private void verficarSiguiendoUsuario() {

        FirebaseDatabase.getInstance().getReference().child("Seguir").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("Siguiendo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                siguiendoList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    siguiendoList.add(snapshot1.getKey());
                }

                leerPosts();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void leerPosts() {

        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();

                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    Post post = snapshot2.getValue(Post.class);

                    for (String id : siguiendoList) {
                        if (post.getPublicador().equals(id)) {
                            postList.add(post);
                        }
                    }

                }

                postAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}