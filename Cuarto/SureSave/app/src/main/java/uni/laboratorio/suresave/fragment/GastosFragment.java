package uni.laboratorio.suresave.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import uni.laboratorio.suresave.R;
import uni.laboratorio.suresave.modelos.Usuario;


public class GastosFragment extends Fragment {

    private EditText calendario_gastos;
    private EditText cantidad_gastos;
    private RadioButton comida, ropa, libro, informatica, transporte, otros;

    ImageButton btn_cancel_gastos;

    //ventana emergente para elegir la fecha
    private DatePickerDialog datePickerDialog;

    //creamos una variable de enviar datos interfaz
    EnviarDatosInterfaz enviarDatosInterfaz;

    //variables globales para guardar los datos introducidos
    String categoria_gastos;
    long fecha_gastos;
    double total_gastos;


    //muy importante este dato para poder tener un valor sumado a todos del usuario
    double gastos_usuario;

    //Variables necesarios para firebase
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();


    public GastosFragment() {
    }

    //este interfaz sirve para mandar datos al activity pagina principal
    public interface EnviarDatosInterfaz {
        void enviarDatos(String datos);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gastos, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //se referencian los imagebuttons del layout
        btn_cancel_gastos = view.findViewById(R.id.cancel_button_gastos);
        ImageButton btn_calendario = view.findViewById(R.id.btn_calendario);
        //se referencian los edit text
        calendario_gastos = view.findViewById(R.id.calendario_gastos);
        cantidad_gastos = view.findViewById(R.id.gastos);
        //se referencia el boton de añadir
        MaterialButton btn_anadir_gastos = view.findViewById(R.id.añadir_gastos);

        //se referencian todos los radioButtons
        comida = view.findViewById(R.id.comida);
        ropa = view.findViewById(R.id.ropa);
        libro = view.findViewById(R.id.libro);
        informatica = view.findViewById(R.id.informatica);
        transporte = view.findViewById(R.id.transporte);
        otros = view.findViewById(R.id.otros);

        //ponemos en el cuadrado del calendario la fecha actual con el metodo getDiaActual
        calendario_gastos.setText(getDiaActual());


        //al hacer click en el icono del calendario
        btn_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //iniciamos el dialogo de calendario para elegir una fecha
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //el mes viene por defecto en 0, como no exite ponemos enero
                        month = month + 1;
                        //el usuario elige el dia, mes y el año y lo guardamos en el string
                        String date = dayOfMonth + "/" + month + "/" + year;
                        //ponemos en el texto la fecha elegida
                        calendario_gastos.setText(date);

