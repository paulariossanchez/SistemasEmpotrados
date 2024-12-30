package com.example.wellnest.database;

import android.content.Context;
import android.util.Log;

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
                Log.i(TAG, "appDatabase getInstance en AppDatabase");
                if (instance == null) {
                        Log.i(TAG, "Instance es NULL");
                        instance = Room.databaseBuilder(context.getApplicationContext(),
                                        AppDatabase.class, "wellnest_database")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .addCallback(roomCallback)
                                .build();
                }
                return instance;
        }



        // DAO para Registro Diario
        public abstract RegistroDiarioDao registroDiarioDao();

        // Otros DAOs se pueden agregar aquí
        public abstract EjercicioGuiadoDao ejercicioGuiadoDao();
        private static final String TAG = "ejercicioGuiadoDao";
        // Callback para insertar datos iniciales
        private static final Callback roomCallback = new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                Log.i(TAG, "roomCallback.onCreate before insert");
                        super.onCreate(db);
                        Log.i(TAG, "roomCallback.onCreate - Insertando datos iniciales");

                        // Insertar datos iniciales directamente con SQL
                        db.execSQL("INSERT INTO EjercicioGuiado (nombre, urlVideo, instrucciones) VALUES " +
                                "('Meditación en 5 minutos', 'https://www.youtube.com/watch?v=inpok4MKVLM', NULL)");
                        db.execSQL("INSERT INTO EjercicioGuiado (nombre, urlVideo, instrucciones) VALUES " +
                                "('Relajación antes de dormir', 'https://www.youtube.com/watch?v=_VHO3dEsdj0', NULL)");
                        db.execSQL("INSERT INTO EjercicioGuiado (nombre, urlVideo, instrucciones) VALUES " +
                                "('Ejercicio 5-4-3-2-1', NULL, '5 cosas que puedas ver:\n" +
                                "Mira a tu alrededor y nombra cinco cosas que puedas ver.\n" +
                                "(Ejemplo: una lámpara, un cuadro, una planta...)\n\n" +
                                "4 cosas que puedas tocar:\nNota cuatro texturas o superficies que puedas sentir.\n" +
                                "(Ejemplo: la ropa, la mesa, una silla...)\n\n" +
                                "3 cosas que puedas escuchar:\nIdentifica tres sonidos que puedas oír ahora mismo.\n" +
                                "(Ejemplo: el viento, un reloj, tu respiración...)\n\n" +
                                "2 cosas que puedas oler:\nEncuentra dos olores presentes o piensa en tus olores favoritos.\n" +
                                "(Ejemplo: café, perfume...)\n(Si no hay olores cerca, recuerda uno que te guste).\n\n" +
                                "1 cosa que puedas saborear:\nNota un sabor presente o piensa en algo que te guste.\n" +
                                "(Ejemplo: agua, un chicle...)')");

                }
        };


}
