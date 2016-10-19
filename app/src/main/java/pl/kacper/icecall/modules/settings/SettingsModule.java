package pl.kacper.icecall.modules.settings;

import pl.kacper.icecall.modules.RemoteMedModule;
import pl.kacper.icecall.ui.basic.adapters.ViewPagerAdapter;
import pl.kacper.icecall.ui.seizuredetect.fragments.SeizureAttacksFragment;
import pl.kacper.icecall.ui.seizuredetect.fragments.SeizureICENumbersFragment;
import pl.kacper.icecall.ui.settings.fragments.SettingsFragment;

/**
 * Created by kacper on 01.01.16.
 * Class for handling display of all the Settings data.
 */
public class SettingsModule implements RemoteMedModule{

    private final static String moduleName = "Settings";

    public SettingsModule() {
    }

    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public ViewPagerAdapter fillUIFragments(ViewPagerAdapter adapter) {
        adapter.addFragment(new SettingsFragment(), "Settings");
        return adapter;
    }

    @Override
    public void enableModule() {

    }

    @Override
    public void disableModule() {

    }
}
