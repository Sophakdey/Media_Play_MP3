package com.sophakdey.sak.mediaplaymp3;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by sak on 11/20/16.
 */

public class ClassButtonPlay {
    private final List<Song> musicFileInfos;
    private static ClassButtonPlay instance;
    Context context;
    static int currentPosition = 0;

    public static ClassButtonPlay getInstance(Context context) {
        if(instance == null) {
            instance = new ClassButtonPlay(context);
        }
        return instance;
    }

    private ClassButtonPlay(Context context){
        this.context = context;
        musicFileInfos = ScannerMusic.scanSdCard(context);
    }

    public Song current() {
        Log.d("PlayList","MusicFileInfos"+currentPosition);
        return musicFileInfos.get(currentPosition);
    }

    public Song move(int position) {
        Log.d("PlayList","Move"+position);
        currentPosition = position;
        return current();
    }

    public Song next() {
        currentPosition += 1;
        if(currentPosition == musicFileInfos.size()) {
            Log.d("Next","CurrentPosition<0");
            currentPosition = (currentPosition +1)%musicFileInfos.size();

        }
        return current();
    }

    public Song previous() {
        currentPosition -= 1;
        if(currentPosition < 0) {
            Log.d("Previous","CurrentPosition<0");
            currentPosition =(currentPosition -1<0)?musicFileInfos.size()-1 :currentPosition-1;

        }
        return current();
    }

}
