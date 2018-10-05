package com.example.lnthe54.miniproject2.presenter;

import android.view.MenuItem;

/**
 * @author lnthe54 on 10/5/2018
 * @project MiniProject2
 */
public class FrgAlbumPresenter {
    private CallBack callBack;

    public FrgAlbumPresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void changeIconConvert(MenuItem item) {
        callBack.changeIconConvert(item);
    }

    public void getAlbumFromStorage() {
        callBack.getAlbumFromStorage();
    }

    public void openDetailAlbumActivity(int position) {
        callBack.openDetailAlbumActivity(position);
    }

    public interface CallBack {

        void changeIconConvert(MenuItem item);

        void getAlbumFromStorage();

        void openDetailAlbumActivity(int position);
    }
}
