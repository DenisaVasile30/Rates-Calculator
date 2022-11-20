package eu.ase.ro.ratescalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class CreditsFragment extends Fragment {

    private RadioGroup rgCreditType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initComponents();
        return inflater.inflate(R.layout.fragment_credits, container, false);
    }

    private void initComponents() {
        rgCreditType = requireView().findViewById(R.id.rg_credits_type);

    }
}
