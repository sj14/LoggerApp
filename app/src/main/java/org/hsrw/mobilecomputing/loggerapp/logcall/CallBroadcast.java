package org.hsrw.mobilecomputing.loggerapp.logcall;

/**
 * Created by simon on 08.05.16.
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
                        Date date = Calendar.getInstance().getTime();
                        LogCallElement mLogCallElement = new LogCallElement(date, incomingNumber);

                        String filePath = CallRecord.getRecordingPath(date.getTime() / 1000);
                        Log.d("CallBroadcast Record", filePath);
                        mCallRecord.startRecording(filePath, getContext());
                        LogCallElement.addCallElement(mLogCallElement, CallBroadcast.this.getContext());
                    }
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    if (mCallRecord.isRecording()) {
                         mCallRecord.stopRecording();
                    }
                    break;
            }
        }
    }
}
