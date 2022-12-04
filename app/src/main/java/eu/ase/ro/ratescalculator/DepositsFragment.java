package eu.ase.ro.ratescalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import eu.ase.ro.ratescalculator.util.Credit;
import eu.ase.ro.ratescalculator.util.Deposit;

public class DepositsFragment extends Fragment {

    private EditText tv_deposit_amount_value;
    private Spinner sp_deposit_period;
    private EditText tv_interest_value;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Button btn_calculate;
    private TextView tv_deposited_value;
    private TextView tv_interest_rate_value;
    private TextView tv_interest_tax_value;
    private TextView tv_period_value;
    private TextView tv_earnings_value;
    private TextView tv_accumulated_value;

    public DepositsFragment(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_deposits, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        if (getContext() != null) {
            initDepositValue(view);
            initSpnPeriod(view);
            initInterestValue(view);
            btn_calculate = view.findViewById(R.id.btn_calculate);
            btn_calculate.setOnClickListener(generateDepositValues());
        }
    }

    private View.OnClickListener generateDepositValues() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Deposit deposit = initDatas();
                Toast.makeText(getContext().getApplicationContext(),
                        deposit.toString(),
                        Toast.LENGTH_LONG).show();
            }

            private Deposit initDatas() {
                tv_deposited_value = getView().findViewById(R.id.tv_deposited_value);
                tv_deposited_value.setText(tv_deposit_amount_value.getText().toString());
                int depositedValue = Integer.parseInt(tv_deposited_value.getText().toString());

                tv_period_value = getView().findViewById(R.id.tv_period_value);
                String period = sp_deposit_period.getSelectedItem().toString();
                tv_period_value.setText(period);
                int days = (Integer.parseInt(period.substring(0, period.indexOf(" ")))) * 30;

                tv_interest_rate_value = getView().findViewById(R.id.tv_interest_rate_value);
               // tv_interest_rate_value.setText(tv_interest_value.getText().toString());
                float interestRate = Float.parseFloat(tv_interest_value.getText().toString());

                tv_interest_tax_value = getView().findViewById(R.id.tv_interest_tax_value);
                tv_earnings_value = getView().findViewById(R.id.tv_earnings_value);
                tv_accumulated_value = getView().findViewById(R.id.tv_accumulated_value);

                float calculatedInterest = (float)(depositedValue
                            * days * interestRate)
                            / (360 * 100);
                Log.i("dobanda calculata: ", String.valueOf(calculatedInterest));
                tv_interest_rate_value.setText(String.valueOf(calculatedInterest));

                float interestTax = (float) (0.1 * calculatedInterest);
                tv_interest_tax_value.setText(String.valueOf(interestTax));

                float earnings = calculatedInterest - interestTax;
                tv_earnings_value.setText(String.valueOf(earnings));

                float accumulatedValue = depositedValue + earnings;
                tv_accumulated_value.setText(String.valueOf(accumulatedValue));

                return new Deposit(depositedValue, days, interestRate, interestTax,
                        earnings, accumulatedValue);
            }
        };
    }

    private void initInterestValue(View view) {
        tv_interest_value = view.findViewById(R.id.tv_interest_value);
        tv_interest_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( tv_interest_value.getText().toString().length() == 0) {
                    tv_interest_value.setText(R.string.default_interest);
                }
                double interestValue = Float.parseFloat(tv_interest_value.getText().toString());
                if (!(interestValue != 0)) {
                    Toast.makeText(view.getContext().getApplicationContext(),
                            "The interest rate can not be zero!",
                            Toast.LENGTH_LONG).show();
                    tv_interest_value.setText(R.string.default_interest);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initSpnPeriod(View view) {
        sp_deposit_period = view.findViewById(R.id.sp_deposit_period);
    }

    private void initDepositValue(View view) {
        tv_deposit_amount_value = view.findViewById(R.id.tv_deposit_amount_value);
        tv_deposit_amount_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( tv_deposit_amount_value.getText().toString().length() == 0) {
                    tv_deposit_amount_value.setText(R.string.value_100);
                }
                int depositedValue = Integer.parseInt(tv_deposit_amount_value.getText().toString());
                if (depositedValue > 100000) {
                    Toast.makeText(view.getContext().getApplicationContext(),
                            "Deposited value must be an integer less than 100.000",
                            Toast.LENGTH_LONG).show();
                    tv_deposit_amount_value.setText(R.string.value_100);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
//        tv_deposit_amount_value.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    int depositedValue = Integer.parseInt(tv_deposit_amount_value.getText().toString());
//                    if (!( depositedValue > 0 && depositedValue <= 100000)) {
//                        Toast.makeText(view.getContext().getApplicationContext(),
//                                "Deposited value must be an integer less than 100.000",
//                                Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception ex){
//                    Toast.makeText(view.getContext().getApplicationContext(),
//                            "Deposited value must be an integer less than 100.000",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }
}
