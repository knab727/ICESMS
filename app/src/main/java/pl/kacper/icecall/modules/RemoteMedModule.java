package pl.kacper.icecall.modules;

import android.support.v4.app.Fragment;

import java.util.Map;

import pl.kacper.icecall.ui.basic.adapters.ViewPagerAdapter;

/**
 * Created by kacper on 01.01.16.
 * Interface which must be implemented by every Module in the framework.
 */
public interface RemoteMedModule {
    String getModuleName();

    ViewPagerAdapter fillUIFragments(ViewPagerAdapter adapter);

    void enableModule();

    void disableModule();
}
