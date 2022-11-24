package eu.ase.ro.ratescalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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


//            ArrayAdapter<CharSequence> adapter =
//                    ArrayAdapter.createFromResource(view.getContext(), R.array.sp_credit_period,
//                    android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            sp_credit_period.setAdapter(adapter);
//
//            sp_credit_period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    sp_credit_period.getSelectedItem();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });

            sb_desired_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    tv_desired_amount.setText(String.valueOf(progress * 500));
                }

                @Override
                public void onStartTrackingTouch(SeekBar sb) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar sb) {
                }
            });


            sw_salary_porting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (sw_salary_porting.isChecked()) {
                        tv_interest_value.setText(String.valueOf(9.99));
                    } else {
                        tv_interest_value.setText(String.valueOf(11.2));
                    }
                    getCreditDetails();
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
                    getCreditDetails();
                }
            });
            
            // don t forget to validate data!!!
            sp_credit_period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
        double interestValue = Double.parseDouble(tv_interest_value.getText().toString());
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
