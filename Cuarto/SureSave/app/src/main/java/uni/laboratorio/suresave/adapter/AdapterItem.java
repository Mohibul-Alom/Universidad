package uni.laboratorio.suresave.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uni.laboratorio.suresave.R;
import uni.laboratorio.suresave.modelos.Gastos;
import uni.laboratorio.suresave.modelos.Ingresos;
import uni.laboratorio.suresave.modelos.Movimiento;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ViewHolder>{

    private Context context;
    private List<Movimiento> lista_movimiento;

    public AdapterItem(Context context, List<Movimiento> lista_movimiento) {
        this.context = context;
        this.lista_movimiento = lista_movimiento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);

        return new AdapterItem.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movimiento movimiento = lista_movimiento.get(position);
        String tempFecha = convertirFecha(movimiento.getFecha());
        holder.fecha.setText(tempFecha);

        holder.categoria.setText(movimiento.getCategoria());

        double temp = movimiento.getCantidad();
        String gastoTemp = Double.toString(temp);
        holder.cantidad.setText(gastoTemp);

        switch (movimiento.getCategoria()){
            case "Ropa":
                holder.imagen_categoria.setImageResource(R.drawable.clothes);
                break;
            case "Comida":
                holder.imagen_categoria.setImageResource(R.drawable.restaurant);
                break;
            case "Libro":
                holder.imagen_categoria.setImageResource(R.drawable.books);
                break;
            case "Informatica":
                holder.imagen_categoria.setImageResource(R.drawable.computadora);
                break;
            case "Trasporte":
                holder.imagen_categoria.setImageResource(R.drawable.bus);
                break;
            case "Otros":
                holder.imagen_categoria.setImageResource(R.drawable.ic_baseline_more_horiz_24);
                break;
            case "Bizum":
                holder.imagen_categoria.setImageResource(R.drawable.bizum);
                break;
            case "Transferencia":
                holder.imagen_categoria.setImageResource(R.drawable.transfer);
                break;
            case "Nómina":
                holder.imagen_categoria.setImageResource(R.drawable.nomina);
                break;
            default:
                holder.imagen_categoria.setImageResource(R.drawable.outcome);
                break;
        }

        if (movimiento.isTipo()){

            holder.barra_indicador.setBackgroundColor(R.drawable.rectangulo_rojo);
            System.out.println("Color que tendria que ser en true: rojo********************************************");
        }else {
            holder.barra_indicador.setBackgroundColor(R.drawable.rectangulo_verde);
            System.out.println("Color que tendria que ser en true: verde********************************************");
        }

    }

    @Override
    public int getItemCount() {
        return lista_movimiento.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fecha,categoria,cantidad;
        public ImageView imagen_categoria;
        public LinearLayout barra_indicador;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fecha = itemView.findViewById(R.id.fecha);
            categoria = itemView.findViewById(R.id.categoria);
            cantidad = itemView.findViewById(R.id.cantidad);
            imagen_categoria = itemView.findViewById(R.id.imagen_categorias);
            barra_indicador = itemView.findViewById(R.id.barra_indicador);

        }
    }

    private String convertirFecha(long fecha) {

        Date date = new Date(fecha);
        @SuppressLint("SimpleDateFormat")
        Format format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);

    }


}
