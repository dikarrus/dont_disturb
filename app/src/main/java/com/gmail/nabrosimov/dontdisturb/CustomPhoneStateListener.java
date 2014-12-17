package com.gmail.nabrosimov.dontdisturb;

import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by nikolay on 17.12.14.
 */
public class CustomPhoneStateListener extends PhoneStateListener {
    private final String TAG = "CustomPhoneStateListener";

    private int mainRingerMode = AudioManager.RINGER_MODE_NORMAL;
    private AudioManager m_audioManager;

    public CustomPhoneStateListener(AudioManager inAudioManager) {
        m_audioManager = inAudioManager;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        Log.v(TAG, "onCallStateChanged called");
        switch(state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "RINGING " + incomingNumber);
                if (incomingNumber.equals("+79295492088")) {
                    Log.d(TAG, "Setting silent mode");
                    mainRingerMode = m_audioManager.getRingerMode();
                    setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG, "IDLE");
                setRingerMode(mainRingerMode);
                break;
        }
    }

    private void setRingerMode(int inRingerMode) {
        m_audioManager.setRingerMode(inRingerMode);
    }
}
