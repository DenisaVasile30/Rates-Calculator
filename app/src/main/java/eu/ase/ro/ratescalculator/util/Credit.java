package eu.ase.ro.ratescalculator.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Credit implements Parcelable {
    String loanType;
    int desiredAmount;
    int period;
    boolean collectSalary;
    float interestValue;
    float firstRateValue;
    float totalPaymentValue;

    public Credit(String loanType, int desiredAmount, int period, boolean collectSalary,
                  float interestValue, float firstRateValue, float totalPaymentValue) {
        this.loanType = loanType;
        this.desiredAmount = desiredAmount;
        this.period = period;
        this.collectSalary = collectSalary;
        this.interestValue = interestValue;
        this.firstRateValue = firstRateValue;
        this.totalPaymentValue = totalPaymentValue;
    }

    protected Credit(Parcel in) {
        loanType = in.readString();
        desiredAmount = in.readInt();
        period = in.readInt();
        collectSalary = in.readByte() != 0;
        interestValue = in.readFloat();
        firstRateValue = in.readFloat();
        totalPaymentValue = in.readFloat();
    }

    public static final Creator<Credit> CREATOR = new Creator<Credit>() {
        @Override
        public Credit createFromParcel(Parcel in) {
            return new Credit(in);
        }

        @Override
        public Credit[] newArray(int size) {
            return new Credit[size];
        }
    };

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public int getDesiredAmount() {
        return desiredAmount;
    }

    public void setDesiredAmount(int desiredAmount) {
        this.desiredAmount = desiredAmount;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isCollectSalary() {
        return collectSalary;
    }

    public void setCollectSalary(boolean collectSalary) {
        this.collectSalary = collectSalary;
    }

    public double getInterestValue() {
        return interestValue;
    }

    public void setInterestValue(float interestValue) {
        this.interestValue = interestValue;
    }

    public double getFirstRateValue() {
        return firstRateValue;
    }

    public void setFirstRateValue(float firstRateValue) {
        this.firstRateValue = firstRateValue;
    }

    public double getTotalPaymentValue() {
        return totalPaymentValue;
    }

    public void setTotalPaymentValue(float totalPaymentValue) {
        this.totalPaymentValue = totalPaymentValue;
    }

    @Override
    public String toString() {
        return ("Credit details: " +
                "\nLoan Type => " + loanType +
                "\nDesired Amount => " + desiredAmount +
                "\nPeriod => " + period +
                "\nCollect Salary => " + collectSalary +
                "\nInterest Percent => " + interestValue +
                "\nFirst Rate Value => " + firstRateValue +
                "\nTotal Payment Value => " + totalPaymentValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(loanType);
        parcel.writeInt(desiredAmount);
        parcel.writeInt(period);
        parcel.writeByte((byte) (collectSalary ? 1 : 0));
        parcel.writeFloat(interestValue);
        parcel.writeFloat(firstRateValue);
        parcel.writeFloat(totalPaymentValue);
    }
}
