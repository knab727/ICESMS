package pl.kacper.icecall.dagger;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.kacper.icecall.activities.LoginActivity;
import pl.kacper.icecall.activities.MainActivity;
import pl.kacper.icecall.activities.RegisterActivity;
import pl.kacper.icecall.activities.UserListActivity;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.persistance.local.LocalPersistenceUtil;
import pl.kacper.icecall.persistance.network.NetworkPersistenceUtil;

/**
 * Created by kacper on 29.11.15.
 */
@Module(
        injects = {
                LoginActivity.class,
                LocalPersistenceUtil.class,
                MainActivity.class,
                RegisterActivity.class,
                UserListActivity.class,
                PersistenceUtil.class
        },
        addsTo = ApplicationModule.class,
        library = true
)
public class RemoteMedModule {
    private final Context context;

    public RemoteMedModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public LocalPersistenceUtil provideFileManager(File file){
        return new LocalPersistenceUtil(file);
    }

    @Provides
    @Singleton
    public NetworkPersistenceUtil provideNetworkPersistenceUtil(){
        return new NetworkPersistenceUtil();
    }

    @Provides
    @Singleton
    public PersistenceUtil providePersistenceUtil(LocalPersistenceUtil localPersistenceUtil, NetworkPersistenceUtil networkManagerUtil){
        return new PersistenceUtil(localPersistenceUtil, networkManagerUtil);
    }

}
