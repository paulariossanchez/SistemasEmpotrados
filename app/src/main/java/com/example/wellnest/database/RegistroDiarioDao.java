package com.example.wellnest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RegistroDiarioDao {
    @Insert
    void insertRegistro(RegistroDiario registro);

    @Query("SELECT * FROM RegistroDiario WHERE fecha = :fecha")
    List<RegistroDiario> getRegistrosByFecha(String fecha);
}
