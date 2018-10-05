package com.example.lnthe54.miniproject2.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.lnthe54.miniproject2.service.MusicService;
import com.example.lnthe54.miniproject2.utils.AppController;
import com.example.lnthe54.miniproject2.view.activity.PlayMusicActivity;

/**
 * @author lnthe54 on 10/5/2018
 * @project MiniProject2
 */
public class PreviousSong extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PlayMusicActivity musicActivity = (PlayMusicActivity) AppController.getInstance().getPlayMusicActivity();
        MusicService musicService = (MusicService) AppController.getInstance().getMusicService();

        if (musicActivity != null) {
            // Note: check presenter
            musicActivity.back();
        } else {
            musicService.back();
        }
    }
}
