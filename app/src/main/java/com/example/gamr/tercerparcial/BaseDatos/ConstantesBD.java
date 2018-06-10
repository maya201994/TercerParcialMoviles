package com.example.gamr.tercerparcial.BaseDatos;

public class ConstantesBD {

    //BASE DE DATOS
    public static String NOMBRE_BD = "bd_estacion";

    //TABLA ESTACION
    public static String TABLA_DISPOSITIVO = "dispositivo";
    public static String CAMPO_ID_DISPOSITIVO = "iddispositivo";
    public static String CAMPO_NOMBRE = "nombre";
    public static String CAMPO_LATITUD = "latitud";
    public static String CAMPO_LONGITUD = "longitud";
    public static String CAMPO_DESCRIPCION = "descripcion";
    public static String CAMPO_RANGO_MIN = "ran_minimo";
    public static String CAMPO_RANGO_MAX = "ran_max";

    public static final  String  CREAR_TABLA_DISPOSITIVO="CREATE TABLE "+TABLA_DISPOSITIVO+" ("+CAMPO_ID_DISPOSITIVO+
            " INTEGER PRIMARY KEY AUTOINCREMENT,"+CAMPO_NOMBRE+" TEXT,"+CAMPO_LATITUD+" INTEGER,"+
            " "+CAMPO_LONGITUD+" INTEGER,"+CAMPO_DESCRIPCION+" TEXT, "+CAMPO_RANGO_MIN+" INTEGER," +
            " "+CAMPO_RANGO_MAX+" INTEGER)";

    public static final String ELIMINAR_TABLA_DISPOSITIVO = "DROP TABLE IF EXISTS "+TABLA_DISPOSITIVO;
}
