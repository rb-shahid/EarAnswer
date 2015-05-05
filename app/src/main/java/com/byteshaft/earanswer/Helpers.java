package com.byteshaft.earanswer;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

public class Helpers extends ContextWrapper {

    public Helpers(Context base) {
        super(base);
    }

    TelephonyManager getTelephonyManager() {
        return (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    }

    void pickUpCall() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(
                KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
        sendOrderedBroadcast(intent, null);
    }
}
