package com.example.rastreador2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.rastreador2.consts.herramientaConsts;
import com.example.rastreador2.consts.usuarioConsts;
public class conexionSQ extends SQLiteOpenHelper {

    public conexionSQ(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(herramientaConsts.CREATE_TABLE);
        db.execSQL(usuarioConsts.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS " + herramientaConsts.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + usuarioConsts.TABLE_NAME);
        onCreate(db);
    }
}
