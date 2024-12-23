package com.example.wellnest;

import android.os.Bundle;
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

public class EjerciciosGuiadosFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejercicios_guiados, container, false);

        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewEjercicios);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Obtener los ejercicios desde la base de datos
        AppDatabase db = AppDatabase.getInstance(requireContext());
        List<EjercicioGuiado> ejercicios = db.ejercicioGuiadoDao().getAllEjercicios();

        // Configurar el adaptador
        EjerciciosAdapter adapter = new EjerciciosAdapter(requireContext(), ejercicios);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
