package com.example.lnthe54.miniproject2.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.adapter.AlbumAdapter;
import com.example.lnthe54.miniproject2.adapter.SongOfAlbumAdapter;
import com.example.lnthe54.miniproject2.model.Albums;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.presenter.DetailArtistPresenter;
import com.example.lnthe54.miniproject2.utils.Config;

import java.util.ArrayList;

/**
 * @author lnthe54 on 10/1/2018
 * @project MiniProject2
 */
public class DetailArtistActivity extends AppCompatActivity
        implements AlbumAdapter.CallBack, SongOfAlbumAdapter.CallBack, DetailArtistPresenter.CallBack {
    private static final int SPAN_COUNT = 3;
    private static final String TAG = "DetailArtistActivity";
    private android.support.v7.widget.Toolbar toolbar;
    private TextView tvNameArtist;
    private TextView tvNumberOfAlbum;
    private TextView tvNumberOfSong;

    private String nameArtist;
    private int numberOfSong;
    private int numberOfAlbum;
    private int artistID;

    private AlbumAdapter albumAdapter;
    private ArrayList<Albums> listAlbum;
    private RecyclerView rvListAlbum;

    private ArrayList<Song> listSong;
    private ArrayList<Song> listSongSend;
    private RecyclerView rvListSong;
    private SongOfAlbumAdapter songAdapter;

    private DetailArtistPresenter detailArtistPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artists);

        detailArtistPresenter = new DetailArtistPresenter(this);
        getDataFromIntent();
        initViews();
        addEvents();
        showData();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();

        artistID = intent.getIntExtra(Config.ID_ARTIST, 0);
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

        rvListAlbum = findViewById(R.id.list_album_of_artist);
        rvListAlbum.setLayoutManager(new GridLayoutManager(DetailArtistActivity.this, SPAN_COUNT));
        rvListAlbum.setHasFixedSize(true);

        rvListSong = findViewById(R.id.list_song_of_artist);
        rvListSong.setLayoutManager(new LinearLayoutManager(DetailArtistActivity.this, LinearLayoutManager.VERTICAL, false));
        rvListSong.setHasFixedSize(true);
    }

    private void addEvents() {
        tvNameArtist.setText(nameArtist);
        tvNumberOfAlbum.setText(numberOfAlbum + " album");
        tvNumberOfSong.setText(numberOfSong + " bài hát");
    }

    private void showData() {
        listAlbum = new ArrayList<>();
        detailArtistPresenter.getListAlbumOfArtist(artistID);
        albumAdapter = new AlbumAdapter(this, listAlbum);
        rvListAlbum.setAdapter(albumAdapter);

        listSong = new ArrayList<>();
        detailArtistPresenter.getListSongOfArtist(artistID);
        songAdapter = new SongOfAlbumAdapter(this, listSong);
        rvListSong.setAdapter(songAdapter);
    }

    @Override
    public void getListAlbumOfArtist(int artistID) {
        Uri album = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(album,
                new String[]{MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART,
                        MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ARTIST,
                        MediaStore.Audio.Albums.NUMBER_OF_SONGS},
                MediaStore.Audio.Media.ARTIST_ID + "=?", new String[]{String.valueOf(artistID)},
                MediaStore.Audio.Albums.ALBUM + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String nameAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String artistAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                int numberSong = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                Albums albums = new Albums(id, nameAlbum, artistAlbum, albumArt, numberSong);
                listAlbum.add(albums);
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void getListSongOfArtist(int artistID) {
        Uri albumUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(albumUri,
                new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.ALBUM_ID},
                MediaStore.Audio.Media.ARTIST_ID + "=?",
                new String[]{String.valueOf(artistID)}, MediaStore.Audio.Media.TITLE + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                int songId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                long albumID = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String imageAlbum = detailArtistPresenter.getImageAlbum(albumID);
                Song song = new Song(songId, title, artist, album, imageAlbum, duration, path);
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

    @Override
    public void itemClickGrid(int position) {
        detailArtistPresenter.openDetailAlbumActivity(position);
    }

    @Override
    public void itemClick(int position) {
        detailArtistPresenter.openPlayMusicActivity(position);
    }

    @Override
    public void openDetailAlbumActivity(int position) {
        Intent openDetailAlbum = new Intent(DetailArtistActivity.this, DetailAlbumActivity.class);

        int idAlbum = listAlbum.get(position).getId();
        String nameAlbum = listAlbum.get(position).getNameAlbum();
        String nameArtist = listAlbum.get(position).getAuthorAlbum();
        String path = listAlbum.get(position).getAlbumArt();
        int numberOfSong = listAlbum.get(position).getNumberSong();

        openDetailAlbum.putExtra(Config.ID_ALBUM, idAlbum);
        openDetailAlbum.putExtra(Config.NAME_ALBUM, nameAlbum);
        openDetailAlbum.putExtra(Config.NAME_ARTIST, nameArtist);
        openDetailAlbum.putExtra(Config.IMAGE, path);
        openDetailAlbum.putExtra(Config.NUMBER_SONG, numberOfSong);

        startActivity(openDetailAlbum);
    }

    @Override
    public void openPlayMusicActivity(int position) {
        Intent openPlayMusicActivity = new Intent(DetailArtistActivity.this, PlayMusicActivity.class);

        listSongSend = (ArrayList<Song>) listSong.clone();
        int songPosition = position;

        openPlayMusicActivity.putExtra(Config.SONG_POSITION, songPosition);
        openPlayMusicActivity.putExtra(Config.IS_PLAYING, false);
        openPlayMusicActivity.putExtra(Config.PATH, listSong.get(position).getPath());
        openPlayMusicActivity.putExtra(Config.LIST_SONG, listSongSend);

        startActivity(openPlayMusicActivity);
        this.overridePendingTransition(R.anim.slide_up, R.anim.no_change);
    }
}
