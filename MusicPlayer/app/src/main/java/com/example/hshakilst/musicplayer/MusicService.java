package com.example.hshakilst.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.PowerManager;
import android.util.Log;
import java.util.Random;
import android.app.Notification;
import android.app.PendingIntent;
import android.widget.Toast;

/**
 * Created by hshakilst on 5/4/2017.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener{

    private MediaPlayer mediaPlayer;
    private ArrayList<Song> songs;
    private int songPosition;
    private final IBinder musicBind = new MusicBinder();
    private String songTitle;
    private static final int NOTIFY_ID = 1;
    private boolean shuffle=false;
    private Random rand;

    @Override
    public void onCreate() {
        super.onCreate();
        songPosition = 0;
        mediaPlayer = new MediaPlayer();
        rand = new Random();
        initMusicPlayer();
    }

    public void setShuffle(){
        if(shuffle) {
            shuffle = false;
            Toast.makeText(this, "Shuffle is disabled", Toast.LENGTH_LONG).show();
        }
        else {
            shuffle = true;
            Toast.makeText(this, "Shuffle is enabled", Toast.LENGTH_LONG).show();
        }
    }

    public void setSong(int songIndex){
        songPosition = songIndex;
    }

    public void playSong(){
        mediaPlayer.reset();
        Song playSong = songs.get(songPosition);
        long currSongId = playSong.getSongId();
        this.songTitle = playSong.getSongTitle();
        Uri songUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currSongId);
        try{
            mediaPlayer.setDataSource(getApplicationContext(), songUri);
        }catch (Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        mediaPlayer.prepareAsync();
    }

    private void initMusicPlayer(){
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    public void setSongList(ArrayList<Song> songList){
        this.songs = songList;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (mediaPlayer.getCurrentPosition() > 0){
            mediaPlayer.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mediaPlayer.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_play_arrow_black_24dp)
                .setTicker(songTitle).setOngoing(true).setContentTitle("Now playing").setContentText(songTitle);
        Notification notification = builder.build();
        startForeground(NOTIFY_ID, notification);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    public class MusicBinder extends Binder{
        MusicService getService(){
            return MusicService.this;
        }
    }

    public int getPosn(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDur(){
        return mediaPlayer.getDuration();
    }

    public boolean isPng(){
        return mediaPlayer.isPlaying();
    }

    public void pausePlayer(){
        mediaPlayer.pause();
    }

    public void seek(int posn){
        mediaPlayer.seekTo(posn);
    }

    public void go(){
        mediaPlayer.start();
    }

    public void playPrev(){
        songPosition--;
        if(songPosition < 0)
            songPosition = songs.size()-1;
        playSong();
    }

    public void playNext(){
        if(shuffle){
            int newSong = songPosition;
            while(newSong == songPosition){
                newSong = rand.nextInt(songs.size());
            }
            songPosition = newSong;
        }
        else{
            songPosition ++;
            if(songPosition >= songs.size())
                songPosition = 0;
        }
        playSong();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }
}
