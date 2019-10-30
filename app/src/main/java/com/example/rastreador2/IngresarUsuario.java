package com.example.rastreador2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rastreador2.repositories.usuarioRepo;
import com.example.rastreador2.entidades.Usuario;
import com.example.rastreador2.consts.usuarioConsts;

public class IngresarUsuario extends AppCompatActivity {

    Button btnFOTO, btnSave;
    ImageView imgFoto;
    EditText nombre, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_usuario);

        imgFoto = findViewById(R.id.imagen);
        btnFOTO = findViewById(R.id.btnfoto);
        nombre = findViewById(R.id.campooperador);
        telefono = findViewById(R.id.campocelular);
        btnSave = findViewById(R.id.BtnActualizar);
        btnFOTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"
                ));
                startActivityForResult(intent, 1);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nombre.getText().toString();
                String phoneNumber = telefono.getText().toString();
                if(!name.equals("") && !phoneNumber.equals("")) {
                    Long insertedId = new usuarioRepo(getApplicationContext()).create(phoneNumber, name);
                    Toast.makeText(getApplicationContext(), "Usuario creado con id " + insertedId.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Verifica tus datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==1){
            Uri uri=data.getData();
            imgFoto.setImageURI(uri);
        }
    }
}
