package com.example.wellnest.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {RegistroDiario.class, EjercicioGuiado.class}, version = 1)
@TypeConverters({EmocionConverter.class, DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
        private static AppDatabase instance; // Declaración de la instancia estática


        public static synchronized AppDatabase getInstance(Context context) {
                if (instance == null) {
                        instance = Room.databaseBuilder(context.getApplicationContext(),
                                        AppDatabase.class, "wellnest_database")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries() // Solo para pruebas; evita en producción
                                .addCallback(new RoomDatabase.Callback() {
                                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                                super.onCreate(db);
                                                Executors.newSingleThreadExecutor().execute(() -> {
                                                        EjercicioGuiadoDao dao = instance.ejercicioGuiadoDao();
                                                        dao.insertEjercicio(new EjercicioGuiado("Ejercicio 1", null, "Instrucciones del ejercicio 1"));
                                                        dao.insertEjercicio(new EjercicioGuiado("Ejercicio 2", null, "Instrucciones del ejercicio 2"));
                                                });
                                        }
                                })
                                .build();
                }
                return instance;
        }



        // DAO para Registro Diario
        public abstract RegistroDiarioDao registroDiarioDao();

        // Otros DAOs se pueden agregar aquí
        public abstract EjercicioGuiadoDao ejercicioGuiadoDao();


}
