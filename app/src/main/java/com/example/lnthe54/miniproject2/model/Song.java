package com.example.lnthe54.miniproject2.model;

import java.io.Serializable;

/**
 * @author lnthe54 on 9/28/2018
 * @project MiniProject2
 */
public class Song implements Serializable {
    private int id;
    private String nameSong;
    private String artistSong;
    private String albums;
    private String albumImage;
    private int duration;
    private String path;

    public Song(int id, String nameSong, String artistSong, String albums, int duration, String path) {
        this.id = id;
        this.nameSong = nameSong;
        this.artistSong = artistSong;
        this.albums = albums;
        this.duration = duration;
        this.path = path;
    }

    public Song(int id, String nameSong, String artistSong, String albums, String albumImage, int duration, String path) {
        this.id = id;
        this.nameSong = nameSong;
        this.artistSong = artistSong;
        this.albums = albums;
        this.albumImage = albumImage;
        this.duration = duration;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getArtistSong() {
        return artistSong;
    }

    public void setArtistSong(String artistSong) {
        this.artistSong = artistSong;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

