package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.repositories.usuarioRepo;

import java.util.ArrayList;

public class detallesOperadorHerrameintas extends AppCompatActivity {

    ListView listViewOperatorsStatus;
    ArrayList<String> operatorsStatusList = new ArrayList<>();
    ArrayList<Usuario> operatorsStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_operador_herrameintas);
        listViewOperatorsStatus = findViewById(R.id.listViewOperatorsStatus);
        usuarioRepo repo = new usuarioRepo(this);
        operatorsStatus = repo.getActive();
        Log.d("ROTOS", operatorsStatus.toString());
        this.getOperatorsList(operatorsStatus);

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.layoutlist,
                operatorsStatusList
        );
        listViewOperatorsStatus.setAdapter(adapter);

        listViewOperatorsStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), desactivarherra.class);
                intent.putExtra("user_id", operatorsStatus.get(position).getId());
                intent.putExtra("user_name", operatorsStatus.get(position).getNombre());
                intent.putExtra("user_phone", operatorsStatus.get(position).getPhone_number());
                intent.putExtra("user_image", operatorsStatus.get(position).getImage_path());
                startActivity(intent);
            }
        });
    }

    private void getOperatorsList(ArrayList<Usuario> usuarios) {
        if(!usuarios.isEmpty()) {
            Log.d("No deberia entra", "Aqui");
            for(int i = 0; i < usuarios.size(); i++) {
                operatorsStatusList.add(usuarios.get(i).getNombre());
            }
        } else {
            operatorsStatusList.add("No hay usuario activos");
        }
    }
}
