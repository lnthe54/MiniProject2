package com.example.lnthe54.miniproject2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.model.Artist;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/29/2018
 * @project MiniProject2
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private static final String SONGS = " bài hát";
    private static final String ALBUMS = " album";

    private ArrayList<Artist> listArtist;

    public ArtistsAdapter(ArrayList<Artist> listArtist) {
        this.listArtist = listArtist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artist artist = listArtist.get(position);
        holder.bindData(artist);
    }

    @Override
    public int getItemCount() {
        return listArtist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivArtist;
        private TextView tvNameArtist;
        private TextView tvNumberSong;
        private TextView tvNumberAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivArtist = itemView.findViewById(R.id.iv_artist);
            tvNameArtist = itemView.findViewById(R.id.tv_name_artist);
            tvNumberAlbum = itemView.findViewById(R.id.tv_number_album);
            tvNumberSong = itemView.findViewById(R.id.tv_number_song);
        }

        private void bindData(Artist artist) {
            tvNameArtist.setText(artist.getNameArtist());
            tvNumberSong.setText(artist.getNumberSong() + SONGS);
            tvNumberAlbum.setText(artist.getNumberAlbums() + ALBUMS);
        }
    }
}
