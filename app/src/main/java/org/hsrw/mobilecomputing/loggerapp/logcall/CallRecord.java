package org.hsrw.mobilecomputing.loggerapp.logcall;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by simon on 09.05.16.
 */
public class CallRecord {

    private MediaRecorder mRecorder;
    private static final String LOG_TAG = "CallRecord";


    public void startRecording(String dateFileName) {
        String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/thisisATest.3gp";

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed: ");
            e.printStackTrace();

        }

        try {
            mRecorder.start();
        } catch (Exception e) {
            Log.e(LOG_TAG, "start() failed: ");
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        try {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
