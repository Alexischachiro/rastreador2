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

import java.util.ArrayList;
import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.repositories.usuarioRepo;

public class seleccionarOperador extends AppCompatActivity {

    Button btnselecherra;
    ListView listViewUsuarios;
    ArrayList<String> listaUsuarios = new ArrayList<>();
    ArrayList<Usuario> usuarios = null;
    Usuario userSelected;
    TextView txtUserSelected;
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
        txtUserSelected = findViewById(R.id.txtUserSelected);
        usuarioRepo repo = new usuarioRepo(this);
        usuarios = repo.getAll();
        this.getUserLists(usuarios);

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listaUsuarios
        );
        listViewUsuarios.setAdapter(adapter);

        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("ONCLICK", "item " + listViewUsuarios.getItemAtPosition(position));
                Log.d("ONCLICK", "position " + position );
                Log.d("ONCLICK", "id " + id);
                Log.d("ONCLICK", "view " + view.toString());
                setSelectedUser(position);
            }
        });
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

    private void setSelectedUser(int position) {
        userSelected = usuarios.get(position);
        txtUserSelected.setText(userSelected.getNombre());
    }

}
