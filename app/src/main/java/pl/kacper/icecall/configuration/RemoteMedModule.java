package pl.kacper.icecall.configuration;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.kacper.icecall.modules.SensorsService;
import pl.kacper.icecall.modules.ModuleManager;
import pl.kacper.icecall.modules.seizuredetect.SeizureDetectModule;
import pl.kacper.icecall.modules.settings.SettingsModule;
import pl.kacper.icecall.ui.basic.activities.DisplayActivity;
import pl.kacper.icecall.ui.basic.activities.LoginActivity;
import pl.kacper.icecall.ui.basic.activities.MainActivity;
import pl.kacper.icecall.ui.basic.activities.RegisterActivity;
import pl.kacper.icecall.ui.basic.activities.UserListActivity;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.persistance.local.LocalPersistenceUtil;
import pl.kacper.icecall.persistance.network.NetworkPersistenceUtil;
import pl.kacper.icecall.ui.seizuredetect.fragments.SeizureAttacksFragment;
import pl.kacper.icecall.ui.seizuredetect.fragments.SeizureICENumbersFragment;
import pl.kacper.icecall.ui.settings.fragments.SettingsFragment;

/**
 * Created by kacper on 29.11.15.
 * Class used with dagger to provide dependency injection.
 * Provides injection for listed classes
 */
@Module(
        injects = {
                LoginActivity.class,
                LocalPersistenceUtil.class,
                MainActivity.class,
                RegisterActivity.class,
                UserListActivity.class,
                PersistenceUtil.class,
                ModuleManager.class,
                SeizureAttacksFragment.class,
                SeizureICENumbersFragment.class,
                SettingsFragment.class,
                DisplayActivity.class,
                SensorsService.class,
        },
        addsTo = ApplicationModule.class,
        library = true
)
public class RemoteMedModule {
    private final Context context;

    public RemoteMedModule(Context context){
        this.context = context;
    }

    /**
     * provides a single instance of LocalPersistenceUtil
     * @param file provided automatically by dagger framework
     * @return
     */
    @Provides
    @Singleton
    public LocalPersistenceUtil provideLocalPersistanceUtil(File file){
        return new LocalPersistenceUtil(file);
    }

    /**
     * provides NetworkPersistenceUtil, a shell class to be implemented
     * with REST API communication
     * @return NetworkPersistenceUtil
     */
    @Provides
    @Singleton
    public NetworkPersistenceUtil provideNetworkPersistenceUtil(){
        return new NetworkPersistenceUtil();
    }


    /**
     *
     * @param localPersistenceUtil provided by framework
     * @param networkManagerUtil provided by framework
     * @return PersistenceUtil an Adapter class dealing with persistence
     */
    @Provides
    @Singleton
    public PersistenceUtil providePersistenceUtil(LocalPersistenceUtil localPersistenceUtil, NetworkPersistenceUtil networkManagerUtil){
        return new PersistenceUtil(localPersistenceUtil, networkManagerUtil);
    }

    //Modules

    /**
     * Creation of a single instance ModuleHolder, a general module manager
     * @param seizureDetectModule
     * @param settingsModule
     * @param persistenceUtil
     * @return
     */
    @Provides
    @Singleton
    public ModuleManager provideModuleHolder(
            SeizureDetectModule seizureDetectModule,
            SettingsModule settingsModule,
            PersistenceUtil persistenceUtil
    ){
        return new ModuleManager(seizureDetectModule, settingsModule, persistenceUtil);
    }

    /**
     * Creation of a SeizureDetectModule. Every module is a singleton.
     * @return
     */
    @Provides
    @Singleton
    public SeizureDetectModule provideSeizureDetectModule(){
        return new SeizureDetectModule();
    }

    /**
     * Creation of a SettingsModule. Every module is a singleton.
     * @return
     */
    @Provides
    @Singleton
    public SettingsModule provideSettingsModule(){
        return new SettingsModule();
    }

}
