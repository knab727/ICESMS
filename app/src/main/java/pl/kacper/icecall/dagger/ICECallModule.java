package pl.kacper.icecall.dagger;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.kacper.icecall.activities.LoginActivity;
import pl.kacper.icecall.persistance.FileManagerUtil;

/**
 * Created by kacper on 29.11.15.
 */
@Module(
        injects = {
                LoginActivity.class,
                FileManagerUtil.class,
        },
        addsTo = ApplicationModule.class,
        library = true
)
public class ICECallModule {
    private final Context context;

    public ICECallModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public FileManagerUtil provideFileManager(File file){
        return new FileManagerUtil(file);
    }
}
