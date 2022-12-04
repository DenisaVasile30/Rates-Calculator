package eu.ase.ro.ratescalculator.util;

import android.widget.TextView;

public class Deposit {
    private int tv_deposited_value;
    private float tv_interest_rate_value;
    private float tv_interest_tax_value;
    private int tv_period_value;
    private float tv_earnings_value;
    private float tv_accumulated_value;

    public Deposit(int tv_deposited_value,
                   int tv_period_value,
                   float tv_interest_rate_value,
                   float tv_interest_tax_value,
                   float tv_earnings_value,
                   float tv_accumulated_value) {
        this.tv_deposited_value = tv_deposited_value;
        this.tv_period_value = tv_period_value;
        this.tv_interest_rate_value = tv_interest_rate_value;
        this.tv_interest_tax_value = tv_interest_tax_value;
        this.tv_earnings_value = tv_earnings_value;
        this.tv_accumulated_value = tv_accumulated_value;
    }

    public int getTv_deposited_value() {
        return tv_deposited_value;
    }

    public void setTv_deposited_value(int tv_deposited_value) {
        this.tv_deposited_value = tv_deposited_value;
    }

    public float getTv_interest_rate_value() {
        return tv_interest_rate_value;
    }

    public void setTv_interest_rate_value(float tv_interest_rate_value) {
        this.tv_interest_rate_value = tv_interest_rate_value;
    }

    public float getTv_interest_tax_value() {
        return tv_interest_tax_value;
    }

    public void setTv_interest_tax_value(float tv_interest_tax_value) {
        this.tv_interest_tax_value = tv_interest_tax_value;
    }

    public int getTv_period_value() {
        return tv_period_value;
    }

    public void setTv_period_value(int tv_period_value) {
        this.tv_period_value = tv_period_value;
    }

    public float getTv_earnings_value() {
        return tv_earnings_value;
    }

    public void setTv_earnings_value(float tv_earnings_value) {
        this.tv_earnings_value = tv_earnings_value;
    }

    public float getTv_accumulated_value() {
        return tv_accumulated_value;
    }

    public void setTv_accumulated_value(float tv_accumulated_value) {
        this.tv_accumulated_value = tv_accumulated_value;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "tv_deposited_value=" + tv_deposited_value +
                ", tv_interest_rate_value=" + tv_interest_rate_value +
                ", tv_interest_tax_value=" + tv_interest_tax_value +
                ", tv_period_value=" + tv_period_value +
                ", tv_earnings_value=" + tv_earnings_value +
                ", tv_accumulated_value=" + tv_accumulated_value +
                '}';
    }
}
