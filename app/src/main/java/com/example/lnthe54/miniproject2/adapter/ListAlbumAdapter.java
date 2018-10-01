package com.example.lnthe54.miniproject2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.model.Albums;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/30/2018
 * @project MiniProject2
 */
public class ListAlbumAdapter extends RecyclerView.Adapter<ListAlbumAdapter.ViewHolder> {
    private ArrayList<Albums> listAlbum;
    private CallBack callBack;

    public ListAlbumAdapter(CallBack callBack, ArrayList<Albums> listAlbum) {
        this.callBack = callBack;
        this.listAlbum = listAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_albums, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Albums albums = listAlbum.get(position);
        holder.bindData(albums);
    }

    @Override
    public int getItemCount() {
        return listAlbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView ivAlbum;
        private TextView tvNameAlbum;
        private TextView tvNameArtist;
        private TextView tvNumberSong;
        private TextView tvDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAlbum = itemView.findViewById(R.id.iv_album);
            tvNameAlbum = itemView.findViewById(R.id.tv_name_album);
            tvNameArtist = itemView.findViewById(R.id.tv_name_artist);
            tvNumberSong = itemView.findViewById(R.id.tv_number_song);
            tvDuration = itemView.findViewById(R.id.tv_duration_total);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.itemClickList(getAdapterPosition());
                }
            });
        }

        public void bindData(Albums albums) {
            String path = albums.getAlbumArt();
            if (path != null) {
                Glide.with(itemView.getContext()).load(path).into(ivAlbum);
            } else {
                ivAlbum.setImageResource(R.drawable.ic_album_default);
            }

            tvNameAlbum.setText(albums.getNameAlbum());
            tvNameArtist.setText(albums.getAuthorAlbum());
            tvNumberSong.setText(albums.getNumberSong() + " bài hát");
        }
    }

    public interface CallBack {
        void itemClickList(int position);
    }
}
