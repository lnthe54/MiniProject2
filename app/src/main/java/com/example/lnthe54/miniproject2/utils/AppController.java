package com.example.lnthe54.miniproject2.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

/**
 * @author lnthe54 on 10/1/2018
 * @project MiniProject2
 */
public class AppController extends Application {
    private static AppController controller = new AppController();

    public static AppController getInstance() {
        return controller;
    }

    private Service musicService;
    private Activity mainActivity;
    private Activity playMusicActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        controller = this;
    }

    public Service getMusicService() {
        return musicService;
    }

    public void setMusicService(Service musicService) {
        this.musicService = musicService;
    }

    public Activity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Activity getPlayMusicActivity() {
        return playMusicActivity;
    }

    public void setPlayMusicActivity(Activity playMusicActivity) {
        this.playMusicActivity = playMusicActivity;
    }
}
