package pl.kacper.icecall.modules;

import android.app.usage.UsageEvents;
import android.hardware.SensorEvent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import pl.kacper.icecall.model.ModuleData;
import pl.kacper.icecall.model.User;
import pl.kacper.icecall.modules.seizuredetect.SeizureDetectModule;
import pl.kacper.icecall.modules.settings.SettingsModule;
import pl.kacper.icecall.persistance.PersistenceUtil;

/**
 * Created by kacper on 30.12.15.
 * Class relating general module info and relaying it to anyone asking for it.
 */
public class ModuleManager {

    @Inject
    SeizureDetectModule seizureDetectModule;

    @Inject
    SettingsModule settingsModule;

    @Inject
    PersistenceUtil persistenceUtil;

    List<String> moduleNames = new ArrayList<>();
    Map<String, RemoteMedModule> modules = new HashMap<>();

    public ModuleManager(SeizureDetectModule seizureDetectModule,
                         SettingsModule settingsModule,
                         PersistenceUtil persistenceUtil) {
        this.seizureDetectModule = seizureDetectModule;
        this.settingsModule = settingsModule;
        this.persistenceUtil = persistenceUtil;
        initModules();  }

    private void initModules() {
        modules.put(seizureDetectModule.getModuleName(), seizureDetectModule);
        modules.put(settingsModule.getModuleName(), settingsModule);

        for(RemoteMedModule module : modules.values())
            moduleNames.add(module.getModuleName());
    }

    public List<String> getModuleNames() {
        return moduleNames;
    }

    /**
     * Returns a list of enabled modules
     * @return list of names of enabled modules
     */
    public List<String> getEnabledModuleNames(){
        ArrayList<String> list = new ArrayList<>();
        List<ModuleData>  dataList = persistenceUtil.getLoggedInUser().getModuleSettings();
        RemoteMedModule module;
        for(ModuleData data : dataList) {
            if((module = getModuleByName(data.getModuleName())) != null){
                if(data.getEnabled()){
                    module.enableModule();
                    list.add(module.getModuleName());
                } else {
                    module.disableModule();
                }
            }
        }
        list.add(settingsModule.getModuleName());
        return list;
    }

    /**
     * Allows to get a whole module by name
     * @param moduleName
     * @return
     */
    public RemoteMedModule getModuleByName(String moduleName){
        return modules.get(moduleName);
    }

    /**
     * Relays sensorEvents to appropriate modules
     * @param event
     */
    public void manageSensorEvent(SensorEvent event){
        seizureDetectModule.processEvent(event);
    }

    /**
     * Method for setting the piece of model to be used by modules
     */
    public void setData(){
        seizureDetectModule.setData(persistenceUtil.getLoggedInUser().getSeizureDetectData());
        seizureDetectModule.setPersistenceUtil(persistenceUtil);
    }
}
