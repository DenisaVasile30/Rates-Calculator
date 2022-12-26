package eu.ase.ro.ratescalculator.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import eu.ase.ro.ratescalculator.R;
import eu.ase.ro.ratescalculator.asyncTask.Callback;
import eu.ase.ro.ratescalculator.database.DepositContactService;
import eu.ase.ro.ratescalculator.database.DepositService;
import eu.ase.ro.ratescalculator.database.SubmitedData;

public class ApplicationsAdapter extends ArrayAdapter<SubmitedData> {

    private Context context;
    private int resource;
    private SubmitedData selectedItem;
    private LayoutInflater inflater;
    private ArrayList<SubmitedData> submitedDataList;

    public ApplicationsAdapter(@NonNull Context context, int resource,
                               ArrayList<SubmitedData> submitedDataList) {
        super(context, resource, submitedDataList);
        this.context = context;
        this.resource = resource;
        this.submitedDataList = new ArrayList<SubmitedData>(submitedDataList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String firstName = getItem(position).getFirstName();
        String lastName = getItem(position).getLastName();
        String applicationType;
        String loanType = "";
        if (getItem(position).getReceivedObject() != null) {
            applicationType = "Credit";
            loanType =  ((Credit[]) getItem(position).getReceivedObject())[0].getLoanType();

        } else {
            applicationType = "Deposit";
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        if (applicationType.equals("Credit")) {
            if (loanType != null && loanType.length() > 0) {
                addSpecificCredit(convertView, loanType);
            }
        } else {
            applicationType = "Deposit";
        }

        addSubmitedName(convertView, firstName, lastName);
        addSubmitedApplicationType(convertView, applicationType);

        return convertView;
    }

    private void addSubmitedApplicationType(View view, String applicationType) {
        TextView tv = view.findViewById(R.id.tv_application_type);
        tv.setText(applicationType);
    }

    private void addSpecificCredit(View convertView, String loanType) {
        addSubmitedLoanType(convertView, loanType);
    }

    private void addSubmitedLoanType(View view, String loanType) {
        TextView tv = view.findViewById(R.id.tv_appl_type);
        if (loanType != null && !loanType.trim().isEmpty()) {
            tv.setText(loanType);
        } else {
            tv.setText(R.string.tv_empty_val);
        }
    }

    private void addSubmitedName(View view, String firstName, String lastName) {
        TextView textView = view.findViewById(R.id.tv_applications);
        if (firstName != null && !firstName.trim().isEmpty()) {
            String fullName = firstName + " " + lastName;
            textView.setText(fullName);
            Log.i("text:", textView.getText().toString());
        } else {
            textView.setText(R.string.tv_empty_val);
        }
    }
}
