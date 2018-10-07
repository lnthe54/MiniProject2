package com.example.lnthe54.miniproject2.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.utils.Config;

/**
 * @author lnthe54 on 10/7/2018
 * @project MiniProject2
 */
public class FragmentMoreMusic extends BottomSheetDialogFragment {

    private View view;
    private ImageView ivSong;
    private TextView tvNameSong;
    private TextView tvNameArtist;

    private String imageSong;
    private String nameSong;
    private String nameArtist;

    public FragmentMoreMusic() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_song_more, container, false);
        getData();
        initViews(view);
        addEvents();
        return view;
    }

    private void getData() {
        imageSong = getArguments().getString(Config.IMAGE);
        nameSong = getArguments().getString(Config.NAME_SONG);
        nameArtist = getArguments().getString(Config.NAME_ARTIST);
    }

    private void initViews(View view) {
        ivSong = view.findViewById(R.id.iv_song);
        tvNameSong = view.findViewById(R.id.tv_name_song);
        tvNameArtist = view.findViewById(R.id.tv_artist_song);
    }

    private void addEvents() {
        if (imageSong != null) {
            Glide.with(getContext()).load(imageSong).into(ivSong);
        } else {
            ivSong.setImageResource(R.drawable.album_default);
        }

        tvNameSong.setText(nameSong);
        tvNameArtist.setText(nameArtist);
    }
}
