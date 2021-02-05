package uni.laboratorio.suresave.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import uni.laboratorio.suresave.R;

public class IngresosFragment extends Fragment {

    private ImageButton btn_cancel_ingresos;
    private ImageButton btn_calendario;
    private EditText calendario_ingresos;


    GastosFragment.EnviarDatosInterfaz enviarDatosInterfaz;

    //este interfaz sirve para mandar datos al activity pagina principal
    public interface EnviarDatosInterfaz {
        public void enviarDatos(String datos);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_ingresos, container, false);

        btn_cancel_ingresos = view.findViewById(R.id.cancel_button_ingresos);

        btn_cancel_ingresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prueba = "Selecccionado";
                enviarDatosInterfaz.enviarDatos(prueba);
            }
        });

        return view;
    }

    //no tengo ni idea de lo que hace
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            enviarDatosInterfaz = (GastosFragment.EnviarDatosInterfaz) activity;
        }catch (RuntimeException e){
            throw new RuntimeException(activity.toString()+"Se debe impletenar el metodo");
        }

    }

}