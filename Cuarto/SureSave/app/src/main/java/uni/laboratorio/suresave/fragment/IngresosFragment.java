package uni.laboratorio.suresave.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

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

public class IngresosFragment extends Fragment {

    private ImageButton btn_cancel_ingresos;
    private ImageButton btn_calendario;
    private EditText calendario_ingresos, cantidad_ingresos;
    private RadioButton bizum, trasferencia, nomina, otros;
    
    //ventana emergente para elegir la fecha
    private DatePickerDialog datePickerDialog;
    
    //Variables globales para guardar los datos introducidos
    String categoria_ingresos;
    long fecha_ingresos;
    double total_ingresos;
    
    //muy importante recoger el dato de los ingresos del usuario
    double ingreso_usuario;
    
    //Variables necesarios para firebase
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    
    //creamos una variable para la interfaz
    //hay que separar los interfaces
    GastosFragment.EnviarDatosInterfaz enviarDatosInterfaz;

    public IngresosFragment() {
    }

    //este interfaz sirve para mandar datos al activity pagina principal
    public interface EnviarDatosInterfaz {
        public void enviarDatos(String datos);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_ingresos, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //refenciamos los imageButtons del layout
        btn_cancel_ingresos = view.findViewById(R.id.cancel_button_ingresos);
        btn_calendario = view.findViewById(R.id.imagen_calendario);
        //refenciamos los edit text
        calendario_ingresos = view.findViewById(R.id.calendario_ingresos);
        cantidad_ingresos = view.findViewById(R.id.ingresos);
        //Referenciamos el boton añadir 
        MaterialButton btn_anadir_ingresos = view.findViewById(R.id.añadir_ingresos);

        //refenciamos los radioButton
        bizum=view.findViewById(R.id.bizum);
        trasferencia=view.findViewById(R.id.trasferencia);
        nomina=view.findViewById(R.id.nomina);
        otros=view.findViewById(R.id.otros);
        
        //ponemos en el cuadrado del calendario la fecha actual con el metodo getDiaActual
        calendario_ingresos.setText(getDiaActual());

        //al hacer click en el icono del calendario aparece
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
                        calendario_ingresos.setText(date);

                        //guardamos en el variable global la fecha elegida por el usuario
                        fecha_ingresos = convertirFecha(date);
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

        //dando click en la imagen de cruz para cancelar mandamos un mensaje para quitar
        //el fragment
        btn_cancel_ingresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prueba = "Selecccionado";
                enviarDatosInterfaz.enviarDatos(prueba);
            }
        });

        //click listener para el boton añadir
        btn_anadir_ingresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se puede dejarlo mas limpio
                //obtenermos los ingresos previos del usuario
                obtenerGastosUsuario();
                //comprobamos si se ha elegido alguna opcion de radiogroup
                if (!comprobarOpciones()){
                    //como es false, inidicamos al usuario que debe seleccionar alguna opcion
                    Toast.makeText(getContext(), "Seleccione una de las opciones disponibles", Toast.LENGTH_SHORT).show();
                }else{
                    //comprobamos que la fecha introducida es con el formato correcto
                    if (!comprobarFecha(calendario_ingresos.getText().toString())){
                        Toast.makeText(getContext(), "No coincide la fecha con el formato dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                    }else {
                        //comprobamos que se haya introducido una numero mayor a 0 en el apartado de edittext ingresos
                        if (!comprobarCantidad(cantidad_ingresos.getText().toString())){
                            Toast.makeText(getContext(), "Introdzca un cantidad superior a 0", Toast.LENGTH_SHORT).show();
                        }else{
                            //tenemos todos los datos correctos, asi que vamos a subirlo a firebase
                            subirFirebase();
                        }
                    }
                }
            }
        });

        return view;
    }

    private void subirFirebase() {

        //genermos un id único
        String ingresosId = reference.push().getKey();
        // creamos un hashmas equivalente al objeto Ingresos
        HashMap<String,Object> datos_ingresos = new HashMap<>();
        datos_ingresos.put("usuarioid",auth.getCurrentUser().getUid());
        datos_ingresos.put("ingresosid",ingresosId);
        datos_ingresos.put("total",(double)total_ingresos);
        datos_ingresos.put("categoria",categoria_ingresos);
        datos_ingresos.put("fecha",fecha_ingresos);

        //subirmos el hashmap a la base de datos
        reference.child("Ingresos").child(auth.getCurrentUser().getUid())
                .child(ingresosId).setValue(datos_ingresos)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //ahora que se completado la subida debemos actualizar el valor de los ingresos del usuario
                        actualizarUsuario();
                        //cerramos el fragment
                        btn_cancel_ingresos.performClick();
                    }
                    //es comveniente añadir un failure listener por si acaso
                });
    }

    private void actualizarUsuario() {
        //ponemos el nuevo valor de gastos que esta conformado por total_ingresos + ingresos usuario
        reference.child("Usuarios").child(auth.getCurrentUser().getUid())
                .child("ingresos").setValue(total_ingresos+ingreso_usuario);
    }

    private boolean comprobarCantidad(String cantidad) {

        //comprobamos primero que no este vacio el campo
        if (!cantidad.equals("")){
            double cantidadNum = 0;
            try {
                //cambiamos el formato introducido de 25,48 a 25.48
                cantidadNum = NumberFormat.getNumberInstance(Locale.FRANCE).parse(cantidad).doubleValue();
                //comprobamos que el valor sea mayor que 0
                if (cantidadNum>=0){
                    //lo guardamos en el variable global
                    total_ingresos = cantidadNum;
                    return true;
                }
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        //si esta vacio o es un numero inferior a 0
        return false;
    }

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
        fecha_ingresos = convertirFecha(fecha);
        return true;

    }

    private boolean comprobarOpciones() {
        //dependiendo de la opcion seleccionada actualizamos la categoria
        if (bizum.isChecked()) {
            categoria_ingresos = "Bizum";
            return true;
        } else if (trasferencia.isChecked()) {
            categoria_ingresos = "Transferencia";
            return true;
        } else if (nomina.isChecked()) {
            categoria_ingresos = "Nómina";
            return true;
        } else if (otros.isChecked()) {
            categoria_ingresos = "Otros";
            return true;
        } else {
            //si no se selecciona nada mandamos un false
            return false;
        }

    }

    private void obtenerGastosUsuario() {

        //para obtener los ingresos hay que abrir un value event listener
        reference.child("Usuarios").child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ingreso_usuario = snapshot.getValue(Usuario.class).getIngresos();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private String getDiaActual() {
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = mes + 1;
        //guardamos en el variable global la fecha por defecto actual
        String fecha = dia + "/" + mes + "/" + ano;
        fecha_ingresos = convertirFecha(fecha);
        return fecha;
    }

    private long convertirFecha(String fecha) {
        //convertimos el String en long

        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = simpleDateFormat.parse(fecha);

            fecha_ingresos = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return fecha_ingresos;
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