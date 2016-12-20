package com.sophakdey.sak.mediaplaymp3;

import java.io.Serializable;

/**
 * Created by chhaichivon on 11/14/16.
 */

public class Song implements Serializable {
    private String title;
    private String songDuration;
    private String artist;
    private String path;


    public Song(String title, String songDuration, String artist, String path) {
        this.title = title;
        this.songDuration = songDuration;
        this.artist = artist;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String path) {
        this.songDuration = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        String str ="Title ="+ title +"\tPath ="+songDuration+"\tAritis ="+artist;
        return str;
    }
}
