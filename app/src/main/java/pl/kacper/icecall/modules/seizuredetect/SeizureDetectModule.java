package pl.kacper.icecall.modules.seizuredetect;

import android.hardware.SensorEvent;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import pl.kacper.icecall.model.seizuredetect.SeizureDetectData;
import pl.kacper.icecall.model.seizuredetect.SeizureEvent;
import pl.kacper.icecall.model.seizuredetect.SeizureICEPhoneNumber;
import pl.kacper.icecall.modules.RemoteMedModule;
import pl.kacper.icecall.persistance.PersistenceUtil;
import pl.kacper.icecall.ui.basic.adapters.ViewPagerAdapter;
import pl.kacper.icecall.ui.seizuredetect.fragments.SeizureAttacksFragment;
import pl.kacper.icecall.ui.seizuredetect.fragments.SeizureICENumbersFragment;

/**
 * Created by kacper on 30.12.15.
 * Class for handling all the SeizureDetect data update and incoming events.
 */
public class SeizureDetectModule implements RemoteMedModule {

    private final static String moduleName = "Seizure Detect";
    private boolean enabled;
    private float x = 0.0f;
    private float y = 0.0f;
    private float z = 0.0f;
    Calendar calendar = Calendar.getInstance();

    long lastTime = calendar.getTimeInMillis();

    private PersistenceUtil persistenceUtil;

    public PersistenceUtil getPersistenceUtil() {
        return persistenceUtil;
    }

    public void setPersistenceUtil(PersistenceUtil persistenceUtil) {
        this.persistenceUtil = persistenceUtil;
    }

    private SeizureDetectData data;

    public SeizureDetectData getData() {
        return data;
    }

    public void setData(SeizureDetectData data) {
        this.data = data;
    }

    public SeizureDetectModule() {
    }

    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public ViewPagerAdapter fillUIFragments(ViewPagerAdapter adapter) {
        adapter.addFragment(new SeizureAttacksFragment(), "Seizure Attacks");
        adapter.addFragment(new SeizureICENumbersFragment(), "ICE Numbers");
        return adapter;
    }

    @Override
    public void enableModule() {
        enabled = true;
    }

    @Override
    public void disableModule() {
        enabled = false;
    }

    public void processEvent(SensorEvent event){
        if(!enabled){
            //Log.d("Disabled ", "module disabled");
            return;
        } else {
            x = (x/10) + Math.abs(event.values[0]);
            y = (y/10) + Math.abs(event.values[1]);
            z = (z/10) + Math.abs(event.values[2]);
            Calendar c1 = Calendar.getInstance();
            long current = c1.getTimeInMillis();
            if((x+y+z >= 10) && ((current - lastTime) > 10000)){
                Log.d("Seizure", "OOOOh a SEIZURE :D");
                SeizureEvent seizureEvent = new SeizureEvent();
                Calendar c = Calendar.getInstance();
                Date date = c.getTime();
                lastTime = c.getTimeInMillis();
                seizureEvent.setTimestamp(date);
                seizureEvent.setCallMade(true);
                data.addSeizureEvent(seizureEvent);
                sendSms();
                persistenceUtil.saveData();
            }
        }
    }

    private void sendSms(){
        SmsManager smsManager = SmsManager.getDefault();
        for(SeizureICEPhoneNumber number : data.getSeizurePhoneNumbers()) {
            String num = number.getPhoneNumber();
            smsManager.sendTextMessage(num, null, "I just had a seizure", null, null);
        }
    }
}
