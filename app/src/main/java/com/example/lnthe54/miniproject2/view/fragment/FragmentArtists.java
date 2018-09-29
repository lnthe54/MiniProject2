package com.example.lnthe54.miniproject2.view.fragment;

import android.content.ContentResolver;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.adapter.ArtistsAdapter;
import com.example.lnthe54.miniproject2.model.Artist;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class FragmentArtists extends Fragment {
    private static FragmentArtists instance;
    private RecyclerView rvListArtist;
    private ArrayList<Artist> listArtist;
    private ArtistsAdapter artistsAdapter;

    public static FragmentArtists getInstance() {
        if (instance == null) {
            instance = new FragmentArtists();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        initViews(view);
        showListArtist();
        return view;
    }

    private void initViews(View view) {
        rvListArtist = view.findViewById(R.id.list_artist);
        rvListArtist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        rvListArtist.addItemDecoration(divider);
        rvListArtist.setHasFixedSize(true);
    }

    private void showListArtist() {
        listArtist = new ArrayList<>();
        getListArtistToStorage();
        artistsAdapter = new ArtistsAdapter(listArtist);
        rvListArtist.setAdapter(artistsAdapter);
    }

    private void getListArtistToStorage() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri artists = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(artists,
                new String[]{MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS, MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS},
                null, null, MediaStore.Audio.Artists.ARTIST + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String nameArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                int numberSongs = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS));
                int numberAlbums = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS));
                Artist artist = new Artist(id, nameArtist, numberSongs, numberAlbums);
                listArtist.add(artist);
            } while (cursor.moveToNext());
        }
    }
}
