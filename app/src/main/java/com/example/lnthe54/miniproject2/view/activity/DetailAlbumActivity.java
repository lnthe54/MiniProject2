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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.adapter.SongOfAlbumAdapter;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.presenter.DetailAlbumPresenter;
import com.example.lnthe54.miniproject2.utils.Config;
import com.example.lnthe54.miniproject2.utils.ConvertTime;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/30/2018
 * @project MiniProject2
 */
public class DetailAlbumActivity extends AppCompatActivity
        implements SongOfAlbumAdapter.CallBack, View.OnClickListener, DetailAlbumPresenter.CallBack {
    private android.support.v7.widget.Toolbar toolbar;
    private RoundedImageView ivAlbum;
    private ImageView ivBg;
    private TextView tvNameAlbum;
    private TextView tvNameArtist;
    private TextView tvDurationTotal;
    private TextView tvShuffle;

    private ArrayList<Song> listSong;
    private ArrayList<Song> listSongSend;
    private RecyclerView rvListSong;
    private SongOfAlbumAdapter songAdapter;

    private int idAlbum;
    private String nameAlbum;
    private String nameArtist;
    private String artAlbum;
    private int numberOfSong;
    private int totalTime;

    private DetailAlbumPresenter detailAlbumPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_albums);

        detailAlbumPresenter = new DetailAlbumPresenter(this);
        getDataFromIntent();
        initViews();
        addEvents();
        showData();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        idAlbum = intent.getIntExtra(Config.ID_ALBUM, 0);
        nameAlbum = intent.getStringExtra(Config.NAME_ALBUM);
        nameArtist = intent.getStringExtra(Config.NAME_ARTIST);
        artAlbum = intent.getStringExtra(Config.IMAGE);
        numberOfSong = intent.getIntExtra(Config.NUMBER_SONG, 0);
    }

    private void initViews() {
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

        rvListSong = findViewById(R.id.list_song_of_album);
        rvListSong.setLayoutManager(new LinearLayoutManager(DetailAlbumActivity.this, LinearLayoutManager.VERTICAL, false));
        rvListSong.setHasFixedSize(true);
    }

    private void addEvents() {
        detailAlbumPresenter.updateData();
        tvShuffle.setOnClickListener(this);
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
                this.overridePendingTransition(R.anim.no_change, R.anim.slide_left);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showData() {
        listSong = new ArrayList<>();
        detailAlbumPresenter.getListSongOfAlbum(idAlbum);
        detailAlbumPresenter.getTimeOfListSong();
        tvDurationTotal.setText(numberOfSong + " bài hát\t|\t" + ConvertTime.miniSecondToString(totalTime));
        songAdapter = new SongOfAlbumAdapter(this, listSong);
        rvListSong.setAdapter(songAdapter);
    }

    @Override
    public void updateData() {
        if (artAlbum != null) {
            Glide.with(this).load(artAlbum).into(ivAlbum);
            Glide.with(this).load(artAlbum).into(ivBg);
        } else {
            ivBg.setImageResource(R.drawable.ic_album_default);
            ivAlbum.setImageResource(R.drawable.ic_album_default);
        }

        tvNameAlbum.setText(nameAlbum);
        tvNameArtist.setText(nameArtist);
    }

    @Override
    public void getListSongOfAlbum(long idAlbum) {
        Uri albumUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(albumUri,
                new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.ALBUM_ID},
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
                long albumID = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String artSong = detailAlbumPresenter.getImageAlbum(albumID);
                Song song = new Song(songId, title, artist, album, artSong, duration, path);
                listSong.add(song);

            } while (cursor.moveToNext());
        }
    }

    @Override
    public String getImageAlbum(long albumId) {
        Cursor albumCursor = getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{Long.toString(albumId)},
                null
        );
        boolean queryResult = albumCursor.moveToFirst();
        String result = null;
        if (queryResult) {
            result = albumCursor.getString(0);
        }
        albumCursor.close();
        return result;
    }

    @Override
    public void getTimeOfListSong() {
        for (int i = 0; i < listSong.size(); i++) {
            totalTime += (listSong.get(i).getDuration() / 1000);
        }
    }

    @Override
    public void itemClick(int position) {
        detailAlbumPresenter.openPlayMusicActivity(position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shuffle: {
                handlingShuffle();
            }
        }
    }

    @Override
    public void openPlayMusicActivity(int position) {
        Intent openPlayMusicActivity = new Intent(DetailAlbumActivity.this, PlayMusicActivity.class);

        listSongSend = (ArrayList<Song>) listSong.clone();
        int songPosition = position;

        openPlayMusicActivity.putExtra(Config.SONG_POSITION, songPosition);
        openPlayMusicActivity.putExtra(Config.IS_PLAYING, false);
        openPlayMusicActivity.putExtra(Config.PATH, listSong.get(position).getPath());
        openPlayMusicActivity.putExtra(Config.LIST_SONG, listSongSend);

        startActivity(openPlayMusicActivity);
        this.overridePendingTransition(R.anim.slide_up, R.anim.no_change);
    }

    private void handlingShuffle() {

    }
}
