package pl.kacper.icecall.ui.basic.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.kacper.icecall.R;
import pl.kacper.icecall.configuration.DaggerAppCompatActivity;
import pl.kacper.icecall.modules.ModuleManager;
import pl.kacper.icecall.modules.RemoteMedModule;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.ui.seizuredetect.fragments.SeizureAttacksFragment;
import pl.kacper.icecall.ui.seizuredetect.fragments.SeizureICENumbersFragment;
import pl.kacper.icecall.ui.basic.adapters.ViewPagerAdapter;

public class DisplayActivity extends DaggerAppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RemoteMedModule module;

    @Inject
    ModuleManager moduleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        initModule();
        initToolbar();
        intiViewPager();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initModule() {
        String moduleName = getIntent().getStringExtra("MODULE_NAME");
        module = moduleManager.getModuleByName(moduleName);
        assert(module != null);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = module.getModuleName();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void intiViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        module.fillUIFragments(adapter);
        viewPager.setAdapter(adapter);
    }
}
