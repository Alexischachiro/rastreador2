package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rastreador2.utilidades.utilidades;

public class RegistrarUsuarios extends AppCompatActivity {

    EditText campoID,camponombre,camposerie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuarios);

        campoID = (EditText) findViewById(R.id.campoID);
        camponombre = (EditText) findViewById(R.id.camponombre);
        camposerie = (EditText) findViewById(R.id.camposerie);
    }
    public void onClick(View view){
        //registrarherra();
        registrarUsuariosSql();
    }

    private void registrarUsuariosSql() {

        conexionSQ conn= new conexionSQ(this, "bd_usuarios", null, 1);
        SQLiteDatabase db=conn.getWritableDatabase();


        String insert="INSERT INTO" +utilidades.TABLA_USUARIOS
                +"(" +utilidades.CAMPO_ID+","+utilidades.CAMPO_NOMBRE+","+utilidades.CAMPO_SERIE+"" +
                ") VALUES ("+campoID.getText().toString()+", ' "+camponombre.getText().toString()+"','"
        +camposerie.getText().toString()+"')";

       db.execSQL(insert);

        db.close();
    }

    private void registrarherra(){
        conexionSQ conn= new conexionSQ(this, "bd_usuarios", null, 1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(utilidades.CAMPO_ID, campoID.getText().toString());
        values.put(utilidades.CAMPO_NOMBRE, camponombre.getText().toString());
        values.put(utilidades.CAMPO_SERIE, camposerie.getText().toString());
        Long idResultante=db.insert(utilidades.TABLA_USUARIOS, utilidades.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(), "Id Registro:"+idResultante,Toast.LENGTH_SHORT).show();

   db.close();
    }
}
