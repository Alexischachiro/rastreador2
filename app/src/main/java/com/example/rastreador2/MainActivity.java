package com.example.rastreador2;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

    Button btnregreso;
    Button btnmasinfo;
    String latitud = "";
    String longitud = "";
    String hora = "";
    String fecha = "";
    String tool_phone_id;
    String tool_name = "";
    Integer tool_user_id;

    private static final int my_permissions_request_receive_sms = 0;
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    TextView messageTV, numberTV;
    MyReceiver receiver = new MyReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if(phoneNo.equals(tool_phone_id)) {
                messageTV.setText(msg);
                String[] data = msg.split(",");
                if (data.length > 4) {
                    latitud = data[0];
                    longitud = data[1];
                    hora= data[3];
                    fecha = data[4];
                    Double latitud = Double.parseDouble(data[0]);
                    Double longitud = Double.parseDouble(data[1].split("'")[0]);
                    mapa(latitud, longitud);
                    btnmasinfo.setEnabled(true);
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        tool_phone_id = intent.getStringExtra("tool_phone_id");
        tool_name = intent.getStringExtra("tool_name");
        tool_user_id = intent.getIntExtra("tool_user_id", 0);


        btnregreso = findViewById(R.id.regreso);
        btnregreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(MainActivity.this, GPS.class);
                startActivity(intent);
            }
        });

        btnmasinfo = findViewById(R.id.masinfo);
        btnmasinfo.setEnabled(false);
        btnmasinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, masinfor.class);
                intent.putExtra("latitud",latitud);
                intent.putExtra("longitud",longitud);
                intent.putExtra("hora",hora);
                intent.putExtra("fecha",fecha);
                intent.putExtra("tool_user_id", tool_user_id);
                startActivity(intent);
            }
        });

        messageTV = findViewById(R.id.message);
        numberTV = findViewById(R.id.number);
        numberTV.setText(tool_phone_id);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS))
            {

            }
            else
            {
               ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, my_permissions_request_receive_sms);
            }
        }

    }

    public void mapa(double lat, double lon) {
        // Fragment del Mapa
        FragmentMaps fragment = new FragmentMaps();

        Bundle bundle = new Bundle();
        bundle.putDouble("lat", new Double(lat));
        bundle.putDouble("lon", new Double(lon));
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, fragment, null);
        fragmentTransaction.commit();
    }
    @Override
    public void onRequestPermissionsResult (int requestCode, String permissions [], int [] grantResults)
    {
        switch(requestCode)
        {
            case (my_permissions_request_receive_sms):
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Gracias por el permiso", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "No puedo hacer nada hasta que me lo permitas", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
