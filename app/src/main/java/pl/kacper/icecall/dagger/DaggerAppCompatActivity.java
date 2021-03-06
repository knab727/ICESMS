package pl.kacper.icecall.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.kacper.icecall.RemoteMedApplication;

/**
 * Created by kacper on 29.11.15.
 */
public class DaggerAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RemoteMedApplication) getApplication()).inject(this);
    }
}
