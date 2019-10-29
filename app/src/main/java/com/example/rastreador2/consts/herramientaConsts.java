package com.example.rastreador2.consts;

public class herramientaConsts {

    public static final String TABLE_NAME = "herramienta";
    public static final String ID_COLUMN_NAME = "id";
    public static final String PHONE_COLUMN_NAME = "phone_number";
    public static final String NAME_COLUMN_NAME = "nombre";
    public static final String SERIE_COLUMN_NAME = "serie";

    public static final String CREATE_TABLE = String.format(
            "CREATE TABLE %s(" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s CHAR(15) NOT NULL ," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "UNIQUE(%s)" +
                    ")",
            TABLE_NAME,
            ID_COLUMN_NAME,
            PHONE_COLUMN_NAME,
            NAME_COLUMN_NAME,
            SERIE_COLUMN_NAME,
            PHONE_COLUMN_NAME
    );

    public static String FIND = String.format(
            "SELECT %s, %s, %s, %s" +
                    " FROM %s",
            ID_COLUMN_NAME,
            PHONE_COLUMN_NAME,
            NAME_COLUMN_NAME,
            SERIE_COLUMN_NAME,
            TABLE_NAME
            );


}
