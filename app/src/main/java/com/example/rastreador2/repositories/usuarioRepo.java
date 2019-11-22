package com.example.rastreador2.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import com.example.rastreador2.conexionSQ;
import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.consts.usuarioConsts;


public class usuarioRepo {

    conexionSQ conn;

    public usuarioRepo(Context context) {
        conn = new conexionSQ(context, "rastreadordb", null, 12);
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
            if(!cursor.isNull(4)) {
                usuario.setImage_path(cursor.getString(4));
            }
            usuarios.add(usuario);
        }
        cursor.close();
        conn.close();
        return usuarios;
    }

    public Usuario getOne(int id) {
        SQLiteDatabase db = conn.getReadableDatabase();
        Usuario usuario = new Usuario();
        String [] fields = {
                usuarioConsts.ID_COLUMN_NAME,
                usuarioConsts.PHONE_COLUMN_NAME,
                usuarioConsts.NAME_COLUMN_NAME,
                usuarioConsts.ACTIVE_COLUMN_NAME,
                usuarioConsts.IMAGE_COLUMN_NAME
        };
        String [] parameters = { String.valueOf(id) };

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
            if(!cursor.isNull(4)) {
                usuario.setImage_path(cursor.getString(4));
            }
            cursor.close();
            conn.close();
            return usuario;
        } catch(Exception e) {
            conn.close();
            usuario = null;
            return usuario;
        }

    }

    public Usuario getOneByName(String name) {
        SQLiteDatabase db = conn.getReadableDatabase();
        Usuario usuario = new Usuario();
        String[] fields = {
                usuarioConsts.ID_COLUMN_NAME,
                usuarioConsts.PHONE_COLUMN_NAME,
                usuarioConsts.NAME_COLUMN_NAME,
                usuarioConsts.ACTIVE_COLUMN_NAME,
                usuarioConsts.IMAGE_COLUMN_NAME
        };
        String[] parameters = {name};

        try {
            Cursor cursor = db.query(
                    usuarioConsts.TABLE_NAME,
                    fields,
                    usuarioConsts.NAME_COLUMN_NAME + " = ?",
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
            if (!cursor.isNull(4)) {
                usuario.setImage_path(cursor.getString(4));
            }
            cursor.close();
            conn.close();
            return usuario;
        } catch (Exception e) {
            conn.close();
            usuario = null;
            return usuario;
        }
    }

    public ArrayList<Usuario> getActive() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String [] fields = {
                usuarioConsts.ID_COLUMN_NAME,
                usuarioConsts.PHONE_COLUMN_NAME,
                usuarioConsts.NAME_COLUMN_NAME,
                usuarioConsts.ACTIVE_COLUMN_NAME,
                usuarioConsts.IMAGE_COLUMN_NAME
        };

        try {
            Cursor cursor = db.query(
                    usuarioConsts.TABLE_NAME,
                    fields,
                    usuarioConsts.ACTIVE_COLUMN_NAME + " = 1",
                    null,
                    null,
                    null,
                    null
            );
            while(cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setPhone_number(cursor.getString(1));
                usuario.setNombre(cursor.getString(2));
                usuario.setActivo(cursor.getInt(3));
                if(!cursor.isNull(4)) {
                    usuario.setImage_path(cursor.getString(4));
                }
                usuarios.add(usuario);
            }
            cursor.close();
            conn.close();
            return usuarios;
        } catch(Exception e) {
            conn.close();
            return usuarios;
        }
    }

    public ArrayList<Usuario> getNonActive() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String [] fields = {
                usuarioConsts.ID_COLUMN_NAME,
                usuarioConsts.PHONE_COLUMN_NAME,
                usuarioConsts.NAME_COLUMN_NAME,
                usuarioConsts.ACTIVE_COLUMN_NAME,
                usuarioConsts.IMAGE_COLUMN_NAME
        };

        try {
            Cursor cursor = db.query(
                    usuarioConsts.TABLE_NAME,
                    fields,
                    usuarioConsts.ACTIVE_COLUMN_NAME + " = 0",
                    null,
                    null,
                    null,
                    null
            );
            while(cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setPhone_number(cursor.getString(1));
                usuario.setNombre(cursor.getString(2));
                usuario.setActivo(cursor.getInt(3));
                if(!cursor.isNull(4)) {
                    usuario.setImage_path(cursor.getString(4));
                }
                usuarios.add(usuario);
            }
            cursor.close();
            conn.close();
            return usuarios;
        } catch(Exception e) {
            conn.close();
            return usuarios;
        }
    }

    public long create(String phone_number, String name, String image_path) {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(usuarioConsts.PHONE_COLUMN_NAME, phone_number);
        values.put(usuarioConsts.NAME_COLUMN_NAME, name);
        values.put(usuarioConsts.IMAGE_COLUMN_NAME, image_path);
        long inserted_id = db.insert(
                usuarioConsts.TABLE_NAME,
                null,
                values
        );
        db.close();
        return inserted_id;
    }

    public int updateActive(Integer userId) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parameters = { String.valueOf(userId) };
        ContentValues values = new ContentValues();
        values.put(usuarioConsts.ACTIVE_COLUMN_NAME, 1);
        try {
            int affected = db.update(
                    usuarioConsts.TABLE_NAME,
                    values,
                    usuarioConsts.ID_COLUMN_NAME + " = ?",
                    parameters
            );
            db.close();
            return affected;
        } catch (Exception e) {
            db.close();
            throw e;
        }
    }

    public int disactiveUser(Integer userId) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parameters = { userId.toString() };
        ContentValues values = new ContentValues();
        values.put(usuarioConsts.ACTIVE_COLUMN_NAME, 0);

        int affected = db.update(
                usuarioConsts.TABLE_NAME,
                values,
                usuarioConsts.ID_COLUMN_NAME + " = ?",
                parameters
        );
        db.close();
        return affected;
    }

    public int update(Integer id, String phone_number, String name, String image_path) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parameters = { id.toString() };
        ContentValues values = new ContentValues();
        values.put(usuarioConsts.PHONE_COLUMN_NAME, phone_number);
        values.put(usuarioConsts.NAME_COLUMN_NAME, name);
        values.put(usuarioConsts.IMAGE_COLUMN_NAME, image_path);
        int affected = db.update(
                usuarioConsts.TABLE_NAME,
                values,
                usuarioConsts.ID_COLUMN_NAME + " = ?",
                parameters
        );
        db.close();
        return affected;
    }

    public int delete(Integer id) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parameters = { id.toString() };
        int deleted = db.delete(
                usuarioConsts.TABLE_NAME,
                usuarioConsts.ID_COLUMN_NAME + " = ?",
                parameters
        );
        db.close();
        return deleted;
    }
}
