package com.example.lnthe54.miniproject2.utils;

import com.example.lnthe54.miniproject2.view.activity.MainActivity;

/**
 * @author lnthe54 on 10/1/2018
 * @project MiniProject2
 */
public class Common {
    public static void updateMainActivity() {
        MainActivity mainActivity = (MainActivity) AppController.getInstance().getMainActivity();
        if (mainActivity != null) {
            mainActivity.showCurrentSong();
        }
    }
}
