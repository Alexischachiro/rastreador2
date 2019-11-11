package com.example.rastreador2.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.rastreador2.entidades.Herramienta;
import com.example.rastreador2.conexionSQ;
import com.example.rastreador2.consts.herramientaConsts;

import java.util.ArrayList;

public class herramientaRepo {

    conexionSQ conn;

    public herramientaRepo(Context context) {
        conn = new conexionSQ(context, "rastreadordb", null, 5);
    };


    public ArrayList<Herramienta> getAll() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Herramienta> herramientas = new ArrayList<>();
        Cursor cursor = db.rawQuery(herramientaConsts.FIND, null);
        while(cursor.moveToNext()) {
            Herramienta herramienta = new Herramienta();
            herramienta.setId(cursor.getInt(0));
            herramienta.setPhone_number(cursor.getString(1));
            herramienta.setNombre(cursor.getString(2));
            herramienta.setSerie(cursor.getString(3));
            herramienta.setActive(cursor.getInt(4));
            herramienta.setUserId(cursor.getInt(5));
            herramientas.add(herramienta);
        }
        conn.close();
        return herramientas;
    }

    public ArrayList<Herramienta> getActive() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Herramienta> herramientas = new ArrayList<>();
        String[] fields = {
                herramientaConsts.ID_COLUMN_NAME,
                herramientaConsts.NAME_COLUMN_NAME,
                herramientaConsts.PHONE_COLUMN_NAME,
                herramientaConsts.SERIE_COLUMN_NAME,
                herramientaConsts.ACTIVE_COLUMN_NAME,
                herramientaConsts.USERID_COLUMN_NAME
        };
        try {
            Cursor cursor = db.query(
                    herramientaConsts.TABLE_NAME,
                    fields,
                    herramientaConsts.ACTIVE_COLUMN_NAME + " = 1",
                    null,
                    null,
                    null,
                    null
            );
            while(cursor.moveToNext()) {
                Herramienta herramienta = new Herramienta();
                herramienta.setId(cursor.getInt(0));
                herramienta.setNombre(cursor.getString(1));
                herramienta.setPhone_number(cursor.getString(2));
                herramienta.setSerie(cursor.getString(3));
                herramienta.setActive(cursor.getInt(4));
                if(!cursor.isNull(5)) {
                    herramienta.setUserId(cursor.getInt(5));
                }
                herramientas.add(herramienta);
            }
            cursor.close();
            conn.close();
            return herramientas;
        } catch (Exception e) {
            conn.close();
            return herramientas;
        }
    }

    public ArrayList<Herramienta> getNonActive() {
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<Herramienta> herramientas = new ArrayList<>();
        String[] fields = {
                herramientaConsts.ID_COLUMN_NAME,
                herramientaConsts.NAME_COLUMN_NAME,
                herramientaConsts.PHONE_COLUMN_NAME,
                herramientaConsts.SERIE_COLUMN_NAME,
                herramientaConsts.ACTIVE_COLUMN_NAME,
                herramientaConsts.USERID_COLUMN_NAME
        };
        try {
            Cursor cursor = db.query(
                    herramientaConsts.TABLE_NAME,
                    fields,
                    herramientaConsts.ACTIVE_COLUMN_NAME + " = 0",
                    null,
                    null,
                    null,
                    null
            );
            while(cursor.moveToNext()) {
                Herramienta herramienta = new Herramienta();
                herramienta.setId(cursor.getInt(0));
                herramienta.setNombre(cursor.getString(1));
                herramienta.setPhone_number(cursor.getString(2));
                herramienta.setSerie(cursor.getString(3));
                herramienta.setActive(cursor.getInt(4));
                if(!cursor.isNull(5)) {
                    herramienta.setUserId(cursor.getInt(5));
                }
                herramientas.add(herramienta);
            }
            cursor.close();
            conn.close();
            return herramientas;
        } catch (Exception e) {
            conn.close();
            return herramientas;
        }
    }

    public Herramienta getOne(String phone_number) {
        SQLiteDatabase db = conn.getReadableDatabase();
        Herramienta herramienta = new Herramienta();
        String [] fields = {
                herramientaConsts.ID_COLUMN_NAME,
                herramientaConsts.NAME_COLUMN_NAME,
                herramientaConsts.SERIE_COLUMN_NAME,
                herramientaConsts.ACTIVE_COLUMN_NAME,
                herramientaConsts.USERID_COLUMN_NAME
        };
        String [] parameters = { phone_number };
        try {
            Cursor cursor = db.query(
                    herramientaConsts.TABLE_NAME,
                    fields,
                    herramientaConsts.PHONE_COLUMN_NAME + " = ?",
                    parameters,
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();
            herramienta.setId(cursor.getInt(0));
            herramienta.setPhone_number(phone_number);
            herramienta.setNombre(cursor.getString(1));
            herramienta.setSerie(cursor.getString(2));
            herramienta.setActive(cursor.getInt(3));
            herramienta.setUserId(cursor.getInt(4));
            cursor.close();
            conn.close();
            return herramienta;
        } catch(Exception e) {
            conn.close();
            herramienta = null;
            return herramienta;
        }

    }

    public long create(String phone_number, String name, String serie) {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(herramientaConsts.PHONE_COLUMN_NAME, phone_number);
        values.put(herramientaConsts.NAME_COLUMN_NAME, name);
        values.put(herramientaConsts.SERIE_COLUMN_NAME, serie);
        long inserted_id = db.insert(
                herramientaConsts.TABLE_NAME,
                null,
                values
        );
        db.close();
        return inserted_id;
    }

    public int update(String phone_number, String name, String serie) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parameters = { phone_number };
        ContentValues values = new ContentValues();
        values.put(herramientaConsts.NAME_COLUMN_NAME, name);
        values.put(herramientaConsts.SERIE_COLUMN_NAME, serie);
        int affected = db.update(
                herramientaConsts.TABLE_NAME,
                values,
                herramientaConsts.PHONE_COLUMN_NAME + " = ?",
                parameters
        );
        db.close();
        return affected;
    }

    public int delete(String phone_number) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parameters = { phone_number };
        int deleted = db.delete(
                herramientaConsts.TABLE_NAME,
                herramientaConsts.PHONE_COLUMN_NAME + " = ?",
                parameters
        );
        db.close();
        return deleted;
    }

    public int assignToUser(Integer id, Integer userId) {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(herramientaConsts.ACTIVE_COLUMN_NAME, 1);
        values.put(herramientaConsts.USERID_COLUMN_NAME, userId);
        String [] parameters = { id.toString() };
        int updated = db.update(
                herramientaConsts.TABLE_NAME,
                values,
                herramientaConsts.ID_COLUMN_NAME + " = ?",
                parameters
        );
        db.close();
        return updated;
    }
}
