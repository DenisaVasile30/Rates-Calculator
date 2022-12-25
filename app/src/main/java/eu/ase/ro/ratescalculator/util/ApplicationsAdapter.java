package eu.ase.ro.ratescalculator.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import eu.ase.ro.ratescalculator.R;
import eu.ase.ro.ratescalculator.database.SubmitedData;

public class ApplicationsAdapter extends ArrayAdapter<SubmitedData> {

    private Context context;
    private int resource;
    private SubmitedData submitedData;
    private LayoutInflater inflater;
    private ArrayList<SubmitedData> submitedDataList;

    public ApplicationsAdapter(@NonNull Context context, int resource,
                               ArrayList<SubmitedData> submitedDataList) {
        super(context, resource, submitedDataList);
        this.context = context;
        this.resource = resource;
        this.submitedDataList = new ArrayList<SubmitedData>(submitedDataList);

        Log.i("data list adapteer:", String.valueOf(submitedDataList.size()));
        //Log.i("data list elem ", String.valueOf(this.submitedDataList.get(0)));

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
            Log.i("onCredit", "!!");

        } else {
            applicationType = "Deposit";
            Log.i("on deposit", "!!");
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);
        if (applicationType.equals("Credit")) {
            if (loanType != null && loanType.length() > 0) {
                addSpecificCredit(convertView, loanType);
            }
        } else {
            applicationType = "Deposit";
            ImageButton btn_delete = (ImageButton) convertView.findViewById(R.id.ib_delete);
            btn_delete.setOnClickListener(deleteClickedItem());
            Log.i("weAreOnTheRight", "!!");
            // to do addSpecificDeposit()
        }

       // Log.i("infor:", submitedData.getFirstName());
        addSubmitedName(convertView, firstName, lastName);
        addSubmitedApplicationType(convertView, applicationType);


        return convertView;
    }

    private View.OnClickListener deleteClickedItem() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Button",Toast.LENGTH_LONG).show();
                Log.i("value tv: ","good!");
            }
        };
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


    public void notifyAdapter(ListView lv_applications) {

        ApplicationsAdapter adapter = (ApplicationsAdapter) lv_applications.getAdapter();
        adapter.notifyDataSetChanged();
    }

}
