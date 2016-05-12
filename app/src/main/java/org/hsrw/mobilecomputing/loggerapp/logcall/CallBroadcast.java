package org.hsrw.mobilecomputing.loggerapp.logcall;

/**
 * Created by simon on 08.05.16.
 * based on:
 * http://androidexample.com/Incomming_Phone_Call_Broadcast_Receiver__-_Android_Example/index.php?view=article_discription&aid=61&aaid=86
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.hsrw.mobilecomputing.loggerapp.LogElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

            Log.d("MyPhoneListener", state + "   incoming no:" + incomingNumber);

            switch (state)
            {
                case TelephonyManager.CALL_STATE_RINGING:
                    String msg = "New Phone Call Event. Incoming Number : " + incomingNumber;
                    Log.d("Phone Receive", " " + msg);
                    String dateString = Calendar.getInstance().getTime().toString();
                    mCallRecord.startRecording(dateString);
                    LogCallElement mLogElement = new LogCallElement(Calendar.getInstance().getTime(), incomingNumber);



                    SharedPreferences appSharedPrefs = PreferenceManager
                            .getDefaultSharedPreferences(CallBroadcast.this.getContext());
                    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

                    String jsonGet = appSharedPrefs.getString("LogCallElements", "");

                    Type type = new TypeToken<List<LogCallElement>>(){}.getType();
                    Gson gson = new Gson();

                    List<LogCallElement> listLogCallElements = gson.fromJson(jsonGet, type);

                    listLogCallElements.add(mLogElement);

                    String jsonAdd = gson.toJson(listLogCallElements);
                    prefsEditor.putString("LogCallElements", jsonAdd);
                    prefsEditor.apply();

                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
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
