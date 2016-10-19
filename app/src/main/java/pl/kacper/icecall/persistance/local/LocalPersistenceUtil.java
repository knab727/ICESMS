package pl.kacper.icecall.persistance.local;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import pl.kacper.icecall.model.seizuredetect.SeizureDetectData;
import pl.kacper.icecall.model.User;
import pl.kacper.icecall.model.seizuredetect.SeizureEvent;
import pl.kacper.icecall.model.seizuredetect.SeizureICEPhoneNumber;

/**
 * Created by kacper on 29.11.15.
 * Class for local persistence in XML for general purpose.
 */
public class LocalPersistenceUtil {

    @Inject
    File file;

    private List<User> userList = new ArrayList<>();

    private Document document;
    private Document outputDocument;

    public LocalPersistenceUtil(File file) {
        this.file = file;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseLocalData() {
        NodeList nUsers = document.getElementsByTagName("user");
        for(int i = 0; i < nUsers.getLength(); i++){
            Node node = nUsers.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) node;
                parseUser(userElement);
            }
        }
    }

    private void parseUser(Element userElement) {
        User user = new User();
        user.setLogin(userElement.getAttribute("login"));
        user.setPassword(userElement.getAttribute("password"));
        user.setLoggedIn(Boolean.parseBoolean(userElement.getAttribute("loggedIn")));
        NodeList moduleNodes = userElement.getChildNodes();
        for(int i = 0; i < moduleNodes.getLength(); i++){
            Node node = moduleNodes.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element module = (Element) node;
                parseModuleData(module, user);
            }
        }
        userList.add(user);
    }

    private void parseModuleData(Element module, User user) {
        if(module.getTagName().equals("seizureDetect"))
            LocalSeizureDetectPersistence.parseSeizureDetect(module, user);
        //other modules to be placed here
    }

    public boolean userExists(String userName) {
        for(User user : userList){
            if(user.getLogin().equals(userName))
                return true;
        }
        return false;
    }

    public void createUser(String userName, String password) {
        String passwordEncrypted;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            passwordEncrypted = new String(digest);
            User newUser = new User(userName, passwordEncrypted);
            newUser.setSeizureDetectData(new SeizureDetectData());
            userList.add(newUser);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (UnsupportedEncodingException e2){
            e2.printStackTrace();
        }
    }

    public boolean authUser(String userName, String password){
        String passwordEncrypted;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            passwordEncrypted = new String(digest);
            for(User user : userList){
                if(user.getLogin().equals(userName) && user.getPassword().equals(passwordEncrypted))
                    return true;
            }
            return false;
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (UnsupportedEncodingException e2){
            e2.printStackTrace();
        }
        return false;
    }

    public void saveData(){
        saveData(userList);
    }

    public void saveData(List<User> userListSaved){
        try{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        outputDocument = documentBuilder.newDocument();

        //root element
        Element rootElement = outputDocument.createElement("users");
        outputDocument.appendChild(rootElement);
        //user elements
        for(int i = 0; i < userListSaved.size(); i++){
            User user = userListSaved.get(i);
            saveUser(rootElement, user);
        }
        //Save data
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            DOMSource source = new DOMSource(outputDocument);
            StreamResult result = new StreamResult(new PrintWriter( new FileOutputStream(file, false)));
            transformer.transform(source, result);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void saveUser(Element rootElement, User userSaved) {
        Element user = outputDocument.createElement("user");
        rootElement.appendChild(user);

        Attr login = outputDocument.createAttribute("login");
        login.setValue(userSaved.getLogin());
        user.setAttributeNode(login);

        Attr password = outputDocument.createAttribute("password");
        password.setValue(userSaved.getPassword());
        user.setAttributeNode(password);

        Attr loggedIn = outputDocument.createAttribute("loggedIn");
        loggedIn.setValue(Boolean.toString(userSaved.isLoggedIn()));
        user.setAttributeNode(loggedIn);

        SeizureDetectData seizureDetectData = userSaved.getSeizureDetectData();
        LocalSeizureDetectPersistence.saveUserSeizureDetectData(outputDocument, seizureDetectData, user);
        //save other modules
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getUser(String userName) {
        for(User user : userList)
            if(user.getLogin().equals(userName))
                return user;
        return null;
    }

    public User getLoggedInUser(){
        for(User u : userList)
            if(u.isLoggedIn())
                return u;
        return null;
    }

    public void setLoggedInUser(User user){
        for(User u : userList)
            if(!u.getLogin().equals(user.getLogin()))
                u.setLoggedIn(false);
        user.setLoggedIn(true);
    }
}
