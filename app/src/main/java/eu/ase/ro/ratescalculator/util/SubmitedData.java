package eu.ase.ro.ratescalculator.util;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class SubmitedData implements Parcelable {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String dateToBeContacted;
    private Object[] receivedObject;



    public SubmitedData(String firstName, String lastName, String phoneNumber,
                        String email, String dateToBeContacted, Object[] receivedObject) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateToBeContacted = dateToBeContacted;
        this.receivedObject = receivedObject;
    }

    protected SubmitedData(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        dateToBeContacted = in.readString();
        receivedObject = in.readArray(Object.class.getClassLoader());

    }

    public static final Creator<SubmitedData> CREATOR = new Creator<SubmitedData>() {
        @Override
        public SubmitedData createFromParcel(Parcel in) {
            return new SubmitedData(in);
        }

        @Override
        public SubmitedData[] newArray(int size) {
            return new SubmitedData[size];
        }
    };

    @Override
    public String toString() {
        return "SubmitedData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dateToBeContacted='" + dateToBeContacted + '\'' +
                ", receivedObject=" + receivedObject[0].toString() +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateToBeContacted() {
        return dateToBeContacted;
    }

    public void setDateToBeContacted(String dateToBeContacted) {
        this.dateToBeContacted = dateToBeContacted;
    }

    public Object[] getReceivedObject() {
        return receivedObject;
    }

    public void setReceivedObject(Object[] receivedObject) {
        this.receivedObject = receivedObject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
        parcel.writeString(dateToBeContacted);
        parcel.writeArray(receivedObject);
    }
}
