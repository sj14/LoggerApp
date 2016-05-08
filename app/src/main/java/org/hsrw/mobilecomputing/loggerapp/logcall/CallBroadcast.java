package org.hsrw.mobilecomputing.loggerapp.logcall;

/**
 * Created by simon on 08.05.16.
 * based on:
 * http://androidexample.com/Incomming_Phone_Call_Broadcast_Receiver__-_Android_Example/index.php?view=article_discription&aid=61&aaid=86
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallBroadcast extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

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

    private class MyPhoneStateListener extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {

            Log.d("MyPhoneListener", state + "   incoming no:" + incomingNumber);

            // Ringing = 1, Offhook = 2
            if (state == 1) {
                String msg = "New Phone Call Event. Incoming Number : " + incomingNumber;
                Log.d("Phone Receive", " " + msg);
            }
        }
    }
}
