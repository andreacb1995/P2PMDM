package com.example.andreacarballidop2pmdm.db.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.andreacarballidop2pmdm.core.Entrenamiento;

import java.util.ArrayList;
import java.util.List;

@Dao
interface EntrenamientoDao {

    @Query("SELECT * FROM entrenamientos")
    List<Entrenamiento> getEntrenamientos();

    @Query("SELECT * FROM entrenamientos WHERE idEntrenamiento LIKE :uuid")
    Entrenamiento getEntrenamiento(String uuid);

    @Insert
    void addEntrenamiento(Entrenamiento book);

    @Delete
    void deleteEntrenamiento(Entrenamiento book);

    @Update
    void updateEntrenamiento(Entrenamiento book);

}
