package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.rastreador2.entidades.usuarios;
import com.example.rastreador2.utilidades.utilidades;

import java.util.ArrayList;

public class consultarbase extends AppCompatActivity {

    EditText campoid, camponombre, camposerie;
    conexionSQ conn;
    ListView listViewPersonas;
    ArrayList<String> listaInformación;
    ArrayList<usuarios> listaUsuarios;

    conexionSQ coon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarbase);

        conn=new conexionSQ(getApplicationContext(),"bd_usuarios",null,1);
        listViewPersonas = (ListView) findViewById(R.id.listViewPersonas);
        consultarListaPersonas();

        ArrayAdapter adatador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformación);
        listViewPersonas.setAdapter(adatador);

        conn =  new conexionSQ(getApplicationContext(), "bd_usuarios", null,1);
        campoid = (EditText) findViewById(R.id.ID);
        camponombre = (EditText) findViewById(R.id.nombre);
        camposerie = (EditText) findViewById(R.id.numeroserie);

    }

    private void consultarListaPersonas() {

        SQLiteDatabase db=conn.getReadableDatabase();
        usuarios usuario=null;
        listaUsuarios= new ArrayList<usuarios>();

        Cursor cursor=db.rawQuery("SELECT * FROM"+ utilidades.TABLA_USUARIOS,null);
        while (cursor.moveToNext()){
            usuario=new usuarios();
            usuario.setID(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setSerie(cursor.getString(2));

            listaUsuarios.add(usuario);

        }
        obtenerLista();

    }

    private void obtenerLista() {
        listaInformación=new ArrayList<String>();

        for (int i=0; i<listaUsuarios.size(); i++){
            listaInformación.add(listaUsuarios.get(i).getNombre()+" --- "+listaUsuarios.get(i).getID()+" --- "+listaUsuarios.get(i).getSerie());
        }
    }


    public void onClick (View view){

        switch (view.getId()) {

            case R.id.buscar:
                buscar1();
                break;
            case R.id.actualizar:
                actualizarusuario();
                break;
            case R.id.eliminar:
                eliminarusuario();
                break;
        }

    }

    private void eliminarusuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros= {campoid.getText().toString()};

        db.delete(utilidades.TABLA_USUARIOS, utilidades.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se ha eliminó la herramienta con exito",Toast.LENGTH_SHORT).show();
        campoid.setText("");
        limpiar();
        db.close();

    }

    private void actualizarusuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros= {campoid.getText().toString()};
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_NOMBRE,camponombre.getText().toString());
        values.put(utilidades.CAMPO_SERIE,camposerie.getText().toString());
        db.update(utilidades.TABLA_USUARIOS,values, utilidades.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"La base de datos ha sido actualizada",Toast.LENGTH_SHORT).show();
        db.close();

    }

    private void buscar1() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String [] parametros= {campoid.getText().toString()};
        String []campos = {utilidades.CAMPO_NOMBRE,utilidades.CAMPO_SERIE};

        try {
            Cursor cursor=db.query(utilidades.TABLA_USUARIOS,campos, utilidades.CAMPO_ID+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            camponombre.setText(cursor.getString(0));
            camposerie.setText(cursor.getString(1));
            cursor.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"La herramienta no existe",Toast.LENGTH_SHORT).show();
            limpiar();
        }



    }

    private void limpiar() {
        camponombre.setText("");
        camposerie.setText("");
    }

}
