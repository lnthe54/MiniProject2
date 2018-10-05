package com.example.lnthe54.miniproject2.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.utils.AppController;
import com.example.lnthe54.miniproject2.utils.Common;
import com.example.lnthe54.miniproject2.utils.ConfigAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author lnthe54 on 10/2/2018
 * @project MiniProject2
 */
public class MusicService extends Service {
    private LocalBinder localBinder = new LocalBinder();
    private static MediaPlayer mediaPlayer;

    private int currentSongPosition;
    private Song currentSong;
    private ArrayList<Song> listSong;

    private boolean isRepeat = false;
    private boolean isShuffle = false;
    private Random rd;

    public void setData(ArrayList<Song> listSong, int currentSongPosition, Song currentSong) {
        this.listSong = listSong;
        this.currentSongPosition = currentSongPosition;
        this.currentSong = currentSong;
    }

    public class LocalBinder extends Binder {
        public MusicService getInstanceBoundService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        rd = new Random();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    public void play(String path) {
        release();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (AppController.getInstance().getMainActivity() != null) {
                    Intent intent = new Intent(ConfigAction.ACTION_COMPLETE_SONG);
                    sendBroadcast(intent);
                    Common.updateMainActivity();
                } else {
                    if (isRepeat()) {
                        play(currentSong.getPath());
                    } else {
                        next();
                    }
                }
            }
        });

        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public void release() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            stop();
            mediaPlayer.release();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void resume() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void next() {
        currentSongPosition = getNextPosition();
        currentSong = listSong.get(currentSongPosition);
        String path = currentSong.getPath();

        play(path);
    }

    public void back() {
        if (currentSongPosition == 0) {
            currentSongPosition = listSong.size();
        } else {
            currentSongPosition--;
        }

        currentSong = listSong.get(currentSongPosition);
        String path = currentSong.getPath();

        play(path);
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void playPause() {
        if (mediaPlayer.isPlaying()) {
            pause();
        } else {
            resume();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }


    public int getTotalTime() {
        return mediaPlayer.getDuration() / 1000;
    }

    public int getCurrentLength() {
        return mediaPlayer.getCurrentPosition() / 1000;
    }

    public void seekTo(int progress) {
        mediaPlayer.seekTo(progress * 1000);
    }

    public int getNextPosition() {
        if (!isShuffle) {
            if (currentSongPosition == listSong.size() - 1) {
                return 0;
            }
        }

        if (currentSongPosition < 0) {
            return 0;
        }

        if (isRepeat()) {
            return currentSongPosition;
        }

        if (isShuffle) {
            int newSongPosition = currentSongPosition;
            while (newSongPosition == currentSongPosition) {
                newSongPosition = rd.nextInt(listSong.size());
            }
            return newSongPosition;
        }
        currentSongPosition += 1;
        return currentSongPosition;
    }

    public int getPreviousPosition() {
        if (isShuffle()) {
            int newSongPosition = currentSongPosition;
            while (newSongPosition == currentSongPosition) {
                newSongPosition = rd.nextInt(listSong.size());
            }
            return newSongPosition;
        }

        int newSongPosition;
        if (currentSongPosition == 0) {
            currentSongPosition = listSong.size() - 1;
        } else {
            currentSongPosition--;
        }
        newSongPosition = currentSongPosition;
        return newSongPosition;
    }

    public int getCurrentSongPosition() {
        return currentSongPosition;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public ArrayList<Song> getListSong() {
        return listSong;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean shuffle) {
        isShuffle = shuffle;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
