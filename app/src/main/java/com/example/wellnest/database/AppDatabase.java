package com.example.wellnest.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
@Database(entities = {RegistroDiario.class, EjercicioGuiado.class}, version = 1)
@TypeConverters({EmocionConverter.class, DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

        public abstract RegistroDiarioDao registroDiarioDao();
        public abstract EjercicioGuiadoDao ejercicioGuiadoDao();
}
