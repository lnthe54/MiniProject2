package com.example.lnthe54.miniproject2.presenter;

/**
 * @author lnthe54 on 10/5/2018
 * @project MiniProject2
 */
public class PlayMusicPresenter {
    private CallBack callBack;

    public PlayMusicPresenter(CallBack callBack) {
        this.callBack = callBack;
    }

    public void getDataFromIntent() {
        callBack.getDataFromIntent();
    }

    public void updateData() {
        callBack.updateData();
    }

    public void updateMainActivity() {
        callBack.updateMainActivity();
    }

    public void updatePlayPause() {
        callBack.updatePlayPause();
    }

    public void updateShuffle() {
        callBack.updateShuffle();
    }

    public void updateRepeat() {
        callBack.updateRepeat();
    }

    public void updateSeekBar() {
        callBack.updateSeekBar();
    }

    public void playMusic() {
        callBack.playMusic();
    }

    public void playPauseMusic() {
        callBack.playPauseMusic();
    }

    public void previousMusic() {
        callBack.back();
    }

    public void nextMusic() {
        callBack.next();
    }

    public interface CallBack {

        void getDataFromIntent();

        void updateData();

        void updateMainActivity();

        void updatePlayPause();

        void updateShuffle();

        void updateRepeat();

        void updateSeekBar();

        void playMusic();

        void playPauseMusic();

        void back();

        void next();
    }
}
