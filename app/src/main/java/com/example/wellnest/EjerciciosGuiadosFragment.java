package com.example.wellnest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wellnest.EjerciciosAdapter;

import com.example.wellnest.database.AppDatabase;
import com.example.wellnest.database.EjercicioGuiado;

import java.util.List;
import java.util.logging.Logger;

public class EjerciciosGuiadosFragment extends Fragment {
    private static final String TAG = "EjerciciosGuiadosFragment";
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejercicios_guiados, container, false);
        Log.i(TAG, "After Inlfate View");
        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewEjercicios);
        Log.i(TAG, recyclerView.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Obtener los ejercicios desde la base de datos
        Log.i(TAG, " AppDatabase.getInstance");
        AppDatabase db = AppDatabase.getInstance(requireContext());
        EjercicioGuiado ejercicio1 = new EjercicioGuiado("Meditación en 5 minutos", "https://www.youtube.com/watch?v=inpok4MKVLM", null);
        db.ejercicioGuiadoDao().insertEjercicio(ejercicio1);

        List<EjercicioGuiado> ejercicios = db.ejercicioGuiadoDao().getAllEjercicios();
        Log.i(TAG, "Ejercicios: " + ejercicios.size());

        // Configurar el adaptador con las tarjetas
        EjerciciosAdapter adapter = new EjerciciosAdapter(requireContext(), ejercicios);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
