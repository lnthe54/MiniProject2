package com.example.lnthe54.miniproject2.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.adapter.SongAdapter;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.ultis.Config;
import com.example.lnthe54.miniproject2.ultis.ConvertTime;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/30/2018
 * @project MiniProject2
 */
public class DetailAlbumActivity extends AppCompatActivity {
    private static final String TAG = "DetailAlbumActivity";
    private android.support.v7.widget.Toolbar toolbar;
    private RoundedImageView ivAlbum;
    private ImageView ivBg;
    private TextView tvNameAlbum;
    private TextView tvNameArtist;
    private TextView tvDurationTotal;
    private TextView tvShuffle;

    private ArrayList<Song> listSong;
    private RecyclerView rvListSong;
    private SongAdapter songAdapter;

    private int idAlbum;
    private String nameAlbum;
    private String nameArtist;
    private String artAlbum;
    private int totalTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_albums);

        initViews();
        showData();
    }

    private void initViews() {
        Intent intent = getIntent();
        idAlbum = intent.getIntExtra(Config.ID_ALBUM, 0);
        nameAlbum = intent.getStringExtra(Config.NAME_ALBUM);
        nameArtist = intent.getStringExtra(Config.NAME_ARTIST);
        artAlbum = intent.getStringExtra(Config.IMAGE);

        toolbar = findViewById(R.id.toolbar);

        setTitle(nameAlbum);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivAlbum = findViewById(R.id.iv_album);
        ivBg = findViewById(R.id.iv_album_bg);
        tvNameAlbum = findViewById(R.id.tv_name_album);
        tvNameArtist = findViewById(R.id.tv_name_artist);
        tvDurationTotal = findViewById(R.id.tv_duration_total);
        tvShuffle = findViewById(R.id.tv_shuffle);

        Glide.with(this).load(artAlbum).into(ivAlbum);
        Glide.with(this).load(artAlbum).into(ivBg);
        tvNameAlbum.setText(nameAlbum);
        tvNameArtist.setText(nameArtist);

        rvListSong = findViewById(R.id.list_song_of_album);
        rvListSong.setLayoutManager(new LinearLayoutManager(DetailAlbumActivity.this, LinearLayoutManager.VERTICAL, false));
        rvListSong.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showData() {
        listSong = new ArrayList<>();
        getListSongOfAlbum(idAlbum);
        getTimeOfListSong();
        tvDurationTotal.setText(listSong.size() + " bài hát\t|\t" + ConvertTime.miniSecondToString(totalTime));
        songAdapter = new SongAdapter(listSong);
        rvListSong.setAdapter(songAdapter);
    }

    public void getListSongOfAlbum(int idAlbum) {
        Uri albumUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(albumUri,
                new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATA},
                MediaStore.Audio.Media.ALBUM_ID + "=?",
                new String[]{String.valueOf(idAlbum)}, MediaStore.Audio.Media.TITLE + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                int songId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                Song song = new Song(songId, title, artist, album, duration, path);
                listSong.add(song);

            } while (cursor.moveToNext());
        }
    }

    public void getTimeOfListSong() {
        for (int i = 0; i < listSong.size(); i++) {
            totalTime += (listSong.get(i).getDuration() / 1000);
        }
    }
}
