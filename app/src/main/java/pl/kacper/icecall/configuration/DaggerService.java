package pl.kacper.icecall.configuration;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import pl.kacper.icecall.RemoteMedApplication;

/**
 * Created by kacper on 06.01.16.
 * A service that can be injected with dagger
 */
public class DaggerService extends Service{
    @Override
    public void onCreate() {
        super.onCreate();
        ((RemoteMedApplication) getApplication()).inject(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
