package com.example.wellnest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wellnest.database.AppDatabase;
import com.example.wellnest.database.Emocion;
import com.example.wellnest.database.RegistroDiario;

import java.util.ArrayList;
import java.util.List;

public class RegistroDiarioFragment extends Fragment {

    private static final String TAG = "RegistroDiarioFragment";

    private Spinner emocionSpinner;
    private EditText gratitud1, gratitud2;
    private Button guardarButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_registro_diario, container, false);
        Log.i(TAG, "After Inlfate View");

        // Referenciar los elementos del layout
        emocionSpinner = view.findViewById(R.id.emocion_spinner);
        gratitud1 = view.findViewById(R.id.gratitud1);
        gratitud2 = view.findViewById(R.id.gratitud2);
        guardarButton = view.findViewById(R.id.guardar_button);
        // Configurar el Spinner con emociones
        configurarSpinner();
        // Configurar el botón de guardar
        guardarButton.setOnClickListener(v -> guardarRegistro());
        return view;
    }

    private void configurarSpinner() {

        // Convertir el enum Emocion a una lista de strings
        List<String> emocionesList = new ArrayList<>();
        for (Emocion emocion : Emocion.values()) {
            emocionesList.add(emocion.name());
        }

        // Crear y asignar el adaptador al Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, emocionesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emocionSpinner.setAdapter(adapter);
    }


    private void guardarRegistro() {
        // Obtener los datos ingresados por el usuario
        String emocionSeleccionada = emocionSpinner.getSelectedItem().toString();
        String gratitudTexto1 = gratitud1.getText().toString();
        String gratitudTexto2 = gratitud2.getText().toString();

        // Validar que los campos no estén vacíos
        if (gratitudTexto1.isEmpty() || gratitudTexto2.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objeto RegistroDiario
        Emocion emocion = Emocion.valueOf(emocionSeleccionada); // Convertir el texto al enum
        RegistroDiario registro = new RegistroDiario(System.currentTimeMillis(), emocion, gratitudTexto1, gratitudTexto2);

        // Guardar en la base de datos
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getContext());
            db.registroDiarioDao().insertRegistro(registro);

            // Mostrar un mensaje de éxito en el hilo principal
            requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Registro guardado exitosamente", Toast.LENGTH_SHORT).show());
        }).start();
    }

}
