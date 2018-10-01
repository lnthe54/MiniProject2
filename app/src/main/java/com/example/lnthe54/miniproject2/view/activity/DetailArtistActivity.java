package com.example.lnthe54.miniproject2.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.ultis.Config;

/**
 * @author lnthe54 on 10/1/2018
 * @project MiniProject2
 */
public class DetailArtistActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private TextView tvNameArtist;
    private TextView tvNumberOfAlbum;
    private TextView tvNumberOfSong;

    private String nameArtist;
    private int numberOfSong;
    private int numberOfAlbum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artists);

        getDataFromIntent();
        initViews();
        addEvents();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();

        nameArtist = intent.getStringExtra(Config.NAME_ARTIST);
        numberOfAlbum = intent.getIntExtra(Config.NUMBER_ALBUM, 0);
        numberOfSong = intent.getIntExtra(Config.NUMBER_SONG, 0);
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setTitle(nameArtist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvNameArtist = findViewById(R.id.tv_name_artist);
        tvNumberOfAlbum = findViewById(R.id.tv_number_album);
        tvNumberOfSong = findViewById(R.id.tv_number_song);
    }

    private void addEvents() {
        tvNameArtist.setText(nameArtist);
        tvNumberOfAlbum.setText(numberOfAlbum + " album");
        tvNumberOfSong.setText(numberOfSong + " bài hát");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
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
