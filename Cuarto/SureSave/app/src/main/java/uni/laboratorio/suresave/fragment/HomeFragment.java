package uni.laboratorio.suresave.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import uni.laboratorio.suresave.R;
import uni.laboratorio.suresave.adapter.AdapterItem;
import uni.laboratorio.suresave.modelos.Gastos;
import uni.laboratorio.suresave.modelos.Ingresos;
import uni.laboratorio.suresave.modelos.Movimiento;
import uni.laboratorio.suresave.modelos.Usuario;


public class HomeFragment extends Fragment {


    private ArrayList<Gastos> lista_gastos;
    private ArrayList<Ingresos> lista_ingresos;
    private ArrayList<Movimiento> lista_movimiento;


    private AdapterItem adapterItem;

    private Usuario usuario;

    private ImageView view_imagen_perfil;
    private TextView view_nombre_usuario;
    private TextView view_ingresos_cantidad;
    private TextView view_gastos_cantidad;
    private RecyclerView recycler_list;
    ProgressBar progressBar;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //referenciamos todos los objetos que vayamos a usar
        view_imagen_perfil = view.findViewById(R.id.imagen_perfil);
        view_nombre_usuario = view.findViewById(R.id.nombre_usuario);
        view_ingresos_cantidad = view.findViewById(R.id.txt_ingresos_cantidad);
        view_gastos_cantidad = view.findViewById(R.id.txt_gastos_cantidad);

        recycler_list = view.findViewById(R.id.recycle_list);
        //recycler_list.setHasFixedSize(true);
        //tendria que haber un
        //recycler_list.setLayoutManager(new RelativeLayout(getActivity()));

        progressBar = view.findViewById(R.id.progressBar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        progressBar.getIndeterminateDrawable()
                .setColorFilter(Color.rgb(0,0,255), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);
        //inicializamos los arrays
        lista_ingresos = new ArrayList<>();
        lista_gastos = new ArrayList<>();
        lista_movimiento = new ArrayList<>();

        //iniciamos el objeto Usuario
        usuario = new Usuario();

        //hay que recuperar los datos del usuario ademas de gastos e ingresos
        infoUsuario();


    }

    private void infoUsuario() {

        reference.child("Usuarios").child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        usuario.setId(snapshot.getValue(Usuario.class).getId());
                        usuario.setNombre(snapshot.getValue(Usuario.class).getNombre());
                        usuario.setEmail(snapshot.getValue(Usuario.class).getEmail());
                        usuario.setImagenUrl(snapshot.getValue(Usuario.class).getImagenUrl());
                        usuario.setGastos(snapshot.getValue(Usuario.class).getGastos());
                        usuario.setIngresos(snapshot.getValue(Usuario.class).getIngresos());


                        view_nombre_usuario.setText(usuario.getNombre());
                        view_ingresos_cantidad.setText(String.valueOf(usuario.getIngresos()));
                        view_gastos_cantidad.setText(String.valueOf(usuario.getGastos()));

                        infoMovimientos();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i("Home fragment", "onCancelled: " + error.getMessage());
                    }
                });
    }

    private void infoMovimientos() {

        if (!lista_movimiento.isEmpty()){
            lista_movimiento.clear();
        }

        reference.child("Gastos").child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!lista_gastos.isEmpty()){
                            lista_gastos.clear();
                        }
                        //guardamos uno a uno todos los gastos del usuario en el array
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Gastos g1 = ds.getValue(Gastos.class);
                            System.out.println(g1.toString());
                            lista_gastos.add(ds.getValue(Gastos.class));

                            Movimiento m1 = new Movimiento();
                            m1.setTipo(true);
                            m1.setCategoria(ds.getValue(Gastos.class).getCategoria());
                            m1.setCantidad(ds.getValue(Gastos.class).getTotal());
                            m1.setFecha(ds.getValue(Gastos.class).getFecha());
                            m1.setIdMovimiento(ds.getValue(Gastos.class).getGastosid());
                            lista_movimiento.add(m1);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        //recuperamos los ingresos
        reference.child("Ingresos").child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!lista_ingresos.isEmpty()){
                            lista_ingresos.clear();
                        }
                        //guardamos los ingresos del usuario en el array
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            lista_ingresos.add(ds.getValue(Ingresos.class));
                            System.out.println(ds.getValue(Ingresos.class).toString());

                            Movimiento m2 = new Movimiento();
                            m2.setTipo(false);
                            m2.setCategoria(ds.getValue(Ingresos.class).getCategoria());
                            m2.setCantidad(ds.getValue(Ingresos.class).getTotal());
                            m2.setFecha(ds.getValue(Ingresos.class).getFecha());
                            m2.setIdMovimiento(ds.getValue(Ingresos.class).getIngresosid());
                            lista_movimiento.add(m2);

                        }
                        //añadido despues
                        //ahora debemos meter ambas listas en el array de movimiento y ordenarlo


                        //ordenamos los movimientos
                        Collections.sort(lista_movimiento);
                        Collections.reverse(lista_movimiento);
                        System.out.println(lista_movimiento.toString());

                        System.out.println("*****************************************************************");
                        System.out.println("Lista de movimiento: "+lista_movimiento);
                        adapterItem = new AdapterItem(getContext(), lista_movimiento);
                        recycler_list.setAdapter(adapterItem);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}