package eu.ase.ro.ratescalculator.util;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    public String name;
    public List<String> interest = new ArrayList<String>();
    public String contactNo;
    public String email;

    public Bank(String name, List<String> interest, String contactNo, String email) {
        this.name = name;
        this.interest = interest;
        this.contactNo = contactNo;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", interest=" + interest +
                ", contactNo='" + contactNo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInterest() {
        return interest;
    }
    public void setInterest(List<String> interest) {
        this.interest = interest;
    }
    public String getContactNo() {
        return contactNo;
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
