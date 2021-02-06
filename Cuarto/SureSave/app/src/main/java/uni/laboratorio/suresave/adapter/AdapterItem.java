package uni.laboratorio.suresave.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uni.laboratorio.suresave.R;
import uni.laboratorio.suresave.modelos.Gastos;
import uni.laboratorio.suresave.modelos.Ingresos;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder>{

    Context context;

    ArrayList<Gastos> listaGastos;
    ArrayList<Ingresos> listaIngresos;
    boolean seleccion;

    public AdapterItem(Context context, ArrayList<Gastos> listaGastos, ArrayList<Ingresos> listaIngresos, boolean seleccion) {
        this.context = context;
        this.listaGastos = listaGastos;
        this.listaIngresos = listaIngresos;
        this.seleccion = seleccion;
    }

    @NonNull
    @Override
    public AdapterItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItem.ItemViewHolder holder, int position) {
        holder.viewBind((listaGastos.get(position)));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {


        //declaramos las cosas del layout
        TextView fecha,categoria,cantidad;
        ImageView imagen_categoria;
        LinearLayout barra_indicador;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            fecha = itemView.findViewById(R.id.fecha);
            categoria = itemView.findViewById(R.id.categoria);
            cantidad = itemView.findViewById(R.id.cantidad);
            imagen_categoria = itemView.findViewById(R.id.imagen_categorias);
            barra_indicador = itemView.findViewById(R.id.barra_indicador);

        }

        @SuppressLint("ResourceAsColor")
        public void viewBind(Gastos gastos) {
            //FBD la fecha esta en long, de long hay que pasar a date, y de date hay que pasarlo a String
            //hay que pasar a string la fecha para poner escribirlo en el edittext
            long tempLong = gastos.getFecha();
            fecha.setText((int)gastos.getFecha());

            categoria.setText(gastos.getCategoria());
            //esta mierda no deja ponerlo en double, luego hay que buscar una solucion
            Double temp = gastos.getTotal();
            String gastoTemp = Double.toString(temp);
            cantidad.setText(gastoTemp);

            //establecemos una imagen por cada categoria
            //cambiar despues por switch case que queda más bonito

            switch (gastos.getCategoria()){
                case "Ropa":
                    imagen_categoria.setImageResource(R.drawable.clothes);
                    break;
                case "Comida":
                    imagen_categoria.setImageResource(R.drawable.restaurant);
                    break;
                case "Libro":
                    imagen_categoria.setImageResource(R.drawable.books);
                    break;
                case "Informatica":
                    imagen_categoria.setImageResource(R.drawable.computadora);
                    break;
                case "Trasporte":
                    imagen_categoria.setImageResource(R.drawable.bus);
                    break;
                case "Otros":
                    imagen_categoria.setImageResource(R.drawable.ic_baseline_more_horiz_24);
                    break;
                default:
                    imagen_categoria.setImageResource(R.drawable.outcome);
            }

            barra_indicador.setBackgroundColor(R.color.white);


            /*
            if (gastos.getCategoria().equals("Ropa")){
                imagen_categoria.setImageResource(R.drawable.clothes);
            }else if (gastos.getCategoria().equals("Comida")){
                imagen_categoria.setImageResource(R.drawable.restaurant);
            }else if (gastos.getCategoria().equals("Libro")){
                imagen_categoria.setImageResource(R.drawable.books);
            }else if (gastos.getCategoria().equals("Informatica")){
                imagen_categoria.setImageResource(R.drawable.computadora);
            }else if (gastos.getCategoria().equals("Trasporte")){
                imagen_categoria.setImageResource(R.drawable.bus);
            }else if (gastos.getCategoria().equals("Otros")){
                imagen_categoria.setImageResource(R.drawable.ic_baseline_more_horiz_24);
            }*/

        }
    }
}
