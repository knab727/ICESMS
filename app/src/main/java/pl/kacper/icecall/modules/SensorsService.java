package pl.kacper.icecall.modules;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import pl.kacper.icecall.configuration.DaggerService;


/**
 * Created by kacper on 05.01.16.
 * Service listening to Accelerometer events and relaying them to
 * ModuleManager who relays them to the correct modules
 */
public class SensorsService extends DaggerService implements SensorEventListener {

    @Inject
    ModuleManager moduleManager;

    private SensorManager sensorManager;
    private Sensor linearAccelSensor;

    @Override
    public void onCreate(){
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            linearAccelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            sensorManager.registerListener(this, linearAccelSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            linearAccelSensor = null;
        }
    }

    @Override
    public void onDestroy(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        moduleManager.manageSensorEvent(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
