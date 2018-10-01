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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.adapter.AlbumAdapter;
import com.example.lnthe54.miniproject2.adapter.ListAlbumAdapter;
import com.example.lnthe54.miniproject2.model.Albums;
import com.example.lnthe54.miniproject2.ultis.Config;
import com.example.lnthe54.miniproject2.view.activity.DetailAlbumActivity;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class FragmentAlbums extends Fragment implements AlbumAdapter.CallBack, ListAlbumAdapter.CallBack {
    private static FragmentAlbums instance;
    private static final int SPAN_COUNT = 3;
    private RecyclerView rvListAlbum;
    private AlbumAdapter albumAdapter;
    private ListAlbumAdapter listAlbumAdapter;
    private ArrayList<Albums> listAlbum;
    private boolean isGrid = true;

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
        setHasOptionsMenu(true);
        initViews(view);
        showListAlbum();
        return view;
    }

    private void initViews(View view) {
        rvListAlbum = view.findViewById(R.id.list_album);
        rvListAlbum.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        rvListAlbum.setHasFixedSize(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_album, menu);
        MenuItem itemConvert = menu.findItem(R.id.icon_convert);
        if (isGrid) {
            itemConvert.setIcon(R.drawable.ic_list);
        } else {
            itemConvert.setIcon(R.drawable.ic_grid);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_convert: {
                if (isGrid) {
                    convertListItem();
                } else {
                    convertGridItem();
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void convertListItem() {
        rvListAlbum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvListAlbum.setHasFixedSize(true);
        listAlbumAdapter = new ListAlbumAdapter(this, listAlbum);
        rvListAlbum.setAdapter(listAlbumAdapter);
        isGrid = false;
    }

    private void convertGridItem() {
        rvListAlbum.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        rvListAlbum.setHasFixedSize(true);
        albumAdapter = new AlbumAdapter(this, listAlbum);
        rvListAlbum.setAdapter(albumAdapter);
        isGrid = true;
    }

    private void showListAlbum() {
        listAlbum = new ArrayList<>();
        getAlbumToStorage();
        albumAdapter = new AlbumAdapter(this, listAlbum);
        rvListAlbum.setAdapter(albumAdapter);
    }

    private void getAlbumToStorage() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri album = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(album,
                new String[]{MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART,
                        MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ARTIST,
                        MediaStore.Audio.Albums.NUMBER_OF_SONGS},
                null, null, MediaStore.Audio.Albums.ALBUM + " ASC");

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
    public void itemClickGrid(int position) {
        openDetailAlbum(position);
    }

    @Override
    public void itemClickList(int position) {
        openDetailAlbum(position);
    }

    private void openDetailAlbum(int position) {
        Intent openDetailAlbum = new Intent(getContext(), DetailAlbumActivity.class);

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

}
