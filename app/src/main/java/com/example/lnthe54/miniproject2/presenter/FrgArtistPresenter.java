package com.example.lnthe54.miniproject2.presenter;

/**
 * @author lnthe54 on 10/5/2018
 * @project MiniProject2
 */
public class FrgArtistPresenter {
    private CallBack callBack;

    public FrgArtistPresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void getArtistFromStorage() {
        callBack.getListArtistFromStorage();
    }

    public void openDetailArtistActivity(int position) {
        callBack.openDetailArtistActivity(position);
    }

    public interface CallBack {

        void getListArtistFromStorage();

        void openDetailArtistActivity(int position);
    }
}
