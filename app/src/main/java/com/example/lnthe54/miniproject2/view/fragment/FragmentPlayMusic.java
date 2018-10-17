package com.example.lnthe54.miniproject2.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.utils.Config;
import com.example.lnthe54.miniproject2.utils.ConfigAction;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author lnthe54 on 10/17/2018
 * @project MiniProject2
 */
public class FragmentPlayMusic extends Fragment {

    public static FragmentPlayMusic getInstance(String artSong) {
        FragmentPlayMusic frgPlayMusic = new FragmentPlayMusic();
        Bundle args = new Bundle();
        args.putString(Config.IMAGE, artSong);
        frgPlayMusic.setArguments(args);
        return frgPlayMusic;
    }

    private View view;
    private CircleImageView ivPlaying;


    private String artSong;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcast();
        if (getArguments() != null) {
            artSong = getArguments().getString(Config.IMAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_playing_music, container, false);
        initViews(view);
        addEvents();
        return view;
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            artSong = intent.getStringExtra(Config.IMAGE);
            addEvents();
        }
    };

    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConfigAction.ACTION_CHANGE_IMAGE);
        getContext().registerReceiver(receiver, intentFilter);
    }

    private void unregisterBroadcast() {
        getContext().unregisterReceiver(receiver);
    }

    private void initViews(View view) {
        ivPlaying = view.findViewById(R.id.iv_playing);
    }

    private void addEvents() {
        if (artSong != null) {
            Glide.with(getContext()).load(artSong).into(ivPlaying);
        } else {
            ivPlaying.setImageResource(R.drawable.album_default);
        }
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_iv_playing);
        ivPlaying.startAnimation(rotate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }
}
