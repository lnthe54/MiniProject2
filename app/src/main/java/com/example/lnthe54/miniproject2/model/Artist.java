package com.example.lnthe54.miniproject2.model;

/**
 * @author lnthe54 on 9/29/2018
 * @project MiniProject2
 */
public class Artist {
    private int id;
    private String nameArtist;
    private int numberSong;
    private int numberAlbums;

    public Artist(int id, String nameArtist, int numberSong, int numberAlbums) {
        this.id = id;
        this.nameArtist = nameArtist;
        this.numberSong = numberSong;
        this.numberAlbums = numberAlbums;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public int getNumberSong() {
        return numberSong;
    }

    public int getNumberAlbums() {
        return numberAlbums;
    }
}
