package com.example.lnthe54.miniproject2.presenter;

/**
 * @author lnthe54 on 10/5/2018
 * @project MiniProject2
 */
public class DetailAlbumPresenter {
    private CallBack callBack;

    public DetailAlbumPresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void updateData() {
        callBack.updateData();
    }

    public void getListSongOfAlbum(long albumId) {
        callBack.getListSongOfAlbum(albumId);
    }

    public String getImageAlbum(long albumID) {
        String path = callBack.getImageAlbum(albumID);
        return path;
    }

    public void getTimeOfListSong() {
        callBack.getTimeOfListSong();
    }

    public void openPlayMusicActivity(int position) {
        callBack.openPlayMusicActivity(position);
    }

    public interface CallBack {

        void updateData();

        void getListSongOfAlbum(long albumID);

        String getImageAlbum(long albumID);

        void getTimeOfListSong();

        void openPlayMusicActivity(int position);
    }
}
