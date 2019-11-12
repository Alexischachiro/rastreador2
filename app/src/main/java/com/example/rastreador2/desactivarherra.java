package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.rastreador2.entidades.Herramienta;
import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.repositories.herramientaRepo;

import java.util.ArrayList;

public class desactivarherra extends AppCompatActivity {

    ListView listViewToolsForUser;
    Button btnAddTools, btnRemoveTools, btnDisasbleOperator;
    String userName, userPhone, userImage;
    Integer userId;
    ArrayList<Herramienta> herramientas = new ArrayList<>();
    ArrayList<String> herramientasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desactivarherra);

        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id", 0);
        userName = intent.getStringExtra("user_name");
        userPhone = intent.getStringExtra("user_phone");
        userImage = intent.getStringExtra("user_image");

        listViewToolsForUser = findViewById(R.id.listViewToolsForUser);
        herramientaRepo repo = new herramientaRepo(this);
        herramientas = repo.getToolsForUser(userId);
        this.getToolsList(herramientas);

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.layoutlist,
                herramientasList
        );

        listViewToolsForUser.setAdapter(adapter);
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
}
