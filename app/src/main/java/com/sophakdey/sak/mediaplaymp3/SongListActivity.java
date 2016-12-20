package com.sophakdey.sak.mediaplaymp3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class SongListActivity extends AppCompatActivity {

    ClassPlayMusic classPlayMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.textRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        final List<Song> songInfo = ScannerMusic.scanSdCard(getApplicationContext());
        Log.d("MUSIC","SCAN FILE" + songInfo.size());
        final RecyclerViewMusicAdapter recyclerViewMusicAdapter = new RecyclerViewMusicAdapter(songInfo);
        mRecyclerView.setAdapter(recyclerViewMusicAdapter);
        recyclerViewMusicAdapter.setAdapterListener(new AdapterListener() {
            @Override
            public void onItemClick(int adapterPosition) {
                Log.d("ActivionViewList","ClickItem"+adapterPosition);
                Song song = songInfo.get(adapterPosition);
                Intent intent = new Intent(getApplicationContext(),SongPlayerActivity.class).putExtra("pos",adapterPosition);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
