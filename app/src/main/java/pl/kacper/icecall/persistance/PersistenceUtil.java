package pl.kacper.icecall.persistance;

import java.util.List;

import javax.inject.Inject;

import pl.kacper.icecall.model.User;
import pl.kacper.icecall.persistance.local.LocalPersistenceUtil;
import pl.kacper.icecall.persistance.network.NetworkPersistenceUtil;

/**
 * Created by kacper on 27.12.15.
 * Class for managing the overall persistence of the app.
 * The persistence is both local and global. The global part will be implemented someday.
 * Local persistence is produced via an XML application file.
 */
public class PersistenceUtil {

    @Inject
    LocalPersistenceUtil localPersistenceUtil;

    @Inject
    NetworkPersistenceUtil networkPersistenceUtil;

    private List<User> userList;

    public PersistenceUtil(LocalPersistenceUtil localPersistenceUtil, NetworkPersistenceUtil networkPersistenceUtil) {
        this.localPersistenceUtil = localPersistenceUtil;
        localPersistenceUtil.parseLocalData();
        userList = localPersistenceUtil.getUserList();
        this.networkPersistenceUtil = networkPersistenceUtil;
    }

    public boolean userExists(String userName) {
        return localPersistenceUtil.userExists(userName);
    }

    public void createUser(String userName, String password) {
        localPersistenceUtil.createUser(userName, password);
    }

    public boolean authUser(String userName, String password) {
        return localPersistenceUtil.authUser(userName, password);
    }

    public User getLoggedInUser(){
        return localPersistenceUtil.getLoggedInUser();
    }

    public void setLoggedInUser(User user){
        localPersistenceUtil.setLoggedInUser(user);
    }

    public void saveData(){
        localPersistenceUtil.saveData();
    }

    public User getUser(String userName){
        return localPersistenceUtil.getUser(userName);
    }

    public List<User> getUserList(){
        return localPersistenceUtil.getUserList();
    }
}
