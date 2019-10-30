package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class seleccionarherramienta extends AppCompatActivity {
Button btninicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionarherramienta);

        btninicio = (Button)findViewById(R.id.finalizar);
        btninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(seleccionarherramienta.this, GPS.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view) {
    }
}
