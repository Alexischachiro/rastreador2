package com.example.rastreador2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final  String TAG = "SmsBroadcastReceiver";
    String msg, phoneNo = "";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i(TAG, "Intent Received: " + intent.getAction());
        if (intent.getAction()==SMS_RECEIVED)
        {
            Bundle dataBundle = intent.getExtras();
            if (dataBundle!=null)
            {
                Object[] mypdu = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];

                for (int i = 0; i<mypdu.length; i++)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        String format = dataBundle.getString("format");

                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i],format);
                    }
                    else
                    {
                        message[i]= SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }
                    msg = message[i].getMessageBody();
                    Log.d("Coordenadas", msg);
                    String[] data = msg.split(",");
                    Log.i("Latitud", data[0]);
                    Log.i("Longitud", data[1].split("'")[0]);
                    phoneNo = message[i].getOriginatingAddress();
                }
                Toast.makeText(context, "Coordenadas: "+msg + "\nId de Herramienta: "+phoneNo, Toast.LENGTH_LONG).show();
            }
        }

    }
}
