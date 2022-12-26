package eu.ase.ro.ratescalculator.database;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "deposits"
//        foreignKeys = @ForeignKey(
//                entity = SubmitedData.class,
//                childColumns = "id_deposit",
//                parentColumns = "id_deposit",
//                onDelete = ForeignKey.CASCADE
//        )
        )
public class Deposit {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_deposit")
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    @ColumnInfo(name = "deposited_value")
    private int tv_deposited_value;

    @ColumnInfo(name = "interest_rate")
    private float tv_interest_rate_value;

    @ColumnInfo(name = "tax_value")
    private float tv_interest_tax_value;

    @ColumnInfo(name = "period")
    private int tv_period_value;

    @ColumnInfo(name = "earnings")
    private float tv_earnings_value;

    @ColumnInfo(name = "accumulated_value")
    private float tv_accumulated_value;

    public Deposit(long id, int tv_deposited_value,
                   int tv_period_value,
                   float tv_interest_rate_value,
                   float tv_interest_tax_value,
                   float tv_earnings_value,
                   float tv_accumulated_value) {
        this.id = id;
        this.tv_deposited_value = tv_deposited_value;
        this.tv_period_value = tv_period_value;
        this.tv_interest_rate_value = tv_interest_rate_value;
        this.tv_interest_tax_value = tv_interest_tax_value;
        this.tv_earnings_value = tv_earnings_value;
        this.tv_accumulated_value = tv_accumulated_value;
    }

    @Ignore
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

    public long getId() {
        return id;
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
