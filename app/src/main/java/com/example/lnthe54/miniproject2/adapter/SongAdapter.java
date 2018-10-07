package com.example.lnthe54.miniproject2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.model.Song;
import com.example.lnthe54.miniproject2.utils.Config;
import com.example.lnthe54.miniproject2.view.activity.MainActivity;
import com.example.lnthe54.miniproject2.view.fragment.FragmentMoreMusic;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private ArrayList<Song> listSong;
    private CallBack callBack;
    private Context context;

    public SongAdapter(Context context, CallBack callBack, ArrayList<Song> listSong) {
        this.context = context;
        this.callBack = callBack;
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
        private RoundedImageView ivSong;
        private ImageView ivMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivSong = itemView.findViewById(R.id.iv_song);
            tvNameSong = itemView.findViewById(R.id.tv_name_song);
            tvArtistSong = itemView.findViewById(R.id.tv_artist_song);
            ivMore = itemView.findViewById(R.id.iv_more);

            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBottomSheetMore(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        callBack.itemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void bindData(Song song) {
            String imageAlbum = song.getAlbumImage();

            if (imageAlbum != null) {
                Glide.with(itemView.getContext()).load(imageAlbum).into(ivSong);
            } else {
                ivSong.setImageResource(R.drawable.album_default);
            }
            tvNameSong.setText(song.getNameSong());
            tvArtistSong.setText(song.getArtistSong());
        }

        public void showBottomSheetMore(int position) {
            Song song = listSong.get(position);

            String imageSong = song.getAlbumImage();
            String nameSong = song.getNameSong();
            String nameArtist = song.getArtistSong();

            Bundle bundle = new Bundle();
            bundle.putString(Config.NAME_SONG, nameSong);
            bundle.putString(Config.NAME_ARTIST, nameArtist);
            bundle.putString(Config.IMAGE, imageSong);

            FragmentMoreMusic frgMoreMusic = new FragmentMoreMusic();
            frgMoreMusic.setArguments(bundle);
            frgMoreMusic.show(((MainActivity) context).getSupportFragmentManager(), frgMoreMusic.getTag());
        }
    }

    public void updateList(List<Song> newListSong) {
        listSong = new ArrayList<>();
        listSong.addAll(newListSong);
        notifyDataSetChanged();
    }

    public interface CallBack {
        void itemClick(int position);
    }
}
