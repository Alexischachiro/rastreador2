package com.example.rastreador2;

import androidx.appcompat.app.AppCompatActivity;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

    Button btnregreso;
    Button btnmasinfo;
    String nombre="";

    private static final int my_permissions_request_receive_sms = 0;
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    TextView messageTV, numberTV, coor;
    MyReceiver receiver = new MyReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            messageTV.setText(msg);
            numberTV.setText(phoneNo);
            String[] data = msg.split(",");
nombre = data[0];
            //Log.v("number", data[0]);
            //coor.setText(data[0]);

            Double latitud = Double.parseDouble(data[0]);
            Double longitud = Double.parseDouble(data[1].split("'")[0]);
            Log.i("Latitud", data[0]);
            Log.i("Longitud", data[1].split("'")[0]);
            mapa(latitud, longitud);
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

        btnregreso = (Button)findViewById(R.id.regreso);
        btnregreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(MainActivity.this, GPS.class);
                startActivity(intent);
            }
        });

        btnmasinfo= (Button)findViewById(R.id.masinfo);
        btnmasinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, masinfor.class);
                intent.putExtra("nombre",nombre);
                startActivity(intent);
            }
        });

        messageTV = findViewById(R.id.message);
        numberTV = findViewById(R.id.number);
        //coor = findViewById(R.id.number);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS))
            {

            }
            else
            {
               ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, my_permissions_request_receive_sms);
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
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
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
