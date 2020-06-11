package com.manu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private PowerManager.WakeLock mWakeLock;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StandbyBroadcastReceiver.registerStandbyBroadcastReceiver(this);
        // 获取电源管理器
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        // 创建唤醒锁
        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, MainActivity.class.getCanonicalName());

        findViewById(R.id.btnWakeLock).setOnClickListener(v -> mWakeLock.acquire(10000*60));
        findViewById(R.id.btnWakeUnLock).setOnClickListener(v -> mWakeLock.release());
    }
}