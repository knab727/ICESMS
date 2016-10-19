package pl.kacper.icecall.model;

import java.util.ArrayList;
import java.util.List;

import pl.kacper.icecall.model.seizuredetect.SeizureDetectData;

/**
 * Created by kacper on 27.12.15.
 * Class holding and controlling all the module and user data.
 * The main model class.
 */
public class User {

    private String login;
    private String password;
    private boolean loggedIn;
    private SeizureDetectData seizureDetectData;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public SeizureDetectData getSeizureDetectData() {
        return seizureDetectData;
    }

    public void setSeizureDetectData(SeizureDetectData seizureDetectData) {
        this.seizureDetectData = seizureDetectData;
    }

    public List<ModuleData> getModuleSettings(){
        List<ModuleData> list = new ArrayList<>();
        list.add(seizureDetectData);
        return list;
    }


    public void setModuleSettings(List<ModuleData> list){
        seizureDetectData.setEnabled(list.get(0).getEnabled());
    }
}
