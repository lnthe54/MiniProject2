package com.example.lnthe54.miniproject2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.view.fragment.FragmentListSongPlaying;
import com.example.lnthe54.miniproject2.view.fragment.FragmentPlayMusic;

import java.util.ArrayList;

/**
 * @author lnthe54 on 10/17/2018
 * @project MiniProject2
 */
public class PlayMusicPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Song> listSongPlaying;
    private String bgAlbum;

    public PlayMusicPagerAdapter(FragmentManager fm, ArrayList<Song> listSongPlaying, String bgAlbum) {
        super(fm);
        this.listSongPlaying = listSongPlaying;
        this.bgAlbum = bgAlbum;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return FragmentListSongPlaying.getInstance(listSongPlaying);

            }
            case 1: {
                return FragmentPlayMusic.getInstance(bgAlbum);
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