                        //guardamos en el variable global la fecha elegida por el usuario
                        fecha_gastos = comvertirFecha(date);
                    }
                };

                //aqui iniciamos el caledario
                Calendar calendar = Calendar.getInstance();
                int ano = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                //el estilo de la ventana emergente
                int style;
                style = R.style.Theme_AppCompat;
                //ponemos los parametros del dialogo
                datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, ano, mes, dia);
                //enseñamos el dialogo en si
                datePickerDialog.show();
            }
        });

        //dando click en la imagen de cruz para cancelar
        btn_cancel_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registramos el click y mandamos un string a la clase de pagina inicial
                String prueba = "Selecccionado";
                //se manda mediante una interfaz ya implementado
                enviarDatosInterfaz.enviarDatos(prueba);

            }
        });


        //damos click al boton añadir
        btn_anadir_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se puede dejarlo mas limpio
                //obtenemos el valor de los euros ya gastados
                obtenerGastosUsuario();

                //comprobamos si se ha elegido alguna opcion de radiogroup
                if (!comprobarOpciones()) {
                    Toast.makeText(getContext(), "Seleccione una de las opciones disponibles", Toast.LENGTH_SHORT).show();
                } else {
                    //comprobamos si la fecha introducida es con el formato correto
                    if (!comprobarFecha(calendario_gastos.getText().toString())) {
                        Toast.makeText(getContext(), "No coincide la fecha con el formato dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                    } else {
                        //la fecha introducida es correcta, ahora veamos si se ha escrito alguna cantidad en gastos
                        if (!comprobarCantidad(cantidad_gastos.getText().toString())) {
                            Toast.makeText(getContext(), "Introdzca un cantidad superior a 0", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getContext(), "Ha introducido " +
                             //       categoria_gastos + ">> " + fecha_gastos + " >>" +
                               //     total_gastos + " ", Toast.LENGTH_SHORT).show();

                            //ahora que tenemos todos lo datos, vamos a volcarlo en firebase
                            subirFirebase();
                        }
                    }
                }
            }

        });

        return view;
    }

    private void subirFirebase() {

        String gastosId = reference.push().getKey();
        HashMap<String, Object> datos_gastos = new HashMap<>();
        datos_gastos.put("usuarioid", auth.getCurrentUser().getUid());
        datos_gastos.put("gastosid", gastosId);
        datos_gastos.put("total", (double)total_gastos);
        datos_gastos.put("categoria", categoria_gastos);
        datos_gastos.put("fecha", fecha_gastos);

        //reference.child("Usuarios").child(auth.getCurrentUser().getUid()).child("gastos").setValue(total_gastos);

        reference.child("Gastos").child(auth.getCurrentUser().getUid()).child(gastosId).setValue(datos_gastos)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //actualizamos el objeto del usuario, la cantidad gastada
                            actualizarUsuario();
                            btn_cancel_gastos.performClick();
                        }
                    }
                });

    }

    private void actualizarUsuario() {
        reference.child("Usuarios").child(auth.getCurrentUser().getUid()).child("gastos").setValue(total_gastos+gastos_usuario);
    }

    private void obtenerGastosUsuario() {

        reference.child("Usuarios").child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        gastos_usuario = snapshot.getValue(Usuario.class).getGastos();
                        Log.i("Gastos fragment", "actualizarUsuario: obtenido los gastos del usuario:\t" +
                                gastos_usuario + " ");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    //comprobamos si se ha elegido algun boton
    private boolean comprobarOpciones() {
        /*
        return comida.isChecked() ||
                ropa.isChecked() ||
                libro.isChecked() ||
                informatica.isChecked() ||
                transporte.isChecked() ||
                otros.isChecked();*/

        if (comida.isChecked()) {
            categoria_gastos = "Comida";
            return true;
        } else if (ropa.isChecked()) {
            categoria_gastos = "Ropa";
            return true;
        } else if (libro.isChecked()) {
            categoria_gastos = "Libro";
            return true;
        } else if (informatica.isChecked()) {
            categoria_gastos = "Informatica";
            return true;
        } else if (transporte.isChecked()) {
            categoria_gastos = "Trasporte";
            return true;
        } else if (otros.isChecked()) {
            categoria_gastos = "Otros";
            return true;
        } else {
            return false;
        }

    }

    //comprobamos la cantidad introducida
    private boolean comprobarCantidad(String cantidad) {
        if (!cantidad.equals("")) {
            double cantidadNum = 0;
            try {
                cantidadNum = NumberFormat.getNumberInstance(Locale.FRANCE).parse(cantidad).doubleValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (cantidadNum >= 0) {
                Log.i("Fragment gastos", "comprobarCantidad: cantidadNum" + cantidadNum);
                total_gastos = cantidadNum;
                return true;
            }
        }
        return false;
    }

    //comprobamos el formato de la fecha si es correcta o no
    @SuppressLint("SimpleDateFormat")
    private boolean comprobarFecha(String fecha) {

        //comprobamos el string de la fecha con nuestro formato para ver si es correcto

        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(fecha);

        } catch (ParseException e) {
            return false;
        }
        fecha_gastos = comvertirFecha(fecha);
        return true;

    }

    private Long comvertirFecha(String fecha) {

        //convertimos el String en LocalDate

        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = simpleDateFormat.parse(fecha);

            fecha_gastos = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return fecha_gastos;
    }


    //no tengo ni idea de lo que hace
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            enviarDatosInterfaz = (EnviarDatosInterfaz) activity;
        } catch (RuntimeException e) {
            throw new RuntimeException(activity.toString() + "Se debe impletenar el metodo");
        }

    }

    //conseguimos el dia actual para ponerlo al inicio

    public String getDiaActual() {
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = mes + 1;
        //guardamos en el variable global la fecha por defecto actual
        String fecha = dia + "/" + mes + "/" + ano;
        fecha_gastos = comvertirFecha(fecha);
        return fecha;

    }


}