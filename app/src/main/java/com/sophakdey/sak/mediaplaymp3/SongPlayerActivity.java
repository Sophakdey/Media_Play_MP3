package com.sophakdey.sak.mediaplaymp3;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.health.TimerStat;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class SongPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton mPlayPause, mPlayNext, mPlayBack, mAddMusic, mlistMusic;
    private TextView mTitle, timestart, timefinal;
    private SeekBar mSeeckTime;
    private final static String ATG = "Inetfaceplay";
    private Handler handler = new Handler();

    int totalDuration = 0;
    int currentPosition = 0;
    double startTime = 0;
    double finalTime = 0;

    ClassButtonPlay classButtonPlay;
    ClassPlayMusic classPlayMusic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final int position = bundle.getInt("pos", 0);
        Log.d(ATG, "Position" + position);

        classButtonPlay = ClassButtonPlay.getInstance(getApplicationContext());
        classPlayMusic = ClassPlayMusic.getAllinstance(getApplicationContext());

        timefinal = (TextView) findViewById(R.id.endtime);
        timestart = (TextView) findViewById(R.id.starttime);
        mTitle = (TextView) findViewById(R.id.title_play);
        mPlayPause = (ImageButton) findViewById(R.id.btn_play);
        mPlayNext = (ImageButton) findViewById(R.id.btn_next);
        mPlayBack = (ImageButton) findViewById(R.id.btn_back);
        mlistMusic = (ImageButton) findViewById(R.id.iConBrowsToListSong);
//        mAddMusic = (ImageButton) findViewById(R.id.btn_add_music);
        mPlayPause.setOnClickListener(this);
        mPlayNext.setOnClickListener(this);
        mPlayBack.setOnClickListener(this);
        mlistMusic.setOnClickListener(this);
//        mAddMusic.setOnClickListener(this);
        mSeeckTime = (SeekBar) findViewById(R.id.seekBar);
        mSeeckTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View me, MotionEvent event) {
                clickTouchMe(me);
                return false;
            }
        });
            Song musicFileInfo = classButtonPlay.move(position);
            mTitle.setText(musicFileInfo.getTitle());
            classPlayMusic.play(musicFileInfo);
            UpdateSongTime.run();
            updateSeekbar();

    }

    public void clickTouchMe(View me) {
        if (classPlayMusic.mediaPlayer.isPlaying()) {
            SeekBar sb = (SeekBar) me;
            classPlayMusic.mediaPlayer.seekTo(sb.getProgress());
        }
    }

    public void updateSeekbar() {
        Log.d("Seekbar", "update");
        if (classPlayMusic.mediaPlayer.isPlaying()) {
            totalDuration = classPlayMusic.mediaPlayer.getDuration();
            currentPosition = classPlayMusic.mediaPlayer.getCurrentPosition();
            mSeeckTime.setMax(totalDuration);
            mSeeckTime.setProgress(currentPosition);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(ATG, "SeekBar Run");
                    updateSeekbar();
                }
            }, 500);
        } else {
            Log.d(ATG, "player not playing");
            classButtonPlay.next();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_play:
                Log.d(ATG, "ClickStart&Pause" + v);
                if (classPlayMusic.mediaPlayer.isPlaying()) {
                    classPlayMusic.mediaPlayer.pause();
                } else classPlayMusic.mediaPlayer.start();
                break;
            case R.id.btn_next:
                Log.d(ATG, "ClickNext" + v);
                classPlayMusic.mediaPlayer.stop();
                classPlayMusic.mediaPlayer.release();
                classPlayMusic.play(classButtonPlay.next());
                Song mSong1 = classButtonPlay.current();
                mTitle.setText(mSong1.getTitle());
                mSeeckTime.setMax(classPlayMusic.mediaPlayer.getDuration());
                UpdateSongTime.run();
                updateSeekbar();
                break;
            case R.id.btn_back:
                Log.d(ATG, "ClickBack" + v);
                classPlayMusic.mediaPlayer.stop();
                classPlayMusic.mediaPlayer.release();
                classPlayMusic.play(classButtonPlay.previous());
                Song mSong = classButtonPlay.current();
                mTitle.setText(mSong.getTitle());
                mSeeckTime.setMax(classPlayMusic.mediaPlayer.getDuration());
                UpdateSongTime.run();
                updateSeekbar();
                break;
            case R.id.iConBrowsToListSong:
                Log.d(ATG,"ClickButtonListMusic");
                Intent intent = new Intent(getApplicationContext(),SongListActivity.class);
                startActivity(intent);
        }
    }
    private Runnable UpdateSongTime = new Runnable() {
        @SuppressLint("DefaultLocale")
        public void run() {
            startTime = classPlayMusic.mediaPlayer.getCurrentPosition();
            timestart.setText(String.format("%d : %d s",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
            finalTime = classPlayMusic.mediaPlayer.getDuration();
            timefinal.setText(String.format("%d : %d s",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))));
            mSeeckTime.setProgress((int)startTime);
            handler.postDelayed(this, 100);
        }
    };
}


