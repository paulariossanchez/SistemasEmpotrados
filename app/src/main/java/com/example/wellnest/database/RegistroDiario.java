package com.example.wellnest.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity
public class RegistroDiario {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String respuestaPregunta1;
    public String respuestaPregunta2;

    @TypeConverters(EmocionConverter.class)
    public Emocion emocion;

    public Date fecha;

    public RegistroDiario(long l, Emocion emocion, String respuestaPregunta1, String respuestaPregunta2) {
        this.emocion = emocion;
        this.respuestaPregunta1 = respuestaPregunta1;
        this.respuestaPregunta2 = respuestaPregunta2;
        this.fecha = new Date(l);
    }

    public RegistroDiario() {

    }
}
