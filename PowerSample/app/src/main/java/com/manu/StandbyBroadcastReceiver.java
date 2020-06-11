package com.manu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * @Desc: StandbyBroadcastReceiver
 * @Author: jzman
 * @Date: 2020/6/9 15:36.
 */
public class StandbyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = StandbyBroadcastReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"intent:"+intent.getAction());
        if (intent.getAction() == null) return;


        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {

        }

        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
        }

        if (Intent.ACTION_SHUTDOWN.equals(action)) {

        }
    }

    public static void registerStandbyBroadcastReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SHUTDOWN);
        context.registerReceiver(new StandbyBroadcastReceiver(), filter);
    }
}
