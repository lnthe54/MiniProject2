package com.example.lnthe54.miniproject2.presenter;

/**
 * @author lnthe54 on 10/5/2018
 * @project MiniProject2
 */
public class DetailArtistPresenter {
    private CallBack callBack;

    public DetailArtistPresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void getListAlbumOfArtist(String nameArtist) {
        callBack.getListAlbumOfArtist(nameArtist);
    }

    public void getListSongOfArtist(String nameArtist) {
        callBack.getListSongOfArtist(nameArtist);
    }

    public String getImageAlbum(long albumID) {
        String path = callBack.getImageAlbum(albumID);
        return path;
    }

    public void openDetailAlbumActivity(int position) {
        callBack.openDetailAlbumActivity(position);
    }

    public void openPlayMusicActivity(int position) {
        callBack.openPlayMusicActivity(position);
    }

    public interface CallBack {

        void getListAlbumOfArtist(String nameArtist);

        void getListSongOfArtist(String nameArtist);

        void openDetailAlbumActivity(int position);

        void openPlayMusicActivity(int position);

        String getImageAlbum(long albumID);
    }
}
