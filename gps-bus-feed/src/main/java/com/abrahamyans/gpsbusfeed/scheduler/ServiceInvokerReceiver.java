package com.abrahamyans.gpsbusfeed.scheduler;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * @author Samvel Abrahamyan
 */

public class ServiceInvokerReceiver extends WakefulBroadcastReceiver{

    private static final String TAG = "ServiceInvokerReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received broadcast");
        Intent serviceIntent = new Intent(context, LocationService.class);
        startWakefulService(context, serviceIntent);
    }
}
