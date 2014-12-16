package com.gmail.nabrosimov.dontdisturb;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


public class CallListenerService extends Service {
    private final String TAG = "CallListenerService";
    public CallListenerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Служба создана",
                Toast.LENGTH_SHORT).show();

        Log.d(TAG, "CallListenerService.onCreate called");

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new CustomPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }
}
