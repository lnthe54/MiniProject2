package com.example.lnthe54.miniproject2.presenter;

/**
 * @author lnthe54 on 10/5/2018
 * @project MiniProject2
 */
public class MainPresenter {
    private CallBack callBack;

    public MainPresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void showCurrentSong() {
        callBack.showCurrentSong();
    }

    public void clickLayoutCurrentSong() {
        callBack.clickLayoutPlaying();
    }

    public void clickBtnPrevious() {
        callBack.clickBtnPrevious();
    }

    public void clickBtnPlayPause() {
        callBack.clickBtnPlayPause();
    }

    public void clickBtnNext() {
        callBack.clickBtnNext();
    }

    public interface CallBack {
        void showCurrentSong();

        void clickLayoutPlaying();

        void clickBtnPrevious();

        void clickBtnPlayPause();

        void clickBtnNext();
    }
}
