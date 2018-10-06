package com.example.lnthe54.miniproject2.view.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.presenter.PlayMusicPresenter;
import com.example.lnthe54.miniproject2.service.MusicService;
import com.example.lnthe54.miniproject2.utils.AppController;
import com.example.lnthe54.miniproject2.utils.BlurImage;
import com.example.lnthe54.miniproject2.utils.Common;
import com.example.lnthe54.miniproject2.utils.Config;
import com.example.lnthe54.miniproject2.utils.ConfigAction;
import com.example.lnthe54.miniproject2.utils.ConvertTime;
import com.example.lnthe54.miniproject2.view.fragment.FragmentShare;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author lnthe54 on 10/2/2018
 * @project MiniProject2
 */
public class PlayMusicActivity extends AppCompatActivity
        implements View.OnClickListener, PlayMusicPresenter.CallBack {

    private Toolbar toolbar;
    private CircleImageView ivPlaying;
    private SeekBar seekBarPlaying;
    private TextView tvTotalTime;
    private TextView tvTimePlayed;
    private RelativeLayout layoutPlayMusic;
    private RelativeLayout layoutShare;

    private ImageView ivPlayPause;
    private ImageView ivNextTrack;
    private ImageView ivPreviousTrack;
    private ImageView ivShuffle;
    private ImageView ivRepeat;

    private String nameSong;
    private String nameArtist;
    private String artSong;
    private String path;
    private int totalTimeSong;

    private ArrayList<Song> listSong;
    private int currentPosition;
    private boolean isShuffle = false;
    private boolean isPlaying = true;
    private boolean isSeeking;

    private MusicService musicService;
    private static PlayMusicPresenter musicPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        musicPresenter = new PlayMusicPresenter(this);
        AppController.getInstance().setPlayMusicActivity(this);
        musicService = (MusicService) AppController.getInstance().getMusicService();

        musicPresenter.getDataFromIntent();
        initViews();

        if (musicService == null) {
            initService();
        } else {
            musicService.showNotification(!musicService.isShowNotification());
            musicPresenter.updateSeekBar();
            totalTimeSong = musicService.getTotalTime();
            musicPresenter.updateData();
            if (!isPlaying) {
                musicPresenter.playMusic();
            }

            musicPresenter.updateShuffle();
            musicPresenter.updatePlayPause();
            musicPresenter.updateRepeat();

        }
        addEvents();
        registerBroadcastSongComplete();
        musicPresenter.updateMainActivity();
    }

    @Override
    public void getDataFromIntent() {
        Intent intent = getIntent();
        isPlaying = intent.getExtras().getBoolean(Config.IS_PLAYING);

        if (isPlaying) {
            path = musicService.getCurrentSong().getPath();
            currentPosition = musicService.getCurrentSongPosition();
            listSong = musicService.getListSong();
            isShuffle = musicService.isShuffle();

        } else {
            currentPosition = intent.getIntExtra(Config.SONG_POSITION, 0);
            path = intent.getStringExtra(Config.PATH);
            listSong = (ArrayList<Song>) intent.getSerializableExtra(Config.LIST_SONG);
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        layoutPlayMusic = findViewById(R.id.layout_play_music);
        layoutShare = findViewById(R.id.layout_share);

        ivPlaying = findViewById(R.id.iv_playing);
        tvTimePlayed = findViewById(R.id.tv_time_played);
        tvTotalTime = findViewById(R.id.tv_time_total);

        seekBarPlaying = findViewById(R.id.seek_bar_playing);

        ivShuffle = findViewById(R.id.iv_shuffle);
        ivPreviousTrack = findViewById(R.id.iv_previous);
        ivPlayPause = findViewById(R.id.iv_pause_play);
        ivNextTrack = findViewById(R.id.iv_next);
        ivRepeat = findViewById(R.id.iv_repeat);

        isSeeking = false;
    }

    private void addEvents() {
        musicPresenter.updateData();

        ivRepeat.setOnClickListener(this);
        ivShuffle.setOnClickListener(this);
        ivPlayPause.setOnClickListener(this);
        ivPreviousTrack.setOnClickListener(this);
        ivNextTrack.setOnClickListener(this);

        seekBarPlaying.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTimePlayed.setText(ConvertTime.miniSecondToString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                musicService.seekTo(seekBar.getProgress());
                if (!musicService.isPlaying()) {
                    musicService.resume();
                    ivPlayPause.setImageResource(R.drawable.ic_pause);
                }

                isSeeking = false;
                musicPresenter.updateSeekBar();
            }
        });
    }

    private void initService() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            musicService = binder.getInstanceBoundService();
            AppController.getInstance().setMusicService(musicService);
            musicService.setRepeat(false);
            musicPresenter.playMusic();
            musicPresenter.updateSeekBar();
            totalTimeSong = musicService.getTotalTime();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    BroadcastReceiver broadcastReceiverSongCompleted = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            musicPresenter.nextMusic();
            totalTimeSong = musicService.getTotalTime();
            musicPresenter.updateSeekBar();
            musicPresenter.updateMainActivity();
            musicService.showNotification(true);
            Common.updateMainActivity();
        }
    };

    private void registerBroadcastSongComplete() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConfigAction.ACTION_COMPLETE_SONG);
        registerReceiver(broadcastReceiverSongCompleted, intentFilter);
    }

    private void unRegisterBroadcastSongComplete() {
        unregisterReceiver(broadcastReceiverSongCompleted);
    }

    @Override
    public void updateData() {
        nameSong = listSong.get(currentPosition).getNameSong();
        nameArtist = listSong.get(currentPosition).getArtistSong();
        artSong = listSong.get(currentPosition).getAlbumImage();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_expand);
        getSupportActionBar().setTitle(nameSong);
        getSupportActionBar().setSubtitle(nameArtist);

        Bitmap bitmap;

        if (artSong != null) {
            bitmap = BitmapFactory.decodeFile(artSong);
            Glide.with(PlayMusicActivity.this).load(artSong).into(ivPlaying);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_bg_playing);
            ivPlaying.setImageResource(R.drawable.album_default);
        }
        bitmap = BlurImage.blur(this, bitmap);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);

        layoutPlayMusic.setBackground(bitmapDrawable);

        Animation rotateAnim = AnimationUtils.loadAnimation(PlayMusicActivity.this, R.anim.rotate_iv_playing);
        ivPlaying.startAnimation(rotateAnim);

        tvTotalTime.setText(ConvertTime.miniSecondToString(totalTimeSong));
    }

    @Override
    public void updateMainActivity() {
        Intent intent = new Intent(ConfigAction.ACTION_UPDATE_PlAY_STATUS);
        sendBroadcast(intent);
    }

    @Override
    public void updatePlayPause() {
        if (musicService != null) {
            if (musicService.isPlaying()) {
                ivPlayPause.setImageResource(R.drawable.ic_pause);
            } else {
                ivPlayPause.setImageResource(R.drawable.ic_play);
            }
        }
    }

    @Override
    public void updateShuffle() {
        if (musicService.isShuffle()) {
            ivShuffle.setImageResource(R.drawable.ic_shuffle_on);
        } else {
            ivShuffle.setImageResource(R.drawable.ic_shuffle_off);
        }
    }

    @Override
    public void updateRepeat() {
        if (musicService.isRepeat()) {
            ivRepeat.setImageResource(R.drawable.ic_repeat_on);
        } else {
            ivRepeat.setImageResource(R.drawable.ic_repeat_off);
        }
    }

    @Override
    public void updateSeekBar() {
        seekBarPlaying.setMax(totalTimeSong);
        int currentLength = musicService.getCurrentLength();

        if (!isSeeking) {
            seekBarPlaying.setProgress(currentLength);
            tvTimePlayed.setText(ConvertTime.miniSecondToString(currentLength));
        }

        tvTotalTime.setText(ConvertTime.miniSecondToString(totalTimeSong));
        Handler musicHandler = new Handler();
        musicHandler.post(new Runnable() {
            @Override
            public void run() {
                musicPresenter.updateSeekBar();
            }
        });
    }

    @Override
    public void playMusic() {
        musicService.play(path);
        totalTimeSong = musicService.getTotalTime();
        Song song = listSong.get(currentPosition);
        musicService.setData(listSong, currentPosition, song);

        Intent openService = new Intent(PlayMusicActivity.this, MusicService.class);
        startService(openService);
        musicPresenter.updateData();
        musicService.showNotification(true);
        musicPresenter.updateMainActivity();
    }

    @Override
    public void playPauseMusic() {
        if (musicService.isPlaying()) {
            ivPlayPause.setImageResource(R.drawable.ic_play);
            musicService.pause();
        } else {
            ivPlayPause.setImageResource(R.drawable.ic_pause);
            musicService.resume();
        }
        musicPresenter.updateMainActivity();
    }

    @Override
    public void next() {
        if (!musicService.isRepeat()) {
            currentPosition = musicService.getNextPosition();
            path = listSong.get(currentPosition).getPath();
        }

        musicPresenter.playMusic();
    }

    @Override
    public void back() {
        currentPosition = musicService.getPreviousPosition();
        path = listSong.get(currentPosition).getPath();
        musicPresenter.playMusic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_play_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
                this.overridePendingTransition(R.anim.no_change, R.anim.slide_down);
                break;
            }

            case R.id.icon_share: {
                showLayoutShare();
                break;
            }
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shuffle: {
                if (musicService == null) {
                    return;
                }
                if (musicService.isShuffle()) {
                    ivShuffle.setImageResource(R.drawable.ic_shuffle_off);
                    musicService.setShuffle(false);
                } else {
                    ivShuffle.setImageResource(R.drawable.ic_shuffle_on);
                    musicService.setShuffle(true);
                }
                break;
            }
            case R.id.iv_previous: {
                musicPresenter.previousMusic();
                ivPlayPause.setImageResource(R.drawable.ic_pause);
                musicService.showNotification(true);
                break;
            }
            case R.id.iv_pause_play: {
                musicPresenter.playPauseMusic();
                musicService.showNotification(true);
                break;
            }

            case R.id.iv_next: {
                musicPresenter.nextMusic();
                ivPlayPause.setImageResource(R.drawable.ic_pause);
                musicService.showNotification(true);
                break;
            }

            case R.id.iv_repeat: {
                if (musicService.isRepeat()) {
                    ivRepeat.setImageResource(R.drawable.ic_repeat_off);
                    musicService.setRepeat(false);
                } else {
                    ivRepeat.setImageResource(R.drawable.ic_repeat_on);
                    musicService.setRepeat(true);
                }
                break;
            }
        }
    }

    private void showLayoutShare() {
        FragmentShare frgShare = new FragmentShare();
        frgShare.show(getSupportFragmentManager(), frgShare.getTag());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.no_change, R.anim.slide_down);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBroadcastSongComplete();
        AppController.getInstance().setPlayMusicActivity(null);
    }
}
