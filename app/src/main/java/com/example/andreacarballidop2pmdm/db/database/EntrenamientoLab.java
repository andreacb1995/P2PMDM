package com.example.andreacarballidop2pmdm.db.database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import com.example.andreacarballidop2pmdm.core.Entrenamiento;

import java.util.ArrayList;

public class EntrenamientoLab {
    @SuppressLint("StaticFieldLeak")
    private static EntrenamientoLab sEntrenamientoLab;

    private static EntrenamientoDao mEntrenamientoDao;

    private EntrenamientoLab(Context context) {
        Context appContext = context.getApplicationContext();
        EntrenamientoDataBase database = Room.databaseBuilder(appContext, EntrenamientoDataBase.class, "entrenamiento")
                .allowMainThreadQueries().build();
        mEntrenamientoDao = database.getEntrenamientoDao();
    }

    public static EntrenamientoLab get(Context context) {
        if (sEntrenamientoLab == null) {
            sEntrenamientoLab = new EntrenamientoLab(context);
        }
        return sEntrenamientoLab;
    }

    public static ArrayList<Entrenamiento> getEntrenamientos() {
        return (ArrayList<Entrenamiento>) mEntrenamientoDao.getEntrenamientos();
    }

    public Entrenamiento getEntrenamiento(String id) {
        return mEntrenamientoDao.getEntrenamiento(id);
    }

    public void addEntrenamiento(Entrenamiento entrenamiento) {
        mEntrenamientoDao.addEntrenamiento(entrenamiento);
    }

    public void updateEntrenamiento(Entrenamiento entrenamiento) {
        mEntrenamientoDao.updateEntrenamiento(entrenamiento);
    }

    public static void deleteEntrenamiento(Entrenamiento entrenamiento) {
        mEntrenamientoDao.deleteEntrenamiento(entrenamiento);
    }
}