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

/**
 * @author lnthe54 on 10/8/2018
 * @project MiniProject2
 */
public class FragmentListSongPlaying extends Fragment {

    private View view;
    private RecyclerView rvListSong;

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
    }
}
