package org.hsrw.mobilecomputing.loggerapp.logcall;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by simon on 09.05.16.
 */
public class CallRecord {

    private static CallRecord instance;
    private  MediaRecorder mRecorder;
    private static final String LOG_TAG = "CallRecord";
    private boolean isRecording = false;

    private CallRecord() { }

    public static CallRecord getInstance () {
        if (CallRecord.instance == null) {
            CallRecord.instance = new CallRecord ();
        }
        return CallRecord.instance;
    }


    public void startRecording(String dateFileName) {
        String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/callrecord_" + dateFileName + ".3gp";

        instance.mRecorder = new MediaRecorder();
        instance.mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        instance.mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        instance.mRecorder.setOutputFile(mFileName);
        instance.mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        try {
            instance.mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed: ");
            e.printStackTrace();

        }

        try {
            instance.mRecorder.start();
            isRecording = true;
        } catch (Exception e) {
            Log.e(LOG_TAG, "start() failed: ");
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        try {
            isRecording = false;
            instance.mRecorder.stop();
            instance.mRecorder.reset();
            instance.mRecorder.release();
            instance.mRecorder = null;
            Log.d(LOG_TAG, "Recording stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRecording() {
        return isRecording;
    }
}
