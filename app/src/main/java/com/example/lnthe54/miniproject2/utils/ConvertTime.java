package com.example.lnthe54.miniproject2.utils;

/**
 * @author lnthe54 on 9/30/2018
 * @project MiniProject2
 */
public class ConvertTime {
    public static String miniSecondToString(int totalTime) {
        int min = totalTime / 60;
        int sec = totalTime - min * 60;
        return String.format("%02d:%02d", min, sec);
    }
}
