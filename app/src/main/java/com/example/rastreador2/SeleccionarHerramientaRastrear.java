package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import com.example.rastreador2.entidades.Herramienta;
import com.example.rastreador2.repositories.herramientaRepo;

public class SeleccionarHerramientaRastrear extends AppCompatActivity {

    ListView listViewHerramientasRastrear;
    ArrayList<Herramienta> herramientas;
    ArrayList<String> listHerramientas = new ArrayList<>();
    final String NOT_TOOLS_AVAILABLE = "No hay herramientas en uso en este momento";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_herramienta_rastrear);
        herramientaRepo repo = new herramientaRepo(this);
        listViewHerramientasRastrear = findViewById(R.id.herramientasRastreoLV);
        herramientas = repo.getActive();
        this.getHerramientaStringList(herramientas);
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.layoutlist,
                listHerramientas
        );
        listViewHerramientasRastrear.setAdapter(adapter);

        if(listHerramientas.size() != 1 || !listHerramientas.get(0).equals(NOT_TOOLS_AVAILABLE)) {
            // Handle click on a tool for track
            listViewHerramientasRastrear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Log.d("ONCLICK","item" + listViewHerramientasRastrear.getItemAtPosition(position));
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("tool_phone_id", herramientas.get(position).getPhoneNumber());
                    intent.putExtra("tool_name", herramientas.get(position).getNombre());
                    intent.putExtra("tool_user_id", herramientas.get(position).getUserId());
                    startActivity(intent);
                }
            });
        }
    }

    private void getHerramientaStringList(ArrayList<Herramienta> herramientas) {
        if(!herramientas.isEmpty()) {
            for(int i = 0; i < herramientas.size(); i++) {
                listHerramientas.add(String.format(
                        "%s (%s)",
                        herramientas.get(i).getNombre(),
                        herramientas.get(i).getPhoneNumber()
                ));
            }
        } else {
            listHerramientas.add(NOT_TOOLS_AVAILABLE);
        }
    }
}
