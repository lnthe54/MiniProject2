package com.example.lnthe54.miniproject2.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.adapter.SongOfAlbumAdapter;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.utils.Config;

import java.util.ArrayList;

/**
 * @author lnthe54 on 10/8/2018
 * @project MiniProject2
 */
public class FragmentListSongPlaying extends Fragment implements SongOfAlbumAdapter.CallBack {

    public static FragmentListSongPlaying getInstance(ArrayList<Song> listSong) {

        FragmentListSongPlaying fragment = new FragmentListSongPlaying();
        Bundle args = new Bundle();
        args.putSerializable(Config.LIST_SONG, listSong);
        fragment.setArguments(args);
        return fragment;
    }

    private View view;
    private RecyclerView rvListSong;
    private ArrayList<Song> listSong;
    private SongOfAlbumAdapter songAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listSong = (ArrayList<Song>) getArguments().getSerializable(Config.LIST_SONG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_list_song_playing, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        rvListSong = view.findViewById(R.id.list_song_playing);
        rvListSong.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvListSong.setHasFixedSize(true);
        addEvents();
    }

    private void addEvents() {
        songAdapter = new SongOfAlbumAdapter(this, listSong);
        rvListSong.setAdapter(songAdapter);
    }

    @Override
    public void itemClick(int position) {

    }
}
