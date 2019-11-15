package com.example.rastreador2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import com.example.rastreador2.repositories.herramientaRepo;
import com.example.rastreador2.entidades.Herramienta;

public class consultarbase extends AppCompatActivity {

    EditText campoid, camponombre, camposerie;
    ListView listViewPersonas;
    ArrayList<String> listaInformacion = new ArrayList<>();
    ArrayAdapter adatador;
    ArrayList<Herramienta> herramientas = new ArrayList<>();
    herramientaRepo repo;
    Button btnBuscar, btnActualizar, btnEliminar;
    final String NOT_TOOLS_REGISTERED = "No existen herramientas registradas";
    String originalId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarbase);

        listViewPersonas = findViewById(R.id.listViewPersonas);
        repo = new herramientaRepo(this);
        herramientas = repo.getAll();
        this.obtenerLista(herramientas);

        adatador = new ArrayAdapter(this,R.layout.layoutlist, listaInformacion);
        adatador.notifyDataSetChanged();
        listViewPersonas.setAdapter(adatador);

        if(herramientas.size() != 1 || !herramientas.get(0).equals(NOT_TOOLS_REGISTERED)) {
            listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Herramienta herramienta = herramientas.get(position);
                    originalId = herramienta.getPhoneNumber();
                    campoid.setText(herramienta.getPhoneNumber());
                    camponombre.setText(herramienta.getNombre());
                    camposerie.setText(herramienta.getSerie());
                }
            });
        }

        campoid = findViewById(R.id.ID);
        camponombre = findViewById(R.id.nombre);
        camposerie = findViewById(R.id.numeroserie);

        btnBuscar = findViewById(R.id.buscar);
        btnActualizar = findViewById(R.id.BtnActualizar);
        btnEliminar = findViewById(R.id.eliminar);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Confirmar");
        builder.setMessage("¿Estás seguro de eliminar a esta herramienta?");
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarusuario();
                        adatador.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_number = campoid.getText().toString();
                buscar(phone_number);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarusuario();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void obtenerLista(ArrayList<Herramienta> herramientas) {
        listaInformacion.clear();
        if(!herramientas.isEmpty()) {
            for (int i = 0; i < herramientas.size(); i++){
                listaInformacion.add(herramientas.get(i).getPhoneNumber()
                        +" - "
                        + herramientas.get(i).getNombre());
            }
        } else {
            listaInformacion.add(NOT_TOOLS_REGISTERED);
        }

    }

    private void eliminarusuario() {
        String phone_number = campoid.getText().toString();
        int rowsDeleted = new herramientaRepo(this).delete(phone_number);
        if(rowsDeleted > 0) {
            campoid.setText("");
            Toast.makeText(getApplicationContext(),"Herramienta eliminada", Toast.LENGTH_SHORT).show();
            limpiar();
        } else {
            Toast.makeText(getApplicationContext(),"Error al eliminar", Toast.LENGTH_SHORT).show();
        }
        herramientas.clear();
        listaInformacion.clear();
        limpiar();
        herramientas = repo.getAll();
        this.obtenerLista(herramientas);
        adatador.notifyDataSetChanged();


    }

    private void actualizarusuario() {

        String phone_number = campoid.getText().toString();
        String name = camponombre.getText().toString();
        String serie = camposerie.getText().toString();

        int rowsAffected = new herramientaRepo(this).update(originalId, phone_number, name, serie);
        if(rowsAffected > 0) {
            Toast.makeText(getApplicationContext(),"Herramienta actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Error al actualizar", Toast.LENGTH_SHORT).show();
        }
        limpiar();
        herramientas.clear();
        listaInformacion.clear();
        herramientas = repo.getAll();
        this.obtenerLista(herramientas);
        adatador.notifyDataSetChanged();
    }

    private void buscar(String phone_number) {
        try {
            Herramienta herramienta = new herramientaRepo(this).getOne(phone_number);
            originalId = phone_number;
            camponombre.setText(herramienta.getNombre());
            camposerie.setText(herramienta.getSerie());
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"La herramienta no existe", Toast.LENGTH_SHORT).show();
            limpiar();

        }

    }

    private void limpiar() {
        camponombre.setText("");
        camposerie.setText("");
    }

}
