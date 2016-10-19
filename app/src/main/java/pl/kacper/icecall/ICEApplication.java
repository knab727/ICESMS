package pl.kacper.icecall;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;
import pl.kacper.icecall.dagger.ApplicationModule;
import pl.kacper.icecall.dagger.ICECallModule;

/**
 * Created by kacper on 29.11.15.
 */
public class ICEApplication extends Application {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
    }
    public void inject(Object object) {
        objectGraph.inject(object);
    }

    protected List<Object> getModules() {
        List<Object> modules = new ArrayList<Object>();
        modules.add(new ICECallModule(this));
        modules.add(new ApplicationModule(this));

        return modules;
    }
}
