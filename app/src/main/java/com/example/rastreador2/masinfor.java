package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rastreador2.repositories.usuarioRepo;

import com.example.rastreador2.entidades.Usuario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class masinfor extends AppCompatActivity {

    String latituD, longituD, horA, fechA;
    TextView latitud, longitud, hora, fecha, userName, userPhone;
    ImageView operatorImage;
    Integer userId;
    Usuario operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masinfor);

        Intent intent = getIntent();
        latituD = intent.getStringExtra("latitud");
        latitud = findViewById(R.id.coorde);
        latitud.setText(latituD);

        longituD = intent.getStringExtra("longitud");
        longitud = findViewById(R.id.longitud);
        longitud.setText(longituD);

        horA = intent.getStringExtra("hora");
        hora = findViewById(R.id.hora);
        hora.setText(horA);

        fechA = intent.getStringExtra("fecha");
        fecha = findViewById(R.id.fecha);
        fecha.setText(fechA);

        userId = intent.getIntExtra("tool_user_id", 0);
        usuarioRepo repo = new usuarioRepo(this);
        operator = repo.getOne(userId);
        Log.d("KEK", operator.getNombre());
        userName = findViewById(R.id.operatorName);
        userName.setText(operator.getNombre());
        userPhone = findViewById(R.id.operatorNumber);
        userPhone.setText(String.format("%s (Click para marcar)", operator.getPhone_number()));
        operatorImage = findViewById(R.id.operatorImage);
        File file = new File(operator.getImage_path());
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            operatorImage.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        userPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(String.format("tel:%s", operator.getPhone_number())));
                startActivity(intent);
            }
        });
    }

}

