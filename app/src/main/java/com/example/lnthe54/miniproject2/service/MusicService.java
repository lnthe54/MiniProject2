package com.example.lnthe54.miniproject2.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.utils.AppController;
import com.example.lnthe54.miniproject2.utils.Common;
import com.example.lnthe54.miniproject2.utils.Config;
import com.example.lnthe54.miniproject2.utils.ConfigAction;
import com.example.lnthe54.miniproject2.view.activity.MainActivity;
import com.example.lnthe54.miniproject2.view.activity.PlayMusicActivity;

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
    private RemoteViews views;
    private Notification notification;
    private NotificationManager notificationManager;

    private int currentSongPosition;
    private Song currentSong;
    private ArrayList<Song> listSong;

    private boolean isShowNotification = false;
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (ConfigAction.ACTION_STOP.equals(intent.getAction())) {
            PlayMusicActivity musicActivity = (PlayMusicActivity) AppController.getInstance().getPlayMusicActivity();
            MainActivity mainActivity = (MainActivity) AppController.getInstance().getMainActivity();
            if (musicActivity != null) {
                musicActivity.changePlayState();
            }
            if (mainActivity != null) {
                mainActivity.clickBtnPlayPause();
            }
            if (musicActivity != null && mainActivity == null) {
                stopSelf();
            }

            pause();
            stopForeground(true);
            isShowNotification = false;
        } else {
            showNotification(isShowNotification());
            isShowNotification = true;
        }

        return START_NOT_STICKY;
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
                    showNotification(true);
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

    public boolean isShowNotification() {
        return isShowNotification;
    }

    public Notification showNotification(boolean isStatus) {
        views = new RemoteViews(getPackageName(), R.layout.layout_notification);
        Intent openPlayMusic = new Intent(getApplicationContext(), PlayMusicActivity.class);
        openPlayMusic.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        openPlayMusic.setAction(Intent.ACTION_MAIN);
        openPlayMusic.addCategory(Intent.CATEGORY_LAUNCHER);
        openPlayMusic.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openPlayMusic.putExtra(Config.IS_PLAYING, true);

        if (isPlaying()) {
            views.setImageViewResource(R.id.iv_play_pause_noti, R.drawable.pause_music);
        } else {
            views.setImageViewResource(R.id.iv_play_pause_noti, R.drawable.play_music);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, openPlayMusic, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentPrev = new Intent(ConfigAction.ACTION_PREV);
        intentPrev.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent piPrevious = PendingIntent.getBroadcast(getApplicationContext(),
                0, intentPrev, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentPlayPause = new Intent(ConfigAction.ACTION_PLAY_PAUSE);
        intentPlayPause.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent piPlayPause = PendingIntent.getBroadcast(getApplicationContext(),
                0, intentPlayPause, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentNext = new Intent(ConfigAction.ACTION_NEXT);
        intentNext.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent piNext = PendingIntent.getBroadcast(getApplicationContext(),
                0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentStopSelf = new Intent(this, MusicService.class);
        intentStopSelf.setAction(ConfigAction.ACTION_STOP);
        PendingIntent piStop = PendingIntent.getService(this,
                0, intentStopSelf, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "lnthe54");
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.ic_song);
        builder.setCustomBigContentView(views);

        views.setTextViewText(R.id.tv_name_song, currentSong.getNameSong());
        views.setTextViewText(R.id.tv_artist_song, currentSong.getArtistSong());

        String imageSong = listSong.get(currentSongPosition).getAlbumImage();
        if (imageSong != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageSong);
            views.setImageViewBitmap(R.id.iv_notification, bitmap);
        } else {
            views.setImageViewResource(R.id.iv_notification, R.drawable.ic_album_default);
        }

        notification = builder.build();

        views.setOnClickPendingIntent(R.id.iv_close, piStop);
        views.setOnClickPendingIntent(R.id.iv_previous_noti, piPrevious);
        views.setOnClickPendingIntent(R.id.iv_play_pause_noti, piPlayPause);
        views.setOnClickPendingIntent(R.id.iv_next_noti, piNext);

        if (isStatus) {
            startForeground(Config.NOTIFICATION, notification);
        }
        return notification;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
