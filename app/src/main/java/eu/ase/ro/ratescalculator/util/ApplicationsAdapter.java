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
    private DepositContactService depositContactService;
    private DepositService depositService;

    public ApplicationsAdapter(@NonNull Context context, int resource,
                               ArrayList<SubmitedData> submitedDataList) {
        super(context, resource, submitedDataList);
        this.context = context;
        this.resource = resource;
        this.submitedDataList = new ArrayList<SubmitedData>(submitedDataList);
        setDepositContactService();
        setDepositService();
        Log.i("data list adapteer:", String.valueOf(submitedDataList.size()));
        //Log.i("data list elem ", String.valueOf(this.submitedDataList.get(0)));

    }

    public void setDepositContactService() {
        this.depositContactService = new DepositContactService(this.context);
    }

    public void setDepositService() {
        this.depositService = new DepositService(this.context);
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

            // to do addSpecificDeposit()
        }
        ImageView btn_delete =  convertView.findViewById(R.id.ib_delete);
        btn_delete.setOnClickListener(deleteClickedItem(position, applicationType));
        Log.i("weAreOnTheRight", "!!");
       // Log.i("infor:", submitedData.getFirstName());
        addSubmitedName(convertView, firstName, lastName);
        addSubmitedApplicationType(convertView, applicationType);


        return convertView;
    }

    private View.OnClickListener deleteClickedItem(int position, String applicationType) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Button",Toast.LENGTH_LONG).show();
                Log.i("button clicked at: ", String.valueOf(position));
                Log.i("subm : ", String.valueOf(submitedDataList.get(0).toStringContacts()));
                SubmitedData submitedData = submitedDataList.get(position);
                Log.i("id depo: " , String.valueOf(submitedData.getId_deposit()));
               // depositContactService.delete(submitedData, deleteDepositContactCallback(position));
                if (applicationType == "Deposit") {
                    depositContactService.deleteDepositContact(
                            submitedData.getId_deposit(),
                            deleteDepositContactItemCallback(submitedData.getId_deposit()));
                } else {
                    submitedDataList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(getContext().getApplicationContext(),
                            R.string.successfully_deleted,
                            Toast.LENGTH_LONG).show();
                }

            }
        };
    }

    private Callback<Boolean> deleteDepositContactItemCallback(long id_deposit) {
        return new Callback<Boolean>() {
            @Override
            public void runResultOnUiThread(Boolean result) {
                if (result) {
                    depositService.deleteDeposit(id_deposit, deleteDepositItemCallback());
                    Log.i("sters cu succes: ", "!");
                    Toast.makeText(getContext().getApplicationContext(),
                            R.string.successfully_deleted,
                            Toast.LENGTH_LONG).show();
                } else {
                    Log.i("sters cu succes: ", "!");
                    Toast.makeText(getContext().getApplicationContext(),
                            R.string.error_deleted,
                            Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private Callback<Boolean> deleteDepositItemCallback() {
        return new Callback<Boolean>() {
            @Override
            public void runResultOnUiThread(Boolean result) {
                if (result) {
                } else {
                    Log.i("eroare depo: ", "!");
                }
            }
        };
    }

    private Callback<Boolean> deleteDepositContactCallback(int position) {
        return new Callback<Boolean>() {
            @Override
            public void runResultOnUiThread(Boolean result) {
                if (result) {
                    Log.i("sters cu succes", "!!");
                } else {
                    Log.i("eroare stergere", "!!");
                }
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
