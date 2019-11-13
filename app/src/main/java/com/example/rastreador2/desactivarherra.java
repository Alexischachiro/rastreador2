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

import com.example.rastreador2.entidades.Herramienta;
import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.repositories.herramientaRepo;

import java.util.ArrayList;

public class desactivarherra extends AppCompatActivity {

    ListView listViewToolsForUser;
    Button btnAddTools, btnRemoveTools, btnDisasbleOperator, btnSave;
    String userName, userPhone, userImage;
    Integer userId;
    String mode;
    ArrayList<Herramienta> herramientasRelated = new ArrayList<>();
    ArrayList<Herramienta> herramientasNotRelated = new ArrayList<>();
    ArrayList<String> herramientasList = new ArrayList<>();
    ArrayList<Integer> selectedAddToolsIndexes = new ArrayList<>();
    ArrayList<Integer> selectedRemoveToolsIndexes = new ArrayList<>();
    TextView textViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desactivarherra);

        btnAddTools = findViewById(R.id.reingresarherra);
        btnRemoveTools = findViewById(R.id.desactivarherra);
        btnDisasbleOperator = findViewById(R.id.desactivartodaherra);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setVisibility(View.INVISIBLE);
        textViewLayout = findViewById(R.id.textViewLayout);

        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id", 0);
        userName = intent.getStringExtra("user_name");
        userPhone = intent.getStringExtra("user_phone");
        userImage = intent.getStringExtra("user_image");

        listViewToolsForUser = findViewById(R.id.listViewToolsForUser);
        herramientaRepo repo = new herramientaRepo(this);
        herramientasRelated = repo.getToolsForUser(userId);
        herramientasNotRelated = repo.getNonActive();
        this.getToolsList(herramientasRelated);

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.layoutlist,
                herramientasList
        );

        listViewToolsForUser.setAdapter(adapter);


        btnAddTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = "adding";
                btnSave.setVisibility(View.VISIBLE);
                textViewLayout.setText("Herramientas disponibles");
                listViewToolsForUser.setAdapter(null);
                herramientasList.clear();
                getToolsList(herramientasNotRelated);
                ArrayAdapter adapter2 = new ArrayAdapter(
                        getApplicationContext(),
                        R.layout.layoutlistmultiple,
                        herramientasList
                );

                listViewToolsForUser.setAdapter(adapter2);
                listViewToolsForUser.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listViewToolsForUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        handleAddNewTools(position);
                    }
                });
            }
        });

        btnRemoveTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = "removing";
                btnSave.setVisibility(View.VISIBLE);
                textViewLayout.setText("Herramientas activas");
                listViewToolsForUser.setAdapter(null);
                herramientasList.clear();
                getToolsList(herramientasRelated);
                ArrayAdapter adapter3 = new ArrayAdapter(
                        getApplicationContext(),
                        R.layout.layoutlistmultiple,
                        herramientasList
                );
                listViewToolsForUser.setAdapter(adapter3);
                listViewToolsForUser.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listViewToolsForUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        handleRemoveTools(position);
                    }
                });

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode.equals("adding")) {
                    addTools();
                } else if(mode.equals("removing")) {
                    removeTools();
                }
            }
        });
    }

    private void getToolsList(ArrayList<Herramienta> herramientas) {
        if(!herramientas.isEmpty()) {
            for(int i = 0; i < herramientas.size(); i++) {
                herramientasList.add(String.format("%s (%s)",
                        herramientas.get(i).getNombre(),
                        herramientas.get(i).getPhoneNumber()
                ));
            }
        } else {
            herramientasList.add("Este operador no cuenta con herramientas asignadas");
        }
    }

    private void handleAddNewTools(Integer position) {
        if(selectedAddToolsIndexes.contains(position)) {
            selectedAddToolsIndexes.remove(
                    selectedAddToolsIndexes.indexOf(position)
            );
        } else {
            selectedAddToolsIndexes.add(position);
        }
        Log.d("New tools indexes", selectedAddToolsIndexes.toString());
        Log.d("No se que pedo", herramientasRelated.toString());
    }

    private void handleRemoveTools(Integer position) {
        if(selectedRemoveToolsIndexes.contains(position)) {
            selectedRemoveToolsIndexes.remove(
                    selectedRemoveToolsIndexes.indexOf(position)
            );
        } else {
            selectedRemoveToolsIndexes.add(position);
        }

        Log.d("Remove tools indexes", selectedRemoveToolsIndexes.toString());
        Log.d("No se que pedo", herramientasRelated.toString());
   }

   private void addTools() {
        if(!selectedAddToolsIndexes.isEmpty()) {
            int counter = 0;
            herramientaRepo repo = new herramientaRepo(getApplicationContext());
            for(int i = 0; i < selectedAddToolsIndexes.size(); i++) {
                int herramientaIndex = selectedAddToolsIndexes.get(i);
                int herramientaId = herramientasNotRelated.get(herramientaIndex).getId();
                Log.d("herramientaId", String.valueOf(herramientaId));
                Log.d("userId", String.valueOf(userId));
                int updated = repo.assignToUser(herramientaId, userId);
                if(updated > 0) {
                    counter++;
                }
            }
            Toast.makeText(getApplicationContext(), "Herramientas actualizadas: " + counter, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "No has seleccionado ninguna herramienta", Toast.LENGTH_LONG).show();
        }
   }

    private void removeTools() {
        if(!selectedRemoveToolsIndexes.isEmpty()) {
            int counter = 0;
            herramientaRepo repo = new herramientaRepo(getApplicationContext());
            for(int i = 0; i < selectedRemoveToolsIndexes.size(); i++) {
                int herramientaIndex = selectedRemoveToolsIndexes.get(i);
                int herramientaId = herramientasRelated.get(herramientaIndex).getId();
                int updated = repo.unAssing(herramientaId);
                if(updated > 0) {
                    counter++;
                }
            }
            Toast.makeText(getApplicationContext(), "Herramientas removidas: " + counter, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "No has seleccionado ninguna herramienta", Toast.LENGTH_LONG).show();
        }
    }
}
