package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.rastreador2.repositories.herramientaRepo;
import com.example.rastreador2.entidades.Herramienta;

import java.util.ArrayList;

public class seleccionarherramienta extends AppCompatActivity {
    Button btninicio;
    TextView txtUserSelected;
    ListView listViewHerramientas;
    String userName, userPhoneNumber;
    Integer userActive, userId;
    ArrayList<Herramienta> herramientas;
    ArrayList<String> listHerramientas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionarherramienta);

        Intent intent = getIntent();
        userName = intent.getStringExtra("user_name");
        userPhoneNumber = intent.getStringExtra("user_phone_number");
        userActive = intent.getIntExtra("user_active", 0);
        userId = intent.getIntExtra("user_id", 0);
        Log.d("userName", userName);
        //getting Herramientas from DB
        listViewHerramientas = findViewById(R.id.listViewHerramientas);
        herramientaRepo repo = new herramientaRepo(this);
        herramientas = repo.getAll();
        this.getHerramientaList(herramientas);
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listHerramientas
        );
        listViewHerramientas.setAdapter(adapter);
        txtUserSelected = findViewById(R.id.txtUser);
        txtUserSelected.setText(String.format(
                "Operador seleccionado: %s",
                userName
        ));



        btninicio = findViewById(R.id.finalizar);
        btninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(seleccionarherramienta.this, GPS.class);
                startActivity(intent);
            }
        });
    }

    private void getHerramientaList(ArrayList<Herramienta> herramientas) {
        if(!herramientas.isEmpty()) {
            for(int i = 0; i < herramientas.size(); i++) {
                listHerramientas.add(String.format(
                        "%s - %s",
                        herramientas.get(i).getPhoneNumber(),
                        herramientas.get(i).getNombre()
                )
                        );
            }
        } else {
            listHerramientas.add("No existen herramientas registrados");
        }
    }

    public void onClick(View view) {
    }
}
