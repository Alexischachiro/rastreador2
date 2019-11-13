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

import java.util.ArrayList;
import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.repositories.usuarioRepo;

public class seleccionarOperador extends AppCompatActivity {

    Button btnselecherra;
    ListView listViewUsuarios;
    ArrayList<String> listaUsuarios = new ArrayList<>();
    ArrayList<Usuario> usuarios;
    Usuario userSelected;
    TextView txtUserSelected;
    final String NOT_OPERATORS_AVAILABLE = "No hay operadores disponibles";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_operador);

        btnselecherra = findViewById(R.id.seleccionarherra);
        btnselecherra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtUserSelected.getText().equals("")) {
                    Intent intent= new Intent(seleccionarOperador.this, seleccionarherramienta.class);
                    intent.putExtra("user_name", userSelected.getNombre());
                    intent.putExtra("user_phone_number", userSelected.getPhone_number());
                    intent.putExtra("user_active", userSelected.getActivo());
                    intent.putExtra("user_id", userSelected.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Selecciona un operador", Toast.LENGTH_LONG).show();
                }
            }
        });
        listViewUsuarios = findViewById(R.id.listViewUsers);
        txtUserSelected = findViewById(R.id.txtUserSelected);
        usuarioRepo repo = new usuarioRepo(this);
        usuarios = repo.getNonActive();
        this.getUserLists(usuarios);

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.layoutlist,
                listaUsuarios
        );
        listViewUsuarios.setAdapter(adapter);

        if(listaUsuarios.size() != 1 || !listaUsuarios.get(0).equals(NOT_OPERATORS_AVAILABLE)) {
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
    }

    private void getUserLists(ArrayList<Usuario> usuarios) {
        if(!usuarios.isEmpty()) {
            for(int i = 0; i < usuarios.size(); i++) {
                listaUsuarios.add(usuarios.get(i).getNombre());
            }
        } else {
            listaUsuarios.add(NOT_OPERATORS_AVAILABLE);
        }
    }

    private void setSelectedUser(int position) {
        userSelected = usuarios.get(position);
        txtUserSelected.setText(userSelected.getNombre());
    }

}
