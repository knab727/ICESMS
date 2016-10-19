package pl.kacper.icecall.model.seizuredetect;

import java.util.Date;

/**
 * Created by kacper on 28.12.15.
 * The smallest object of Seizure Detect Module. Stores data on time of seizure attack and weather
 * the call has been made.
 */
public class SeizureEvent {
    private Date timestamp;
    private boolean callMade;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isCallMade() {
        return callMade;
    }

    public void setCallMade(boolean callMade) {
        this.callMade = callMade;
    }
}
