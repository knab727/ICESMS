package pl.kacper.icecall.model.seizuredetect;

/**
 * Created by kacper on 28.12.15.
 * An object storing data on a number to be called in case of a seizure attack.
 * Data stored comprises of a contact name and phone number.
 *
 */
public class SeizureICEPhoneNumber {
    private String contactName;
    private String phoneNumber;

    public SeizureICEPhoneNumber(String contactName, String phoneNumber) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    public SeizureICEPhoneNumber() {
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeizureICEPhoneNumber that = (SeizureICEPhoneNumber) o;

        if (contactName != null ? !contactName.equals(that.contactName) : that.contactName != null)
            return false;
        return !(phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null);

    }

    @Override
    public int hashCode() {
        int result = contactName != null ? contactName.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }
}
