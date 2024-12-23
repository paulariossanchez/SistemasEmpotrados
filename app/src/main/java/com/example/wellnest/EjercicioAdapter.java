package com.example.wellnest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wellnest.database.EjercicioGuiado;

import java.util.List;

public class EjercicioAdapter extends ArrayAdapter<EjercicioGuiado> {
    private Context context;
    private List<EjercicioGuiado> ejercicios;

    public EjercicioAdapter(Context context, List<EjercicioGuiado> ejercicios) {
        super(context, R.layout.ejercicio_item, ejercicios);
        this.context = context;
        this.ejercicios = ejercicios;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflar el diseño si es necesario
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ejercicio_item, parent, false);
        }

        // Obtener el ejercicio actual
        EjercicioGuiado ejercicio = ejercicios.get(position);

        // Conectar los datos con las vistas
        TextView nombre = convertView.findViewById(R.id.textNombre);
        TextView contenido = convertView.findViewById(R.id.textContenido);

        nombre.setText(ejercicio.nombre);
        if (ejercicio.urlVideo != null && !ejercicio.urlVideo.isEmpty()) {
            contenido.setText("Ver Video");
        } else if (ejercicio.instrucciones != null && !ejercicio.instrucciones.isEmpty()) {
            contenido.setText(ejercicio.instrucciones);
        } else {
            contenido.setText("Sin contenido disponible");
        }

        return convertView;
    }
}
