package com.example.wellnest.database;

import androidx.room.TypeConverter;

public class EmocionConverter {
    @TypeConverter
    public static String fromEmocion(Emocion emocion) {
        return emocion == null ? null : emocion.name();
    }

    @TypeConverter
    public static Emocion toEmocion(String emocion) {
        return emocion == null ? null : Emocion.valueOf(emocion);
    }
}
