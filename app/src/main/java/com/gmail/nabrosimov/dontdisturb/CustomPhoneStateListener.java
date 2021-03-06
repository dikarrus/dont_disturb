package com.gmail.nabrosimov.dontdisturb;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.ContactsContract;
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
    private ContentResolver m_contentResolver;

    public CustomPhoneStateListener(Context inContext) {
        m_audioManager = (AudioManager) inContext.getSystemService(Context.AUDIO_SERVICE);
        m_contentResolver = inContext.getContentResolver();

    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        Log.v(TAG, "onCallStateChanged called");
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "RINGING " + incomingNumber);
                if (!isFavoriteContact(incomingNumber)) {
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

    private boolean isFavoriteContact(String inNumber) {

        Uri queryUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(inNumber));
        Log.v(TAG, "URI = " + queryUri.toString());

        String[] projection = new String[]{
                ContactsContract.PhoneLookup._ID,
                ContactsContract.PhoneLookup.STARRED};

        String selection = ContactsContract.PhoneLookup.STARRED + "='1'";

        Cursor cursor = m_contentResolver.query(queryUri, projection, selection, null, null);

        boolean isStarred = false;
        while (cursor.moveToNext()) {
            int starred = cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup.STARRED));
            if (starred != 0) {
                isStarred = true;
                break;
            }
            Log.v(TAG, String.format("Number = %s, starred = %d", inNumber, starred));
        }
        cursor.close();

        return isStarred;
    }
}
