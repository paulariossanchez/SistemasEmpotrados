package com.example.wellnest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RegistroDiarioDao {

    @Insert
    void insertRegistro(RegistroDiario registro);

    @Query("SELECT * FROM RegistroDiario WHERE DATE(fecha / 1000, 'unixepoch') = DATE('now')")
    RegistroDiario getRegistroDelDia();

    @Query("SELECT * FROM RegistroDiario")
    List<RegistroDiario> getTodosLosRegistros(); // Corregido el nombre del método


}
