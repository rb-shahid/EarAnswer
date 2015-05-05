package byteshaft.com.autoanswer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;


public class SensorService extends Service implements SensorEventListener {
    CallStateListener callStateListener;

    private Sensor proximitySensor;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI);
        callStateListener = new CallStateListener();
        TelephonyManager telephonyManager = getTelephonyManager();
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] != proximitySensor.getMaximumRange() && callStateListener.isCallIncoming()) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
            intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(
                    KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
            sendOrderedBroadcast(intent, null);
            System.out.println("ok");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private TelephonyManager getTelephonyManager() {
        return (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    }
}
