package com.example.thomas.dijoncity.Reveivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.thomas.dijoncity.Activities.DetailsActivity;
import com.example.thomas.dijoncity.Models.Poi;

/**
 * Created by Thomas on 25/10/2017.
 */

public class SMSReceiver extends BroadcastReceiver {

    private final String ACTION_RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_RECEIVE_SMS))
        {
            Toast.makeText(context, "T'as un message mon poto !", Toast.LENGTH_LONG).show();

            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                Object[] pdus = (Object[]) bundle.get("pdus");

                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++)  {  messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);  }  if (messages.length > -1)
                {
                    final String messageBody = messages[0].getMessageBody();
                    final String phoneNumber = messages[0].getDisplayOriginatingAddress();

                    Toast.makeText(context, "Expediteur : " + phoneNumber, Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Message : " + messageBody, Toast.LENGTH_LONG).show();

                    if (phoneNumber.equals("6505551212")) {
                        Intent poiIntent = new Intent(context, DetailsActivity.class);
                        poiIntent.putExtra("id", messageBody);
                        poiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(poiIntent);
                    }
                }
            }
        }
    }
}
