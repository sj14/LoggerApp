package org.hsrw.mobilecomputing.loggerapp.activities;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.hsrw.mobilecomputing.loggerapp.R;
import org.hsrw.mobilecomputing.loggerapp.logcall.CallRecord;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class RecordActivity extends AppCompatActivity {

    Long dateL;
    Date date;
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        TextView tv_name = (TextView) findViewById(R.id.tv_number);
        TextView tv_date = (TextView) findViewById(R.id.tv_date);
        //Button btn_play = (Button) findViewById(R.id.btn_play);

        String name = getIntent().getExtras().getString("name");
        dateL =  getIntent().getExtras().getLong("dateL");
        date = (Date) getIntent().getExtras().get("date");


        Log.d("RecordActivity", name);
        Log.d("RecordActivity", date.toString());


        if (tv_name != null) {
            tv_name.setText(name);
        }

        if (tv_date != null) {
            tv_date.setText(date.toString());
        }
    }

    public void onClickPlay(View view) {
        boolean mStartPlaying = true;
        onPlay(mStartPlaying);
        mStartPlaying = !mStartPlaying;
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        String mFileName = CallRecord.getRecordingPath(date.getTime()/1000);
        Log.d("RecordActivity Play", mFileName);
        FileDescriptor fd = null;

        try {
            FileInputStream fis = new FileInputStream(mFileName);
            fd = fis.getFD();

            mPlayer.setDataSource(fd);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("RecordActivity", "prepare() failed");
            e.printStackTrace();
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }
}
