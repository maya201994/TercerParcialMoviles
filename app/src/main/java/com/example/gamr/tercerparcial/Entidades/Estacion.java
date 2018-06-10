package com.example.gamr.tercerparcial.Entidades;

public class Estacion {

    private String Nombre;
    private String Descripcion;
    private float Latitud;
    private float Longitud;
    private float Rangomin;
    private float Rangomax;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public float getLatitud() {
        return Latitud;
    }

    public void setLatitud(float latitud) {
        Latitud = latitud;
    }

    public float getLongitud() {
        return Longitud;
    }

    public void setLongitud(float longitud) {
        Longitud = longitud;
    }

    public float getRangomin() {
        return Rangomin;
    }

    public void setRangomin(float rangomin) {
        Rangomin = rangomin;
    }

    public float getRangomax() {
        return Rangomax;
    }

    public void setRangomax(float rangomax) {
        Rangomax = rangomax;
    }
}
