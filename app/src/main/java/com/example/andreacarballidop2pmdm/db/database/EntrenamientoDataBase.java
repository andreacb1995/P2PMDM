package com.example.andreacarballidop2pmdm.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.andreacarballidop2pmdm.core.DateConverter;
import com.example.andreacarballidop2pmdm.core.Entrenamiento;

@Database(entities = {Entrenamiento.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class EntrenamientoDataBase extends RoomDatabase {
    public abstract EntrenamientoDao getEntrenamientoDao();
}
