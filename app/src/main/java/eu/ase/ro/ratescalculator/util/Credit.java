package eu.ase.ro.ratescalculator.util;

public class Credit {
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
        return "Credit{" +
                "loanType='" + loanType + '\'' +
                ", desiredAmount=" + desiredAmount +
                ", period=" + period +
                ", collectSalary=" + collectSalary +
                ", interestValue=" + interestValue +
                ", firstRateValue=" + firstRateValue +
                ", totalPaymentValue=" + totalPaymentValue +
                '}';
    }
}
