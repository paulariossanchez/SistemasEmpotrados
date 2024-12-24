package com.example.wellnest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wellnest.database.EjercicioGuiado;

import java.util.List;

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder> {

    private Context context;
    private List<EjercicioGuiado> ejercicios;

    public EjercicioAdapter(Context context, List<EjercicioGuiado> ejercicios) {
        this.context = context;
        this.ejercicios = ejercicios;
    }

    @NonNull
    @Override
    public EjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de las tarjetas
        View view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio, parent, false);
        return new EjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioViewHolder holder, int position) {
        // Configurar cada tarjeta con datos del ejercicio
        EjercicioGuiado ejercicio = ejercicios.get(position);
        holder.nombre.setText(ejercicio.nombre);
        holder.instrucciones.setText(ejercicio.instrucciones != null ? ejercicio.instrucciones : "Sin instrucciones");
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    // Clase ViewHolder para vincular las vistas de las tarjetas
    public static class EjercicioViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, instrucciones;

        public EjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textNombreEjercicio);
            instrucciones = itemView.findViewById(R.id.textInstruccionesEjercicio);
        }
    }
}
