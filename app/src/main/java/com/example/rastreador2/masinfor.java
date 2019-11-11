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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rastreador2.repositories.usuarioRepo;

import com.example.rastreador2.entidades.Usuario;

public class masinfor extends AppCompatActivity {

    String latituD, longituD, horA, fechA;
    TextView latitud, longitud, hora, fecha, userName, userPhone;
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
        userPhone.setText(operator.getPhone_number());

    }

}

