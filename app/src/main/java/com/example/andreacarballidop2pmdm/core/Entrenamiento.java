package com.example.andreacarballidop2pmdm.core;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

@Entity(tableName = "entrenamientos")
public class Entrenamiento {
    @PrimaryKey
    @NonNull
    public String idEntrenamiento;
    public Date fecha;
    public int horas;
    public int minutos;
    public int segundos;
    public int metros;
    public float minutosKm;
    public float velocidadmediakmporhora;

    public Entrenamiento(Date fecha, int horas, int minutos, int segundos, int metros, float minutosKm, float velocidadmediakmporhora) {
        this.fecha = fecha;
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
        this.metros = metros;
        this.minutosKm = minutosKm;
        this.velocidadmediakmporhora = velocidadmediakmporhora;
    }

    public Entrenamiento() {
        idEntrenamiento = UUID.randomUUID().toString();
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public int getMetros() {
        return metros;
    }

    public void setMetros(int metros) {
        this.metros = metros;
    }

    public float getMinutosKm() {
        return minutosKm;
    }

    public void setMinutosKm(float minutosKm) {
        this.minutosKm = minutosKm;
    }

    public float getVelocidadmediakmporhora() {
        return velocidadmediakmporhora;
    }

    public void setVelocidadmediakmporhora(float velocidadmediakmporhora) {
        this.velocidadmediakmporhora = velocidadmediakmporhora;
    }

    @Override
    public String toString() {
        return  "\n\n  Fecha: " + getFormatoFecha() +
                "\n\n  Tiempo:  " + getTiempo() +
                "\n\n  Distancia:  " + getm() +
                "\n\n  Minutos y segundos por km:  " + minutosKm + " min/km" +
                "\n\n  Velocidad media:  " + velocidadmediakmporhora +" kms/h";
    }

    public String getFormatoFecha() {
        Locale espanol = new Locale("es","ES");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
        return formatoFecha.format(fecha);

    }

    public String getTiempo() {
        String tiempo= horas + "h " + minutos + " min " + segundos + " s";
        return tiempo;
    }

    public String getm() {
        String tiempo= metros + " m";
        return tiempo;
    }

    public void modificarEntrenamiento(Date fecha, int horas, int minutos, int segundos, int metros, float minutosKm, float velocidadmediakmporhora) {

        this.fecha = fecha;
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
        this.metros = metros;
        this.minutosKm = minutosKm;
        this.velocidadmediakmporhora = velocidadmediakmporhora;
    }

    public float calcularMinKm(int horas,float minutos,float segundos,float metros){

        float km = metros / 1000;
        float horasenminutos = horas * 60;
        float segundosenminutos = segundos / 60;
        float tiempo = horasenminutos + minutos + segundosenminutos;
        float resultadominutosporkm = tiempo/km;

        resultadominutosporkm = (float) (Math.round(resultadominutosporkm * 100) / 100d);
        return resultadominutosporkm;

    }
    public float calcularVelocidadmedia(float horas,float minutos,float segundos,float metros){
        //Pasamos los minutos y los segundos a horas y calculamos la velocidad media
        float km = metros / 1000;
        float minutoahora = minutos / 60;
        float segundoahora = segundos / 3600;
        float tiempoenhoras = horas + minutoahora + segundoahora;
        float velocidad =km / tiempoenhoras;

        velocidad = (float) (Math.round(velocidad * 100) / 100d);
        return velocidad;
    }

    public String eliminarEntrenamiento(){
        String texto= "Fecha entrenamiento: " + getFormatoFecha()+"\n"+ "Tiempo: "+ getTiempo()+"\n"+ "Distancia: "+getm();
        return texto;
    }



}
