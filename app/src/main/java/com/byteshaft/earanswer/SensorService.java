package com.byteshaft.earanswer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


public class SensorService extends Service implements SensorEventListener {

    private CallStateListener mCallStateListener;
    private Sensor mProximitySensor;
    private Helpers mHelpers;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHelpers = new Helpers(getApplicationContext());
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_UI);
        mCallStateListener = new CallStateListener();
        TelephonyManager telephonyManager = mHelpers.getTelephonyManager();
        telephonyManager.listen(mCallStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }

    @Override public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] != mProximitySensor.getMaximumRange() &&
                mCallStateListener.isCallIncoming()) {
            mHelpers.pickUpCall();
        }
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
