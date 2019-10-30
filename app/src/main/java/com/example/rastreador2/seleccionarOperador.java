package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class seleccionarOperador extends AppCompatActivity {

    Button btnselecherra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_operador);

        btnselecherra = (Button)findViewById(R.id.seleccionarherra);
        btnselecherra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(seleccionarOperador.this, seleccionarherramienta.class);
                startActivity(intent);
            }
        });


    }

    public void onClick(View view) {
    }
}
