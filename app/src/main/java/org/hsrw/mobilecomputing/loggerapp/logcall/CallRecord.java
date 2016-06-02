package org.hsrw.mobilecomputing.loggerapp.logcall;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;

/**
 * This class initiates the recorder
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

    // The recording path is set to the string "callrecord_" + date of the call + ".3gp".
    // E.g. callrecord_1464780617.3gp
    // The .3gp extension is always chosen, even when it's not correct, it does not influence the behaviour.
    public static String getRecordingPath(Long date) {
        String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/callrecord_" + date + ".3gp";
        return mFileName;
    }

    // start the recording
    public void startRecording(String filePath, Context context) {
        instance.mRecorder = new MediaRecorder();

        // Get the setting for the audiosource, because VOICE_CALL does not work on all phones.
        // VOICE_CALL should record both sides, whereas VOICE_COMMUNICATION only record the uplink (The user of this App).
        String audioSource = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString("pref_audiosource", "VOICE_COMMUNICATION");

        if (audioSource.equals("VOICE_COMMUNICATION")) {
            instance.mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        } else {
                instance.mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            }

        instance.mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        instance.mRecorder.setOutputFile(filePath);
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
