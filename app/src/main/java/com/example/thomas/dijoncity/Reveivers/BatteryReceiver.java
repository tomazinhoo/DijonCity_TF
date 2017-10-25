package com.example.thomas.dijoncity.Reveivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Thomas on 25/10/2017.
 */

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Attention mon poto ! T'as bientôt plus de batterie !", Toast.LENGTH_LONG).show();
    }
}
