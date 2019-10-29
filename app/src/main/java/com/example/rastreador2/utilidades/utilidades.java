package com.example.rastreador2.utilidades;

public class utilidades {

    public static final String TABLA_USUARIOS="usuarios";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_PHONE=" phone_number";
    public static final String CAMPO_NOMBRE=" nombre";
    public static final String CAMPO_SERIE=" serie";


    public static final String CREAR_TABLA_USUARIO = String.format(
            "CREATE TABLE %s(" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s CHAR(15) NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL" +
                    ")"
    , TABLA_USUARIOS, CAMPO_ID, CAMPO_PHONE, CAMPO_NOMBRE, CAMPO_SERIE);

}
