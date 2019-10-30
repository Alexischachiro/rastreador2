package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.repositories.usuarioRepo;

public class seleccionarOperador extends AppCompatActivity {

    Button btnselecherra;
    ListView listViewUsuarios;
    ArrayList<String> listaUsuarios = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_operador);

        btnselecherra = findViewById(R.id.seleccionarherra);
        btnselecherra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(seleccionarOperador.this, seleccionarherramienta.class);
                startActivity(intent);
            }
        });
        listViewUsuarios = findViewById(R.id.listViewUsers);
        usuarioRepo repo = new usuarioRepo(this);
        ArrayList<Usuario> usuarios = repo.getAll();
        this.getUserLists(usuarios);

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listaUsuarios
        );
        listViewUsuarios.setAdapter(adapter);
    }

    private void getUserLists(ArrayList<Usuario> usuarios) {
        if(!usuarios.isEmpty()) {
            for(int i = 0; i < usuarios.size(); i++) {
                listaUsuarios.add(usuarios.get(i).getNombre());
            }
        } else {
            listaUsuarios.add("No existen usuarios registrados");
        }
    }

}
