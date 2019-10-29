package com.example.rastreador2.utilidades;

public class utilidades {

    public static final String TABLA_USUARIOS=" usuarios";
    public static final String CAMPO_ID=" ID";
    public static final String CAMPO_NOMBRE=" nombre";
    public static final String CAMPO_SERIE=" serie";

    public static final String CREAR_TABLA_USUARIO=" CREATE TABLE "+
            TABLA_USUARIOS+" ("+CAMPO_ID+ " " +
            " INTEGER, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_SERIE+" TEXT ) ";

}
