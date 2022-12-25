package eu.ase.ro.ratescalculator.database;


import static androidx.room.ForeignKey.CASCADE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts",
//        indices = {@Index(value = {"id_deposit"}, unique = true)},
        foreignKeys = @ForeignKey(
        entity = Deposit.class,
        childColumns = "id_deposit",
        parentColumns = "id_deposit",
                onDelete = ForeignKey.CASCADE
        ))
public class SubmitedData implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "id_deposit")
    private long id_deposit;
    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "date_to_be_contacted")
    private String dateToBeContacted;

    @Ignore
    private Object[] receivedObject;

    @Ignore
    public SubmitedData(){}

    @Ignore
    public SubmitedData(String firstName, String lastName, String phoneNumber,
                        String email, String dateToBeContacted, Object[] receivedObject) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateToBeContacted = dateToBeContacted;
        this.receivedObject = receivedObject;
    }

    public SubmitedData(long id, long id_deposit, String firstName, String lastName, String phoneNumber, String email, String dateToBeContacted) {
        this.id = id;
        this.id_deposit = id_deposit;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateToBeContacted = dateToBeContacted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_deposit() {
        return id_deposit;
    }

    public void setId_deposit(long id_deposit) {
        this.id_deposit = id_deposit;
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

    public String toStringContacts() {
        return "SubmitedData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dateToBeContacted='" + dateToBeContacted + '\'' +
                '}';
    }
}
