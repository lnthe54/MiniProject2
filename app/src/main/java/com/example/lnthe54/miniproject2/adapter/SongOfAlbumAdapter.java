package com.example.lnthe54.miniproject2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.utils.ConvertTime;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/30/2018
 * @project MiniProject2
 */
public class SongOfAlbumAdapter extends RecyclerView.Adapter<SongOfAlbumAdapter.ViewHolder> {
    private ArrayList<Song> listSong;

    public SongOfAlbumAdapter(ArrayList<Song> listSong) {
        this.listSong = listSong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_song_of_album, parent, false);
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

        private TextView tvPosition;
        private TextView tvNameSong;
        private TextView tvArtistSong;
        private TextView tvDuration;

        private int duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPosition = itemView.findViewById(R.id.tv_position);
            tvNameSong = itemView.findViewById(R.id.tv_name_song);
            tvArtistSong = itemView.findViewById(R.id.tv_artist_song);
            tvDuration = itemView.findViewById(R.id.tv_duration);
        }

        public void bindData(Song song) {
            tvPosition.setText(String.valueOf(getAdapterPosition() + 1));
            tvNameSong.setText(song.getNameSong());
            tvArtistSong.setText(song.getArtistSong());
            duration = song.getDuration() / 1000;
            tvDuration.setText(ConvertTime.miniSecondToString(duration));
        }
    }
}
