package com.example.lnthe54.miniproject2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.miniproject2.R;
import com.example.lnthe54.miniproject2.model.Albums;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/29/2018
 * @project MiniProject2
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private ArrayList<Albums> listAlbum;
    private CallBack callBack;

    public AlbumAdapter(CallBack callBack, ArrayList<Albums> listAlbum) {
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
        private ImageView ivAlbum;
        private TextView tvNameAlbum;
        private TextView tvAuthorAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAlbum = itemView.findViewById(R.id.iv_album);
            tvNameAlbum = itemView.findViewById(R.id.tv_name_album);
            tvAuthorAlbum = itemView.findViewById(R.id.tv_artist_album);

            tvNameAlbum.startAnimation(AnimationUtils.loadAnimation(itemView.getContext(), R.anim.translate_text));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.itemClick(getAdapterPosition());
                }
            });
        }

        public void bindData(Albums albums) {
            String albumArt = albums.getAlbumArt();
            if (albumArt != null) {
                Glide.with(itemView.getContext()).load(albumArt).into(ivAlbum);
            } else {
                ivAlbum.setImageResource(R.drawable.ic_album_default);
            }

            tvNameAlbum.setText(albums.getNameAlbum());
            tvAuthorAlbum.setText(albums.getAuthorAlbum());
        }
    }

    public interface CallBack {
        void itemClick(int position);
    }
}
