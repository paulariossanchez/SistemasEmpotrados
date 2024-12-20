package com.example.wellnest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EjercicioGuiadoDao {
    @Insert
    void insertEjercicio(EjercicioGuiado ejercicio);

    @Query("SELECT * FROM EjercicioGuiado")
    List<EjercicioGuiado> getAllEjercicios();
}
