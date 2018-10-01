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
import com.example.lnthe54.miniproject2.adapter.ArtistsAdapter;
import com.example.lnthe54.miniproject2.model.Artist;
import com.example.lnthe54.miniproject2.ultis.Config;
import com.example.lnthe54.miniproject2.view.activity.DetailArtistActivity;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class FragmentArtists extends Fragment implements SearchView.OnQueryTextListener, ArtistsAdapter.CallBack {
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
        setHasOptionsMenu(true);
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
        artistsAdapter = new ArtistsAdapter(this, listArtist);
        rvListArtist.setAdapter(artistsAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_album, menu);
        MenuItem item = menu.findItem(R.id.icon_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<Artist> newList = new ArrayList<>();
        for (Artist artist : listArtist) {
            if (artist.getNameArtist().toLowerCase().contains(userInput)) {
                newList.add(artist);
            }
        }

        artistsAdapter.updateList(newList);
        return true;
    }

    @Override
    public void itemClick(int position) {
        openDetailArtist(position);
    }

    private void openDetailArtist(int position) {
        Intent openDetailArtist = new Intent(getContext(), DetailArtistActivity.class);

        String nameArtist = listArtist.get(position).getNameArtist();
        int numberOfAlbum = listArtist.get(position).getNumberAlbums();
        int numberOfSong = listArtist.get(position).getNumberSong();

        openDetailArtist.putExtra(Config.NAME_ARTIST, nameArtist);
        openDetailArtist.putExtra(Config.NUMBER_ALBUM, numberOfAlbum);
        openDetailArtist.putExtra(Config.NUMBER_SONG, numberOfSong);

        startActivity(openDetailArtist);
    }
}
