package com.example.lnthe54.miniproject2.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lnthe54.miniproject2.view.fragment.FragmentAlbums;
import com.example.lnthe54.miniproject2.view.fragment.FragmentArtists;
import com.example.lnthe54.miniproject2.view.fragment.FragmentSong;

/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private static final String SONG = "BÀI HÁT";
    private static final String ALBUMS = "ALBUM";
    private static final String ARTISTS = "ARTISTS";

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return FragmentSong.getInstance();
            }

            case 1: {
                return FragmentAlbums.getInstance();
            }

            case 2: {
                return FragmentArtists.getInstance();
            }

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return SONG;
            }
            case 1: {
                return ALBUMS;
            }
            case 2: {
                return ARTISTS;
            }
        }
        return super.getPageTitle(position);
    }
}
