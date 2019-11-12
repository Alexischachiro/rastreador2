package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rastreador2.repositories.herramientaRepo;
import com.example.rastreador2.entidades.Herramienta;
import com.example.rastreador2.repositories.usuarioRepo;

import java.util.ArrayList;

public class seleccionarherramienta extends AppCompatActivity {
    Button btnFinalizar;
    TextView txtUserSelected;
    ListView listViewHerramientas;
    String userName, userPhoneNumber;
    Integer userActive, userId;
    ArrayList<Herramienta> herramientas;
    ArrayList<String> listHerramientas = new ArrayList<>();
    ArrayList<Integer> selectedHerramientasIndexes = new ArrayList<>();

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
        herramientas = repo.getNonActive();
        this.getHerramientaList(herramientas);
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.layoutlistmultiple,
                listHerramientas
        );
        listViewHerramientas.setAdapter(adapter);
        listViewHerramientas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewHerramientas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                handleSelectHerramienta(position);
            }
        });
        txtUserSelected = findViewById(R.id.txtUser);
        txtUserSelected.setText(String.format(
                "Operador seleccionado: %s",
                userName
        ));



        btnFinalizar = findViewById(R.id.finalizar);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedHerramientasIndexes.isEmpty()) {
                    for(int i = 0; i < selectedHerramientasIndexes.size(); i++) {
                        int herramientaIndex = selectedHerramientasIndexes.get(i);
                        int herramientaId = herramientas.get(herramientaIndex).getId();
                        herramientaRepo repo = new herramientaRepo(getApplicationContext());
                        int updated = repo.assignToUser(herramientaId, userId);
                        if(updated > 0) {
                            Toast.makeText(getApplicationContext(), "Herramienta actualizada", Toast.LENGTH_SHORT).show();
                        }
                    }
                    usuarioRepo userRepo = new usuarioRepo(getApplicationContext());
                    int updatedUser = userRepo.updateActive(userId);
                    Log.d("CACA", String.valueOf(updatedUser));
                    if(updatedUser > 0) {
                        Toast.makeText(getApplicationContext(), "Operador actualizado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se han seleccionado herramientas", Toast.LENGTH_LONG).show();
                }
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

    private void handleSelectHerramienta(Integer position) {
        if(selectedHerramientasIndexes.contains(position)) {
            selectedHerramientasIndexes.remove(
                    selectedHerramientasIndexes.indexOf(position)
            );
        } else {
            selectedHerramientasIndexes.add(position);
        }
        Log.d("INDEXES SELECTED", selectedHerramientasIndexes.toString());
    }


}
