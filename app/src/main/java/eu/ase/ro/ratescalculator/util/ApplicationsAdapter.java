package eu.ase.ro.ratescalculator.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import eu.ase.ro.ratescalculator.R;

public class ApplicationsAdapter extends ArrayAdapter<SubmitedData> {

    private Context context;
    private int resource;
    private SubmitedData submitedData;
    private LayoutInflater inflater;
    private ArrayList<SubmitedData> submitedDataList;


//    public ApplicationsAdapter(@NonNull Context context, int resource,
//                               ArrayList<SubmitedData> submitedDataList,
//                               LayoutInflater inflater) {
//        super(context, resource, submitedDataList);
//        this.context = context;
//        this.resource = resource;
//        this.submitedDataList = new ArrayList<SubmitedData>(submitedDataList);
//
//        Log.i("data list adapteer:", String.valueOf(submitedDataList.size()));
//        //Log.i("data list elem ", String.valueOf(this.submitedDataList.get(0)));
//        this.inflater = inflater;
//    }
        public ApplicationsAdapter(@NonNull Context context, int resource,
                                   ArrayList<SubmitedData> submitedDataList) {
            super(context, resource, submitedDataList);
            this.context = context;
            this.resource = resource;
            this.submitedDataList = new ArrayList<SubmitedData>(submitedDataList);

            Log.i("data list adapteer:", String.valueOf(submitedDataList.size()));
            //Log.i("data list elem ", String.valueOf(this.submitedDataList.get(0)));

        }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view = inflater.inflate(resource, parent, false);
//        //SubmitedData submitedData = this.submitedDataList.get(position);
//        Log.i("getView", "here");
//        if (submitedDataList == null) {
//            Log.i("getView===null", "here");
//
//            return view;
//        }
//        Log.i("position", String.valueOf(position));
//        Log.i("getView!==null", "here");
//        Log.i("getIndex", String.valueOf(submitedDataList));
//
//       // Log.i("getIndex", submitedDataList.get(0));
//        for(int i = 0; i < submitedDataList.size(); i++) {
////            SubmitedData submitedData = submitedDataList.get(position);
//            SubmitedData submitedData = new SubmitedData();
//            submitedData.setFirstName((submitedDataList.get(i)).getFirstName());
//            Log.i("infor:", submitedData.getFirstName());
//            addSubmitedName(view, (submitedDataList.get(i)).getFirstName(),
//                    submitedDataList.get(i).getLastName());
//        }
//
//        return view;
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String firstName = getItem(position).getFirstName();
        String lastName = getItem(position).getLastName();
        String loanType =  ((Credit[]) getItem(position).getReceivedObject())[0].getLoanType();
        String applicationType;


        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);
        if (loanType != null && loanType.length() > 0) {
            applicationType = "Credit";
            addSpecificCredit(convertView, loanType);
        } else {
            applicationType = "Deposit";
            // to do addSpecificDeposit()
        }
       // Log.i("infor:", submitedData.getFirstName());
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


    public void notifyAdapter(ListView lv_applications) {

        ApplicationsAdapter adapter = (ApplicationsAdapter) lv_applications.getAdapter();
        adapter.notifyDataSetChanged();
    }

}
