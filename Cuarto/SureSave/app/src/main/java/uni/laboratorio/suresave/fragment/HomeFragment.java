package uni.laboratorio.suresave.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uni.laboratorio.suresave.R;
import uni.laboratorio.suresave.adapter.AdapterItem;
import uni.laboratorio.suresave.modelos.Gastos;


public class HomeFragment extends Fragment {

    RecyclerView recycler_list;

    ArrayList<Gastos> lista_gastos;
    AdapterItem adapterItem;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recycler_list = view.findViewById(R.id.recycle_list);

        return view;
    }
}