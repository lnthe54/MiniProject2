package com.example.lnthe54.miniproject2.model;

/**
 * @author lnthe54 on 9/29/2018
 * @project MiniProject2
 */
public class Albums {
    private int id;
    private String nameAlbum;
    private String authorAlbum;
    private String albumArt;
    private int numberSong;

    public Albums(int id, String nameAlbum, String authorAlbum, String albumArt, int numberSong) {
        this.id = id;
        this.nameAlbum = nameAlbum;
        this.authorAlbum = authorAlbum;
        this.albumArt = albumArt;
        this.numberSong = numberSong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    public String getAuthorAlbum() {
        return authorAlbum;
    }

    public void setAuthorAlbum(String authorAlbum) {
        this.authorAlbum = authorAlbum;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public int getNumberSong() {
        return numberSong;
    }
}
