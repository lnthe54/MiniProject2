package com.example.lnthe54.miniproject2.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author lnthe54 on 10/11/2018
 * @project MiniProject2
 */
public class MP3Service extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // START_STICKY : tu dong khoi dong lai
        return START_NOT_STICKY; // khong tu dong khoi dong lai
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
