package com.example.lnthe54.miniproject2.view.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.adapter.SongAdapter;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.ultis.Config;
import com.example.lnthe54.miniproject2.view.activity.PlayMusicActivity;

import java.util.ArrayList;


/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class FragmentSong extends Fragment implements SearchView.OnQueryTextListener, SongAdapter.CallBack {
    private static final String TAG = "FragmentSong";
    private static FragmentSong instance;

    private View view;
    private RecyclerView rvListSong;
    private ArrayList<Song> listSong;
    private ArrayList<Song> listSongSend;
    private SongAdapter songAdapter;

    public static FragmentSong getInstance() {
        if (instance == null) {
            instance = new FragmentSong();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_song, container, false);
        setHasOptionsMenu(true);

        initView(view);
        showListSong();
        return view;
    }

    private void initView(View view) {
        rvListSong = view.findViewById(R.id.list_song);
        rvListSong.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        rvListSong.addItemDecoration(divider);
        rvListSong.setHasFixedSize(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_main, menu);
        MenuItem item = menu.findItem(R.id.icon_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showListSong() {
        listSong = new ArrayList<>();
        getSongToStorage();
        songAdapter = new SongAdapter(this, listSong);
        rvListSong.setAdapter(songAdapter);
    }

    private void getSongToStorage() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri song = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(song,
                new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID},
                null, null, MediaStore.Audio.Media.TITLE);

        if (song != null && songCursor.moveToFirst()) {
            do {
                int currentId = songCursor.getInt(songCursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String currentTitle = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String currentArtists = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String currentAlbum = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                int currentDuration = songCursor.getInt(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String albumPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                long albumID = songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String albumArt = getCoverArtPath(albumID);

                listSong.add(new Song(currentId, currentTitle, currentArtists, currentAlbum, albumArt, currentDuration, albumPath));

            } while (songCursor.moveToNext());
        }
    }

    private String getCoverArtPath(long albumId) {
        Cursor albumCursor = getContext().getContentResolver().query(
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<Song> newList = new ArrayList<>();
        for (Song songs : listSong) {
            if (songs.getNameSong().toLowerCase().contains(userInput)) {
                newList.add(songs);
            }
        }

        songAdapter.updateList(newList);
        return true;
    }

    @Override
    public void itemClick(int position) {
        openPlayMusicActivity(position);
    }

    private void openPlayMusicActivity(int position) {
        Intent openPlayMusicActivity = new Intent(getContext(), PlayMusicActivity.class);

        listSongSend = (ArrayList<Song>) listSong.clone();
        int songPosition = position;

        openPlayMusicActivity.putExtra(Config.SONG_POSITION, songPosition);
        openPlayMusicActivity.putExtra(Config.IS_PLAYING, false);
        openPlayMusicActivity.putExtra(Config.PATH, listSong.get(position).getPath());
        openPlayMusicActivity.putExtra(Config.LIST_SONG, listSongSend);

        startActivity(openPlayMusicActivity);
    }

}
