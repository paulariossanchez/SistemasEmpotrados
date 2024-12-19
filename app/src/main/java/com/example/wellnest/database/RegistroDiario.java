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
}
