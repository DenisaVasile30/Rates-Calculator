package eu.ase.ro.ratescalculator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.Objects;

import eu.ase.ro.ratescalculator.asyncTask.Callback;
import eu.ase.ro.ratescalculator.database.Deposit;
import eu.ase.ro.ratescalculator.database.DepositService;
import eu.ase.ro.ratescalculator.util.Credit;

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
    private ConstraintLayout constraint_group_depo;
    private Button btnGetOffer;
    DecimalFormat df = new DecimalFormat("0.00");
    private DepositService depositService;
    private Deposit deposit;

    public DepositsFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depositService = new DepositService(getContext());
    }

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
            constraint_group_depo = view.findViewById(R.id.constraint_group_depo);
            initBtnCalculate(view);
            initBtnGetOffer(view);
        }
    }

    private void initBtnGetOffer(View view) {
        btnGetOffer = view.findViewById(R.id.btn_get_offer);
        btnGetOffer.setOnClickListener(saveDeposit());

    }

    private View.OnClickListener saveDeposit() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (deposit != null) {
                    //insert db
                    depositService.insert(deposit, insertDepositCallback());
                } else {
                    generateAlertDialog(R.string.deposit_check_failed, "Info");
                }
            }
        };
    }

    private void initBtnCalculate(View view) {
        btn_calculate = view.findViewById(R.id.btn_calculate);
        btn_calculate.setOnClickListener(generateDepositValues());
    }

    private View.OnClickListener generateDepositValues() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn_calculate.requestFocus();
                if (isValidAmount() && isValidInterest()) {
                    deposit = initDatas();
                    Toast.makeText(getContext().getApplicationContext(),
                            deposit.toString(),
                            Toast.LENGTH_LONG).show();
                    constraint_group_depo.setVisibility(view.VISIBLE);


                    resetFilledData();
                }
            }
        };
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
        calculatedInterest = Float.parseFloat(df.format(calculatedInterest));
        tv_interest_rate_value.setText(String.valueOf(calculatedInterest));

        float interestTax = (float) (0.1 * calculatedInterest);
        interestTax = Float.parseFloat(df.format(interestTax));
        tv_interest_tax_value.setText(String.valueOf(interestTax));

        float earnings = calculatedInterest - interestTax;
        earnings = Float.parseFloat(df.format(earnings));
        tv_earnings_value.setText(String.valueOf(earnings));

        float accumulatedValue = depositedValue + earnings;
        accumulatedValue = Float.parseFloat(df.format(accumulatedValue));
        tv_accumulated_value.setText(String.valueOf(accumulatedValue));

        return new Deposit(depositedValue, days, interestRate, interestTax,
                earnings, accumulatedValue);
    }

    private void resetFilledData() {
        tv_deposit_amount_value.setText(R.string.empty_string);
        tv_interest_value.setText(R.string.empty_string);
    }

    private void initInterestValue(View view) {
        tv_interest_value = view.findViewById(R.id.tv_interest_value);
        tv_interest_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!tv_interest_value.hasFocus() && !tv_interest_value.getText().toString().equals("")) {
                    isValidInterest();
                }
            }
        });
        tv_interest_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (tv_interest_value.getText().toString().length() >= 2) {
                    sp_deposit_period.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private boolean isValidInterest() {
        tv_deposit_amount_value.requestFocus();
        if (!tv_interest_value.hasFocus() && !tv_interest_value.getText().toString().equals("")) {
            double interestValue = Float.parseFloat(tv_interest_value.getText().toString());
            interestValue = Double.parseDouble(df.format(interestValue));
            if (interestValue != 0) {
                return true;
            }
        }
        generateAlertDialog(R.string.info_complete_interest, "Invalid amount");
        tv_interest_value.setText(R.string.default_interest);

        return false;
    }

    private void initSpnPeriod(View view) {
        sp_deposit_period = view.findViewById(R.id.sp_deposit_period);
    }

    private void initDepositValue(View view) {
        tv_deposit_amount_value = view.findViewById(R.id.tv_deposit_amount_value);
        tv_deposit_amount_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!tv_deposit_amount_value.hasFocus() && !tv_deposit_amount_value.getText().toString().equals("")) {
                    isValidAmount();
                }
            }
        });
    }

    private boolean isValidAmount() {
        if ( !((tv_deposit_amount_value.getText().toString()).trim()).equals("")) {
            int depositedValue = Integer.parseInt(tv_deposit_amount_value.getText().toString());
            if (depositedValue <= 100000) {
                return true;
            }
        }
        generateAlertDialog(R.string.info_maximum_depo, "Invalid amount");
        tv_deposit_amount_value.setText("");

        return false;
    }

    private void generateAlertDialog(int stringResId, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getContext());
        builder.setMessage(
                getString(stringResId));
        builder.setTitle(title);
        builder.setNegativeButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();;
    }

    private Callback<Deposit> insertDepositCallback() {
        return new Callback<Deposit>() {
            @Override
            public void runResultOnUiThread(Deposit result) {
                if (result != null) {
                    AppCompatActivity activity = (AppCompatActivity) getContext();
                    Fragment fragment = DataFillFragment.newInstance(null);
                    Bundle bundle = new Bundle();
                    bundle.putLong("idDeposit", result.getId());
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,
                                    fragment).addToBackStack(null).commit();
                }
            }
        };
    }
}
