package com.example.rastreador2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rastreador2.repositories.usuarioRepo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

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
                Bitmap image = ((BitmapDrawable) imgFoto.getDrawable()).getBitmap();
                Bitmap emptyBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
                if(!name.equals("") && !phoneNumber.equals("") && !image.sameAs(emptyBitmap)) {
                    String imagePath = saveImageInExternaStorage(image, name);
                    Long insertedId = new usuarioRepo(getApplicationContext()).create(phoneNumber, name, imagePath);
                    Toast.makeText(getApplicationContext(), "Usuario creado con id " + insertedId.toString(), Toast.LENGTH_LONG).show();
                    nombre.setText("");
                    telefono.setText("");
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
            try {
                imgFoto.setImageBitmap(convertViewToBitmap(uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap convertViewToBitmap(Uri data) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data);
        return bitmap;
    }

    private String saveImageInExternaStorage(Bitmap image, String userName) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, String.format("%s-%s.jpg", userName, Calendar.getInstance().getTime().toString()));
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }
}
