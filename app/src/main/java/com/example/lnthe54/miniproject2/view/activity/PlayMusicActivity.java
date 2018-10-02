package com.example.lnthe54.miniproject2.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.service.MusicService;
import com.example.lnthe54.miniproject2.ultis.Config;
import com.example.lnthe54.miniproject2.ultis.ConvertTime;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author lnthe54 on 10/2/2018
 * @project MiniProject2
 */
public class PlayMusicActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView ivPlaying;
    private SeekBar seekBarPlaying;
    private TextView tvTotalTime;
    private TextView tvTimePlayed;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        musicService = new MusicService();
        getDataFromIntent();
        initViews();

        if (musicService == null) {

        } else {

            if (!isPlaying) {
                playMusic();
            }

            updateSeekBar();
            totalTimeSong = musicService.getTotalTime();
        }
        addEvents();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        isPlaying = intent.getExtras().getBoolean(Config.IS_PLAYING);

        if (isPlaying) {
            path = musicService.getCurrentSong().getPath();
            currentPosition = musicService.getCurrentSongPosition();
            listSong = musicService.getListSong();

        } else {
            currentPosition = intent.getIntExtra(Config.SONG_POSITION, 0);
            path = intent.getStringExtra(Config.PATH);
            listSong = (ArrayList<Song>) intent.getSerializableExtra(Config.LIST_SONG);
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);

        ivPlaying = findViewById(R.id.iv_playing);
        tvTimePlayed = findViewById(R.id.tv_time_played);
        tvTotalTime = findViewById(R.id.tv_time_total);

        seekBarPlaying = findViewById(R.id.seek_bar_playing);

        isSeeking = false;
    }

    private void addEvents() {
        nameSong = listSong.get(currentPosition).getNameSong();
        nameArtist = listSong.get(currentPosition).getArtistSong();
        artSong = listSong.get(currentPosition).getAlbumImage();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(nameSong);
        getSupportActionBar().setSubtitle(nameArtist);

        Glide.with(PlayMusicActivity.this).load(artSong).into(ivPlaying);
        Animation rotateAnim = AnimationUtils.loadAnimation(PlayMusicActivity.this, R.anim.rotate_iv_playing);
        ivPlaying.startAnimation(rotateAnim);

        tvTotalTime.setText(ConvertTime.miniSecondToString(totalTimeSong));

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
                    //change image Pause/Play button
                }

                isSeeking = false;
                updateSeekBar();
            }
        });
    }

    private void updateSeekBar() {
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
                updateSeekBar();
            }
        });
    }

    public void playMusic() {
        musicService.play(path);
        totalTimeSong = musicService.getTotalTime();
        Song song = listSong.get(currentPosition);
        musicService.setData(listSong, currentPosition, song);

        Intent openService = new Intent(PlayMusicActivity.this, MusicService.class);
        startService(openService);
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
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
