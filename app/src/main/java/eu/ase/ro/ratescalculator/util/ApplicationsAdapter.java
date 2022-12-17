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

import java.util.ArrayList;

import eu.ase.ro.ratescalculator.R;

public class ApplicationsAdapter extends ArrayAdapter<SubmitedData> {

    private Context context;
    private int resource;
    private SubmitedData submitedData;
    private LayoutInflater inflater;
    private ArrayList<SubmitedData> submitedDataList = new ArrayList<>();

    public ApplicationsAdapter(@NonNull Context context, int resource,
                               ArrayList<SubmitedData> submitedDataList,
                               LayoutInflater inflater) {
        super(context, resource, submitedDataList);
        this.context = context;
        this.resource = resource;
        this.submitedDataList = submitedDataList;

       // Log.i("submited data list:", submitedDataList.get(0).toString());

        this.inflater = inflater;
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view = inflater.inflate(resource, parent, false);
//        Log.i("valuuuuue111", String.valueOf(submitedDataList.size()));
////        SubmitedData submitedData = this.submitedDataList.get(position);
////        if (submitedData == null) {
////            return view;
////        }
//        //Log.i("namee:", submitedData.toString());
//
//        addSubmitedName(view, submitedData.getFirstName());
//
////        for (int i = 0; i < submitedDataList.size(); i++) {
////            addSubmitedName(view, submitedDataList.get(i).getFirstName());
////        }
//        return view;
//    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        SubmitedData submitedData = this.submitedDataList.get(position);
        if (submitedData == null) {
            return view;
        }
        Log.i("total:", String.valueOf(submitedDataList.size()));
        addSubmitedName(view, submitedDataList.get(0).getFirstName(),
                submitedDataList.get(0).getLastName());

        return view;
    }

    private void addSubmitedName(View view, String firstName, String lastName) {
        TextView textView = view.findViewById(R.id.tv_applications);
        if (firstName != null && !firstName.trim().isEmpty()) {
            String fullName = firstName + " " + lastName;
            textView.setText(fullName);
        } else {
            textView.setText(R.string.tv_empty_val);
        }
    }

    public void notifyAdapter(ListView lv_applications) {
        ApplicationsAdapter adapter = (ApplicationsAdapter) lv_applications.getAdapter();
        adapter.notifyDataSetChanged();
    }
}
