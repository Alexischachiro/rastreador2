package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rastreador2.repositories.herramientaRepo;

public class RegistrarUsuarios extends AppCompatActivity {

    EditText campoID, camponombre, camposerie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuarios);

        campoID = findViewById(R.id.campoID);
        camponombre = findViewById(R.id.camponombre);
        camposerie = findViewById(R.id.camposerie);
    }
    public void onClick(View view){
        registrarHerramienta();
    }

    private void registrarHerramienta(){
        Log.d("caca", "Llego a registrar herramienta");
        String phone_number = campoID.getText().toString();
        String name = camponombre.getText().toString();
        String serie = camposerie.getText().toString();
        if(!phone_number.equals("") && !name.equals("") && !serie.equals(""))
        {
            Long insertedId = new herramientaRepo(this).create(phone_number, name, serie);
            Log.d("Inserted ID", insertedId.toString());
            if(insertedId > 0) {
                Toast.makeText(getApplicationContext(), "Herramienta registrada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Algo falló al registrar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Debes introducir todos los campos", Toast.LENGTH_LONG).show();
        }


    }
}
