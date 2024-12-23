package com.example.wellnest.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EjercicioGuiado {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String urlVideo; // Puede ser null si no hay video
    public String instrucciones; // Puede ser null si no hay instrucciones


    public EjercicioGuiado(String nombre, String urlVideo, String instrucciones) {

        this.nombre = nombre;
        this.urlVideo = urlVideo;
        this.instrucciones = instrucciones;
    }


    public EjercicioGuiado() {

    }
}
