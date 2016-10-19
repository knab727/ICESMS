package pl.kacper.icecall.model.seizuredetect;

import java.util.ArrayList;
import java.util.List;

import pl.kacper.icecall.model.ModuleData;

/**
 * Created by kacper on 28.12.15.
 * Model object storing all the XML/Web data for Seizure Detect Module
 */
public class SeizureDetectData implements ModuleData {

    private boolean enabled;
    private List<SeizureEvent> seizureEventList = new ArrayList<>();
    private List<SeizureICEPhoneNumber> seizurePhoneNumbers = new ArrayList<>();
    private final static String moduleName="Seizure Detect";

    public void addSeizureEvent(SeizureEvent event){
        seizureEventList.add(event);
    }

    public void addPhoneNumber(SeizureICEPhoneNumber number){
        seizurePhoneNumbers.add(number);
    }

    public void removePhoneNumber(SeizureICEPhoneNumber number){
        seizurePhoneNumbers.remove(number);
    }

    public void removePhoneNumber(int position){
        seizurePhoneNumbers.remove(position);
    }

    public List<SeizureEvent> getSeizureEventList() {
        return seizureEventList;
    }

    public List<SeizureICEPhoneNumber> getSeizurePhoneNumbers() {
        return seizurePhoneNumbers;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getModuleName(){
        return moduleName;
    }

}
