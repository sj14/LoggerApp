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

    public CallRecord() {
        mRecorder = new MediaRecorder();
    }


    public void startRecording(String mFileName) {
        mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed: " + e.getLocalizedMessage());
        }

        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}
