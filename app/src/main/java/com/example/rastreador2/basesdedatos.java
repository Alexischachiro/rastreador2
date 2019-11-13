package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class basesdedatos extends AppCompatActivity {

    Button btnherra,btnope;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basesdedatos);

        btnherra = findViewById(R.id.btnherra);
        btnherra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(basesdedatos.this, consultarbase.class);
                startActivity(intent);
            }
        });
        btnope = findViewById(R.id.btnusuario);
        btnope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(basesdedatos.this, basedatosoperdores.class);
                startActivity(intent);
            }
        });
    }
}
