package com.example.rastreador2.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rastreador2.conexionSQ;
import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.consts.usuarioConsts;

import java.util.ArrayList;

public class usuarioRepo {

    conexionSQ conn;

    public usuarioRepo(Context context) {
        conn = new conexionSQ(context, "rastreadordb", null, 5);
    };


    public ArrayList<Usuario> getAll() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = db.rawQuery(usuarioConsts.FIND, null);
        while(cursor.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setPhone_number(cursor.getString(1));
            usuario.setNombre(cursor.getString(2));
            usuario.setActivo(cursor.getInt(3));
            usuarios.add(usuario);
        }
        conn.close();
        return usuarios;
    }

    public Usuario getOne(String id) {
        SQLiteDatabase db = conn.getReadableDatabase();
        Usuario usuario = new Usuario();
        String [] fields = {
                usuarioConsts.ID_COLUMN_NAME,
                usuarioConsts.PHONE_COLUMN_NAME,
                usuarioConsts.NAME_COLUMN_NAME,
                usuarioConsts.ACTIVE_COLUMN_NAME,
        };
        String [] parameters = { id };
        try {
            Cursor cursor = db.query(
                    usuarioConsts.TABLE_NAME,
                    fields,
                    usuarioConsts.ID_COLUMN_NAME + " = ?",
                    parameters,
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();
            usuario.setId(cursor.getInt(0));
            usuario.setPhone_number(cursor.getString(1));
            usuario.setNombre(cursor.getString(2));
            usuario.setActivo(cursor.getInt(3));
            cursor.close();
            conn.close();
            return usuario;
        } catch(Exception e) {
            conn.close();
            usuario = null;
            return usuario;
        }

    }

    public long create(String phone_number, String name) {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(usuarioConsts.PHONE_COLUMN_NAME, phone_number);
        values.put(usuarioConsts.NAME_COLUMN_NAME, name);
        long inserted_id = db.insert(
                usuarioConsts.TABLE_NAME,
                null,
                values
        );
        db.close();
        return inserted_id;
    }

    public int update(String id, String phone_number, String name) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parameters = { id };
        ContentValues values = new ContentValues();
        values.put(usuarioConsts.PHONE_COLUMN_NAME, phone_number);
        values.put(usuarioConsts.NAME_COLUMN_NAME, name);
        int affected = db.update(
                usuarioConsts.TABLE_NAME,
                values,
                usuarioConsts.ID_COLUMN_NAME + " = ?",
                parameters
        );
        db.close();
        return affected;
    }

    public int delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parameters = { id };
        int deleted = db.delete(
                usuarioConsts.TABLE_NAME,
                usuarioConsts.ID_COLUMN_NAME + " = ?",
                parameters
        );
        db.close();
        return deleted;
    }
}
