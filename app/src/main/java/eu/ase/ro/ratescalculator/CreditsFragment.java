package eu.ase.ro.ratescalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class CreditsFragment extends Fragment {

    private RadioGroup rgCreditsType;
    private RadioGroup rgInterestRate;
    private SeekBar sbDesiredAmount;
    private TextView tvDesiredAmount;
    private Spinner spCreditPeriod;
    private Switch swSalaryPorting;

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
            rgCreditsType = view.findViewById(R.id.rg_credits_type);
            rgInterestRate = view.findViewById(R.id.rg_interest_rate);
            tvDesiredAmount = view.findViewById(R.id.tv_desired_amount_from_sb);
            spCreditPeriod = view.findViewById(R.id.sp_credit_period);

            spCreditPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            sbDesiredAmount = view.findViewById(R.id.sb_desired_amount);
            sbDesiredAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    tvDesiredAmount.setText(String.valueOf(progress * 500));

                }

                @Override
                public void onStartTrackingTouch(SeekBar sb) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar sb) {
                }
            });

            swSalaryPorting = view.findViewById(R.id.sw_salary_porting);
            // don t forget to validate data!!!

        }

    }
}
