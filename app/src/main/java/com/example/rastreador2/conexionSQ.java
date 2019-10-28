package com.example.rastreador2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rastreador2.utilidades.utilidades;

import androidx.annotation.Nullable;

public class conexionSQ extends SQLiteOpenHelper {



    public conexionSQ(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_USUARIO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {

        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);

    }
}
