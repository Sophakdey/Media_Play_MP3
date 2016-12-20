package com.sophakdey.sak.mediaplaymp3;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.List;

/**
 * Created by sak on 11/20/16.
 */

public class ClassPlayMusic extends AppCompatActivity {
    private final List<Song> musicFileInfos;
    Context context;
    int currentPosition = 0;
    Uri uri;
    private static ClassPlayMusic allinstance;
    public MediaPlayer mediaPlayer;
    ClassButtonPlay classButtonPlay;


    public ClassPlayMusic(Context context) {
        this.context = context;
        musicFileInfos = ScannerMusic.scanSdCard(context);


    }

    public static ClassPlayMusic getAllinstance(Context context) {
        if ((allinstance == null)) {
            allinstance = new ClassPlayMusic(context);
        }
        return allinstance;
    }

    public Song current() {
        return musicFileInfos.get(currentPosition);
    }


    public Song play(Song musicFileInfo) {
            uri = Uri.parse(musicFileInfo.getPath());
            mediaPlayer = MediaPlayer.create(context, uri);
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        mediaPlayer.start();
        return current();
    }
}
