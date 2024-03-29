package eu.ase.ro.ratescalculator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

import eu.ase.ro.ratescalculator.util.Credit;
import eu.ase.ro.ratescalculator.database.SubmitedData;

public class CreditsFragment extends Fragment {

    private RadioGroup rg_credits_type;
    private RadioGroup rg_interest_rate;
    private SeekBar sb_desired_amount;
    private EditText tv_desired_amount;
    private TextView tv_first_rate_value;
    private TextView tv_total_payment_value;
    private TextView tv_interest_value;
    private Spinner sp_credit_period;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch sw_salary_porting;
    private Button btnGetOffer;
    private ConstraintLayout constraint_group;
    private ArrayList<SubmitedData> submitedDataList;

    public final String CREDIT_DETAILS = "creditDetails";

    public CreditsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credits, container, false);
        initComponents(view);

        return view;
    }

    private void initComponents(View view) {
        if (getContext() != null) {
            rg_credits_type = view.findViewById(R.id.rg_credits_type);
            rg_interest_rate = view.findViewById(R.id.rg_interest_rate);
            tv_first_rate_value = view.findViewById(R.id.tv_first_rate_value);
            tv_total_payment_value = view.findViewById(R.id.tv_total_payment_value);
            tv_interest_value = view.findViewById(R.id.tv_interest_value);
            constraint_group = view.findViewById(R.id.constraint_group);

            initDesiredAmount(view);
            initSalaryPorting(view);
            initDesiredAmountSb(view);
            initPeriod(view);
            initBtnGetOffer(view);
        }
    }

    private void initBtnGetOffer(View view) {
        btnGetOffer = view.findViewById(R.id.btn_get_offer);
        btnGetOffer.setOnClickListener(getSavedOffer());
    }

    private void initPeriod(View view) {
        sp_credit_period = view.findViewById(R.id.sp_credit_period);
        sp_credit_period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCreditDetails();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void initDesiredAmount(View view) {
        tv_desired_amount = view.findViewById(R.id.tv_desired_amount_from_sb);
        tv_desired_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_personal_loan) {
                    tv_desired_amount.setText(String.valueOf(1000));
                } else {
                    tv_desired_amount.setText(String.valueOf(200000));
                }
            }
        });

        tv_desired_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                getCreditDetails();
                constraint_group.setVisibility(getView().VISIBLE);
            }
        });
    }

    private void initSalaryPorting(View view) {
        sw_salary_porting = view.findViewById(R.id.sw_salary_porting);
        if (sw_salary_porting.isChecked()) {
            tv_interest_value.setText(String.valueOf(9.99));
        } else {
            tv_interest_value.setText(String.valueOf(11.2));
        }
        sw_salary_porting.setOnCheckedChangeListener((compoundButton, b) -> {
            if (sw_salary_porting.isChecked()) {
                tv_interest_value.setText(String.valueOf(9.99));
            } else {
                tv_interest_value.setText(String.valueOf(11.2));
            }
            getCreditDetails();
        });
    }

    private void initDesiredAmountSb(View view) {
        sb_desired_amount = view.findViewById(R.id.sb_desired_amount);
        sb_desired_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int prog = 2500;
                if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_personal_loan) {
                    tv_desired_amount.setText(String.valueOf(progress * 1000));
                } else if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_mortgage_loan) {
                    tv_desired_amount.setText(String.valueOf((progress * prog) + 200000));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb) {
                if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_personal_loan) {
                    tv_desired_amount.setText(String.valueOf(1000));
                } else if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_mortgage_loan){
                    tv_desired_amount.setText(String.valueOf(200000));
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb) {}
        });
    }

    private View.OnClickListener getSavedOffer() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (tv_desired_amount.getText().toString().length() != 0) {
                    if (creditAmountIsValid()) {
                        Credit credit = buildFromComponents();
                        Toast.makeText(getContext().getApplicationContext(),
                                credit.toString(),
                                Toast.LENGTH_LONG).show();

                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment fragment = DataFillFragment.newInstance(submitedDataList);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(CREDIT_DETAILS, credit);
                        fragment.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).addToBackStack(null).commit();

                    }
                } else {
                    Toast.makeText(getContext().getApplicationContext(),
                            R.string.info_get_offer,
                            Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private Credit buildFromComponents() {
        String loanType = getString(R.string.rg_credits_personal_loan);
        if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_mortgage_loan) {
            loanType = getString(R.string.rg_credits_mortgage_loan);
        }
        int desiredAmount = Integer.parseInt(tv_desired_amount.getText().toString());
        int period = Integer.parseInt(String.valueOf(sp_credit_period.getSelectedItem()));
        boolean collectSalary = sw_salary_porting.isChecked() ? true : false;
        float interestValue = Float.parseFloat(tv_interest_value.getText().toString());
        float firstRateValue = Float.parseFloat(tv_first_rate_value.getText().toString());
        float totalPaymentValue = Float.parseFloat(tv_total_payment_value.getText().toString());

        return new Credit(loanType, desiredAmount, period, collectSalary,
                interestValue, firstRateValue, totalPaymentValue);
    }

    private void getCreditDetails() {
        DecimalFormat df = new DecimalFormat("0.00");
        if (creditAmountIsValid()) {
            double interestValue = Double.parseDouble(tv_interest_value.getText().toString());
            if ((tv_desired_amount.getText().toString().length()) != 0) {
                int desiredAmount = Integer.parseInt(tv_desired_amount.getText().toString());
                double total_payment = (interestValue / 100 + 1) * desiredAmount;
                total_payment = Double.parseDouble(df.format(total_payment));
                tv_total_payment_value.setText(String.valueOf(total_payment));

                int period = Integer.parseInt(String.valueOf(sp_credit_period.getSelectedItem()));
                double firstRate = total_payment / ( period * 12);
                firstRate = Double.parseDouble(df.format(firstRate));

                tv_first_rate_value.setText(String.valueOf(firstRate));
            }
        }
    }

    private boolean creditAmountIsValid() {
        if ((tv_desired_amount.getText().toString().length()) != 0) {
            if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_personal_loan) {
                if (
                        !(Integer.parseInt(tv_desired_amount.getText().toString()) <= 100000)
                        || !(Integer.parseInt(tv_desired_amount.getText().toString()) >= 1000)
                ){
                    generateAlertDialog(R.string.amount_to_be_borrowed,
                            getString(R.string.personal_loan),
                            getString(R.string.value_1000),
                            getString(R.string.value_100000),
                            getString(R.string.title_personal_loan)
                    );
                    tv_desired_amount.setText(String.valueOf(100000));
                    return false;
                }
            } else if (
                    rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_mortgage_loan
                    ) {
                    if (
                            !(Integer.parseInt(tv_desired_amount.getText().toString()) <= 450000)
                            || !(Integer.parseInt(tv_desired_amount.getText().toString()) >= 200000)
                    ){
                        generateAlertDialog(R.string.amount_to_be_borrowed,
                                getString(R.string.mortgage_loan),
                                getString(R.string.value_200000),
                                getString(R.string.value_450000),
                                getString(R.string.title_mortgage_loan));
                        tv_desired_amount.setText(String.valueOf(450000));
                        return false;
                    }
                }
            }

        return true;
    }

    private void generateAlertDialog(int stringResId, String arg1, String arg2, String arg3, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getContext());
        builder.setMessage(
                getString(stringResId,
                        arg1, arg2, arg3 ));
        builder.setTitle(title);
        builder.setNegativeButton(R.string.text_OK, (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}