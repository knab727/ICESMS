package pl.kacper.icecall.persistance.local;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.List;

import pl.kacper.icecall.model.User;
import pl.kacper.icecall.model.seizuredetect.SeizureDetectData;
import pl.kacper.icecall.model.seizuredetect.SeizureEvent;
import pl.kacper.icecall.model.seizuredetect.SeizureICEPhoneNumber;

/**
 * Created by kacper on 29.12.15.
 * Local, XML, persistence for SeizureDetect Module
 */
public class LocalSeizureDetectPersistence {

    public static void parseSeizureDetect(Element module, User user) {
        SeizureDetectData seizureDetectData = new SeizureDetectData();
        user.setSeizureDetectData(seizureDetectData);
        boolean enabled = Boolean.parseBoolean(module.getAttribute("enabled"));
        seizureDetectData.setEnabled(enabled);
        Element seizureEvents = (Element) module.getElementsByTagName("seizureEvents").item(0);
        parseSeizureEvents(seizureDetectData, seizureEvents);
        Element iceNumbers = (Element) module.getElementsByTagName("ICENumbers").item(0);
        parseICENumbers(seizureDetectData, iceNumbers);
    }


    private static void parseSeizureEvents(SeizureDetectData seizureDetectData, Element seizureEvents) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        NodeList events = seizureEvents.getElementsByTagName("seizureEvent");
        for(int i = 0; i < events.getLength(); i++){
            Node node = events.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element event = (Element) node;
                SeizureEvent seizureEvent = new SeizureEvent();
                seizureEvent.setCallMade(Boolean.parseBoolean(event.getAttribute("callMade")));
                try{
                    seizureEvent.setTimestamp(dateFormat.parse(event.getAttribute("time")));
                } catch (Exception e){
                    e.printStackTrace();
                }
                seizureDetectData.addSeizureEvent(seizureEvent);
            }
        }
    }

    private static void parseICENumbers(SeizureDetectData seizureDetectData, Element iceNumbers) {
        NodeList numbers = iceNumbers.getElementsByTagName("ICENumber");
        for(int i = 0; i < numbers.getLength(); i++){
            Node node = numbers.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element number = (Element) node;
                SeizureICEPhoneNumber iceNumber = new SeizureICEPhoneNumber();
                iceNumber.setContactName(number.getAttribute("contact"));
                iceNumber.setPhoneNumber(number.getAttribute("phoneNumber"));
                seizureDetectData.addPhoneNumber(iceNumber);
            }
        }
    }

    public static void saveUserSeizureDetectData(Document outputDocument, SeizureDetectData seizureDetectData, Element user) {
        Element seizureDetect = outputDocument.createElement("seizureDetect");
        user.appendChild(seizureDetect);

        Attr enabled = outputDocument.createAttribute("enabled");
        enabled.setValue(Boolean.toString(seizureDetectData.getEnabled()));
        seizureDetect.setAttributeNode(enabled);

        Element seizureEvents = outputDocument.createElement("seizureEvents");
        seizureDetect.appendChild(seizureEvents);

        Element iCENumbers = outputDocument.createElement("ICENumbers");
        seizureDetect.appendChild(iCENumbers);

        List<SeizureEvent> events = seizureDetectData.getSeizureEventList();
        List<SeizureICEPhoneNumber> numbers = seizureDetectData.getSeizurePhoneNumbers();

        for(SeizureEvent event : events)
            saveSeizureEvent(outputDocument, event, seizureEvents);
        for(SeizureICEPhoneNumber number: numbers)
            saveSeizureICENumber(outputDocument, number, iCENumbers);
    }

    private static void saveSeizureEvent(Document outputDocument,SeizureEvent event, Element seizureEvents) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Element seizureEvent = outputDocument.createElement("seizureEvent");
        seizureEvents.appendChild(seizureEvent);

        try {
            Attr time = outputDocument.createAttribute("time");
            String dateStr = dateFormat.format(event.getTimestamp());
            time.setValue(dateStr);
            seizureEvent.setAttributeNode(time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Attr callMade = outputDocument.createAttribute("callMade");
        callMade.setValue(Boolean.toString(event.isCallMade()));
        seizureEvent.setAttributeNode(callMade);
    }

    private static void saveSeizureICENumber(Document outputDocument, SeizureICEPhoneNumber number, Element iCENumbers) {
        Element iCENumber = outputDocument.createElement("ICENumber");
        iCENumbers.appendChild(iCENumber);

        Attr contact = outputDocument.createAttribute("contact");
        contact.setValue(number.getContactName());
        iCENumber.setAttributeNode(contact);

        Attr phoneNumber = outputDocument.createAttribute("phoneNumber");
        phoneNumber.setValue(number.getPhoneNumber());
        iCENumber.setAttributeNode(phoneNumber);
    }

}
