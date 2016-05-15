package org.hsrw.mobilecomputing.loggerapp.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;

import org.hsrw.mobilecomputing.loggerapp.R;
import org.hsrw.mobilecomputing.loggerapp.logcall.CallRecord;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;


// http://stackoverflow.com/questions/3747139/how-can-i-show-a-mediacontroller-while-playing-audio-in-android

public class RecordActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl{

    Date date;
    MediaPlayer mPlayer;
    private MediaController mediaController;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        TextView tv_name = (TextView) findViewById(R.id.tv_number);
        TextView tv_date = (TextView) findViewById(R.id.tv_date);

        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);

        mediaController = new MediaController(this);

        String name = getIntent().getExtras().getString("name");
        date = (Date) getIntent().getExtras().get("date");


        Log.d("RecordActivity", name);
        Log.d("RecordActivity", date.toString());


        if (tv_name != null) {
            tv_name.setText(name);
        }

        if (tv_date != null) {
            tv_date.setText(date.toString());
        }

        PreparePlayer();
    }

    private void PreparePlayer() {
        String mFileName = CallRecord.getRecordingPath(date.getTime()/1000);
        Log.d("RecordActivity Play", mFileName);
        FileDescriptor fd;

        try {
            FileInputStream fis = new FileInputStream(mFileName);
            fd = fis.getFD();

            mPlayer.setDataSource(fd);
            mPlayer.prepare();
        } catch (IOException e) {
            Log.e("RecordActivity", "prepare() failed");
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaController.hide();
        mPlayer.stop();
        mPlayer.release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //the MediaController will hide after 3 seconds - tap the screen to make it appear again
        mediaController.show(0);
        return false;
    }

    //--MediaPlayerControl methods----------------------------------------------------

    @Override
    public void start() {
        mPlayer.start();
    }

    @Override
    public void pause() {
        mPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mPlayer.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
    //--------------------------------------------------------------------------------

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("RecordActivity", "onPrepared");
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.record_view));

        handler.post(new Runnable() {
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show(0);
            }
        });
    }
}
