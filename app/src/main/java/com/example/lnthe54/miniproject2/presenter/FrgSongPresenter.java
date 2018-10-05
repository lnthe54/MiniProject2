package com.example.lnthe54.miniproject2.presenter;

/**
 * @author lnthe54 on 10/5/2018
 * @project MiniProject2
 */
public class FrgSongPresenter {
    private CallBack callBack;

    public FrgSongPresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void getSongFromStorage() {
        callBack.getSongFromStorage();
    }

    public String getImageAlbum(long albumID) {
        String path = callBack.getImageAlbum(albumID);
        return path;
    }

    public void openPlayMusicActivity(int position) {
        callBack.openPlayMusicActivity(position);
    }

    public interface CallBack {

        void getSongFromStorage();

        String getImageAlbum(long albumID);

        void openPlayMusicActivity(int position);
    }
}
