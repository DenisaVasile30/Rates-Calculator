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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
            tv_desired_amount = view.findViewById(R.id.tv_desired_amount_from_sb);
            sp_credit_period = view.findViewById(R.id.sp_credit_period);
            tv_first_rate_value = view.findViewById(R.id.tv_first_rate_value);
            tv_total_payment_value = view.findViewById(R.id.tv_total_payment_value);
            sw_salary_porting = view.findViewById(R.id.sw_salary_porting);
            tv_interest_value = view.findViewById(R.id.tv_interes_value);
            sb_desired_amount = view.findViewById(R.id.sb_desired_amount);

            instantiateSalaryPorting(sw_salary_porting);

            sb_desired_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_personal_loan) {
                        tv_desired_amount.setText(String.valueOf(progress * 1000));
                    } else if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_mortgage_loan) {
                        tv_desired_amount.setText(String.valueOf(progress * 4500));
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
                public void onStopTrackingTouch(SeekBar sb) {
                }
            });


            sw_salary_porting.setOnCheckedChangeListener((compoundButton, b) -> {
                if (sw_salary_porting.isChecked()) {
                    tv_interest_value.setText(String.valueOf(9.99));
                } else {
                    tv_interest_value.setText(String.valueOf(11.2));
                }
                getCreditDetails();
            });

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
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void afterTextChanged(Editable editable) {
//                    if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_personal_loan) {
//                        if (Integer.parseInt(tv_desired_amount.getText().toString()) > 100000) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder( getContext());
//                            builder.setMessage(
//                                    getString(R.string.maximum_amount_to_be_borrowed,
//                                            "Personal loan", "100.000" ));
//                            builder.setTitle("Amount credit limit");
//                            builder.setNegativeButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
//                                // If user click no then dialog box is canceled.
//                                dialog.cancel();
//                            });
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();;
//                        }
//                    }

                    getCreditDetails();
                }
            });
            
            // don t forget to validate data!!!
            sp_credit_period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int period = Integer.parseInt(String.valueOf(sp_credit_period.getSelectedItem()));

                    getCreditDetails();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void instantiateSalaryPorting(Switch sw_salary_porting) {
        if (sw_salary_porting.isChecked()) {
            tv_interest_value.setText(String.valueOf("9.99"));
        } else {
            tv_interest_value.setText(String.valueOf("11.2"));
        }
    }

    private void getCreditDetails() {
        Log.i("credit details","here we go");
        if ( creditAmountIsValid()) {
            double interestValue = Double.parseDouble(tv_interest_value.getText().toString());
            if ((tv_desired_amount.getText().toString().length()) != 0) {
                int desiredAmount = Integer.parseInt(tv_desired_amount.getText().toString());
                double total_payment = (interestValue / 100 + 1) * desiredAmount;
                // int total_payment = (Integer.parseInt(String.valueOf(tv_interes_value))/100 + 1)
                //               * Integer.parseInt(tv_desired_amount.getText().toString());

                tv_total_payment_value.setText(String.valueOf(total_payment));

                int period = Integer.parseInt(String.valueOf(sp_credit_period.getSelectedItem()));
                double firstRate = total_payment / ( period * 12);

                tv_first_rate_value.setText(String.valueOf(firstRate));
            }
        }

    }

    private boolean creditAmountIsValid() {
        if ((tv_desired_amount.getText().toString().length()) != 0) {
            if (rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_personal_loan) {
                if (
                        (Integer.parseInt(tv_desired_amount.getText().toString()) > 100000)
                        || (Integer.parseInt(tv_desired_amount.getText().toString()) < 1000)
                ){
                    generateAlertDialog(R.string.amount_to_be_borrowed,
                            "Personal loan", "1000", "100.000", "Amount personal loan limit");
                    tv_desired_amount.setText(String.valueOf(100000));
                    return false;
                }
            } else if (
                    rg_credits_type.getCheckedRadioButtonId() == R.id.rg_credits_mortgage_loan
                    ) {
                    if (
                            (Integer.parseInt(tv_desired_amount.getText().toString()) > 450000)
                            || (Integer.parseInt(tv_desired_amount.getText().toString()) < 200000)
                    ){
                        generateAlertDialog(R.string.amount_to_be_borrowed,
                                "Mortgage loan", "200.000", "450.000", "Amount mortgage loan limit");
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
        builder.setNegativeButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();;
    }
}
