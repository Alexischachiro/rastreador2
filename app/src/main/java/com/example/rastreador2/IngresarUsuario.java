package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class IngresarUsuario extends AppCompatActivity {

    EditText campoNombre,campoDocumento,campoCelular;
    Button btnRegistro, btnFoto;
    ImageView imgFoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_usuario);
    }

    public void onClick(View view) {
    }
}
