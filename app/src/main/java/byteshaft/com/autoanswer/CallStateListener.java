package byteshaft.com.autoanswer;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallStateListener extends PhoneStateListener {

    private boolean isCallIncoming = false;

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isCallIncoming = true;
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                isCallIncoming = false;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                isCallIncoming = false;
                break;
        }
    }

    boolean isCallIncoming() {
        return isCallIncoming;
    }
}
