package com.example.hshakilst.musicplayer;

import java.io.Serializable;

/**
 * Created by hshakilst on 5/4/2017.
 */

public class Song implements Serializable{

    private long songId;
    private String songArtist;
    private String songTitle;

    public Song(long songId, String songTitle, String songArtist){
        this.songId = songId;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
}
