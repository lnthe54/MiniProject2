package com.example.lnthe54.miniproject2.view.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.adapter.AlbumAdapter;
import com.example.lnthe54.miniproject2.model.Albums;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class FragmentAlbums extends Fragment {
    private static FragmentAlbums instance;
    private static final int SPAN_COUNT = 3;
    private RecyclerView rvListAlbum;
    private AlbumAdapter albumAdapter;
    private ArrayList<Albums> listAlbum;

    public static FragmentAlbums getInstance() {
        if (instance == null) {
            instance = new FragmentAlbums();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        initViews(view);
        showListAlbum();
        return view;
    }

    private void initViews(View view) {
        rvListAlbum = view.findViewById(R.id.list_album);
        rvListAlbum.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        rvListAlbum.setHasFixedSize(true);
    }

    private void showListAlbum() {
        listAlbum = new ArrayList<>();
        getAlbumToStorage();

        albumAdapter = new AlbumAdapter(listAlbum);
        rvListAlbum.setAdapter(albumAdapter);
    }

    private void getAlbumToStorage() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri album = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(album,
                new String[]{MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART,
                        MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ARTIST},
                null, null, MediaStore.Audio.Albums.ALBUM + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String nameAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String artistAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

                Albums albums = new Albums(id, nameAlbum, artistAlbum, albumArt);
                listAlbum.add(albums);
            } while (cursor.moveToNext());
        }
    }
}