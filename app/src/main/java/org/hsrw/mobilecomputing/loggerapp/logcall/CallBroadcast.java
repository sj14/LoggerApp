package org.hsrw.mobilecomputing.loggerapp.logcall;

/**
 * This class implements the logic of what to do when a call broadcast is received.
 * based on:
 * http://androidexample.com/Incomming_Phone_Call_Broadcast_Receiver__-_Android_Example/index.php?view=article_discription&aid=61&aaid=86
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class CallBroadcast extends BroadcastReceiver {

    Context mContext = null;

    // Create a MyPhoneStateListener (inner class) if a broadcast is received
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        try {
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            //Create Listner
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();

            // Register listener for LISTEN_CALL_STATE
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }
    }

    public Context getContext() {
        return mContext;
    }

    // Inner class
    private class MyPhoneStateListener extends PhoneStateListener {
        CallRecord mCallRecord = CallRecord.getInstance();

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("pref_log_calls", false)) {
                Log.d("CallBroadcast", "Call Record disabled");
                return;
            }

            Log.d("MyPhoneListener", state + "   incoming no:" + incomingNumber);

            switch (state)
            {
                case TelephonyManager.CALL_STATE_RINGING:
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (!mCallRecord.isRecording()) {
                        String msg = "New Phone Call Event. Incoming Number : " + incomingNumber;
                        Log.d("Phone Receive", " " + msg);

                        // If the call is off-hook, get the current date and time
                        Date date = Calendar.getInstance().getTime();

                        // and create a LogCallElement Object.
                        LogCallElement mLogCallElement = new LogCallElement(date, incomingNumber);

                        // The recording path is set to the timestamp/1000.
                        String filePath = CallRecord.getRecordingPath(date.getTime() / 1000);
                        Log.d("CallBroadcast Record", filePath);

                        // start the recording
                        mCallRecord.startRecording(filePath, getContext());

                        // Add the newly created Object to the list of all Call Objects.
                        LogCallElement.addCallElement(mLogCallElement, CallBroadcast.this.getContext());
                    }
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    // If the call ends, stop the recording
                    if (mCallRecord.isRecording()) {
                         mCallRecord.stopRecording();
                    }
                    break;
            }
        }
    }
}
