package eu.ase.ro.ratescalculator.util;


import java.util.ArrayList;
import java.util.List;

public class BankProduct {

    public String type;
    public Bank bankName;
    public List<String> period = new ArrayList<String>();
    public String minimumAmount;
    public String maximumAmount;

    public BankProduct(String type, Bank bankName, List<String> period, String minimumAmount, String maximumAmount) {
        this.type = type;
        this.bankName = bankName;
        this.period = period;
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
    }

    @Override
    public String toString() {
        return "BankProduct{" +
                "type='" + type + '\'' +
                ", bankName=" + bankName.toString() +
                ", period=" + period +
                ", minimumAmount='" + minimumAmount + '\'' +
                ", maximumAmount='" + maximumAmount + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Bank getBankName() {
        return bankName;
    }
    public void setBankName(Bank bankName) {
        this.bankName = bankName;
    }
    public List<String> getPeriod() {
        return period;
    }
    public void setPeriod(List<String> period) {
        this.period = period;
    }
    public String getMinimumAmount() {
        return minimumAmount;
    }
    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }
    public String getMaximumAmount() {
        return maximumAmount;
    }
    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }
}
