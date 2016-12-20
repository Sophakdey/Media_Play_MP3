package com.sophakdey.sak.mediaplaymp3;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;


public class ScannerMusic {

    public static List<Song> scanSdCard(Context context){
        List<Song> musicFileInfos = new ArrayList<>();
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
        };
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";
        Cursor cursor = null;
        try {
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursor = context.getContentResolver().query(uri, projection, selection, null, sortOrder);
            if( cursor != null){
                cursor.moveToFirst();
                while( !cursor.isAfterLast() ){

                    String title = cursor.getString(0);
                    String artist = cursor.getString(1);
                    String path = cursor.getString(2);
                    String displayName  = cursor.getString(3);
                    String songDuration = cursor.getString(4);
                    Song musicFileInfo = new Song(title, songDuration, artist, path);
                    musicFileInfos.add(musicFileInfo);
                    cursor.moveToNext();
                }

            }
            return musicFileInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if( cursor != null){
                cursor.close();
            }
        }
        return musicFileInfos;
    }
}
