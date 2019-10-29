package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import com.example.rastreador2.repositories.herramientaRepo;
import com.example.rastreador2.entidades.Herramienta;

public class consultarbase extends AppCompatActivity {

    EditText campoid, camponombre, camposerie;
    conexionSQ conn;
    ListView listViewPersonas;
    ArrayList<String> listaInformación = new ArrayList<String>();

    conexionSQ coon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarbase);

        listViewPersonas = (ListView) findViewById(R.id.listViewPersonas);
        herramientaRepo repo = new herramientaRepo(this);
        ArrayList<Herramienta> herramientas = repo.getAll();
        Log.d("caca", herramientas.toString());
        this.obtenerLista(herramientas);

        ArrayAdapter adatador = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listaInformación);
        listViewPersonas.setAdapter(adatador);
        campoid = findViewById(R.id.ID);
        camponombre = findViewById(R.id.nombre);
        camposerie = findViewById(R.id.numeroserie);

    }


    private void obtenerLista(ArrayList<Herramienta> herramientas) {
        if(!herramientas.isEmpty()) {
            Log.d("caca", "Entro a for");
            for (int i = 0; i < herramientas.size(); i++){
                listaInformación.add(herramientas.get(i).getPhoneNumber()
                        +" - "
                        + herramientas.get(i).getNombre());
            }
        } else {
            Log.d("CACA", "vacio");
            listaInformación = new ArrayList<String>();
            listaInformación.add("No hay herramientas aún");
        }
    }


    public void onClick (View view){

        switch (view.getId()) {

            case R.id.buscar:
                String phone_number = campoid.getText().toString();
                buscar(phone_number);
                break;
            case R.id.actualizar:
                actualizarusuario();
                break;
            case R.id.eliminar:
                eliminarusuario();
                break;
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


    }

    private void actualizarusuario() {

        String phone_number = campoid.getText().toString();
        String name = camponombre.getText().toString();
        String serie = camposerie.getText().toString();

        int rowsAffected = new herramientaRepo(this).update(phone_number, name, serie);
        if(rowsAffected > 0) {
            Toast.makeText(getApplicationContext(),"Herramienta actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Error al actualizar", Toast.LENGTH_SHORT).show();
        }
        limpiar();
    }

    private void buscar(String phone_number) {
        try {
            Herramienta herramienta = new herramientaRepo(this).getOne(phone_number);
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
