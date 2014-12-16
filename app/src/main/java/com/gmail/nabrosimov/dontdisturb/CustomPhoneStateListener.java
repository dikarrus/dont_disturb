package com.gmail.nabrosimov.dontdisturb;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by nikolay on 17.12.14.
 */
public class CustomPhoneStateListener extends PhoneStateListener {
    private final String TAG = "CustomPhoneStateListener";

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        Log.v(TAG, "onCallStateChanged called");
        switch(state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "RINGING " + incomingNumber);
                break;
        }
    }
}
