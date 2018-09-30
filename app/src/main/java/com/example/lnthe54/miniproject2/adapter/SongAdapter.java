package com.example.lnthe54.miniproject2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.model.Song;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private static final String TAG = "SongAdapter";
    private ArrayList<Song> listSong;

    public SongAdapter(ArrayList<Song> listSong) {
        this.listSong = listSong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = listSong.get(position);
        holder.bindData(song);
    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameSong;
        private TextView tvArtistSong;
        private CircleImageView ivSong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivSong = itemView.findViewById(R.id.iv_song);
            tvNameSong = itemView.findViewById(R.id.tv_name_song);
            tvArtistSong = itemView.findViewById(R.id.tv_artist_song);
        }

        public void bindData(Song song) {
            String albumArt = song.getAlbumImage();
            if (albumArt != null) {
                Glide.with(itemView.getContext()).load(albumArt).into(ivSong);
            } else {
                ivSong.setImageResource(R.drawable.ic_song);
            }
            tvNameSong.setText(song.getNameSong());
            tvArtistSong.setText(song.getArtistSong());
        }
    }

    public void updateList(List<Song> newListSong) {
        listSong = new ArrayList<>();
        listSong.addAll(newListSong);
        notifyDataSetChanged();
    }
}
