package com.example.rastreador2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.rastreador2.consts.herramientaConsts;

public class conexionSQ extends SQLiteOpenHelper {

    public conexionSQ(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("fddf", "On create");
        db.execSQL(herramientaConsts.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        Log.d("fddf", "On upgrade");
        db.execSQL("DROP TABLE IF EXISTS " + herramientaConsts.TABLE_NAME);
        onCreate(db);

    }
}
