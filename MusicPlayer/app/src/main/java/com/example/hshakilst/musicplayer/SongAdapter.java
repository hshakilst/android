package com.example.hshakilst.musicplayer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hshakilst on 5/4/2017.
 */

public class SongAdapter extends BaseAdapter {
    private ArrayList<Song> songList;
    private LayoutInflater songLayoutInflater;

    public SongAdapter(Context context, ArrayList<Song> list){
        songLayoutInflater = LayoutInflater.from(context);
        songList = list;
    }
    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LinearLayout songLayout = (LinearLayout) songLayoutInflater.inflate(R.layout.song, viewGroup, false);
        TextView songView = (TextView) songLayout.findViewById(R.id.song_title);
        TextView artistView = (TextView) songLayout.findViewById(R.id.song_artist);
        Song currentSong = songList.get(position);
        songView.setText(currentSong.getSongTitle());
        artistView.setText(currentSong.getSongArtist());
        songLayout.setTag(position);
        return songLayout;
    }
}
