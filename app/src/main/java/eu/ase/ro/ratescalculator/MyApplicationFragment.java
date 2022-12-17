package eu.ase.ro.ratescalculator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.ase.ro.ratescalculator.util.ApplicationsAdapter;
import eu.ase.ro.ratescalculator.util.Credit;
import eu.ase.ro.ratescalculator.util.SubmitedData;

public class MyApplicationFragment extends Fragment {

    private SubmitedData receivedDataFilled;
    private ListView lv_applications;
    private ArrayList<SubmitedData>  submitedDataList = new ArrayList<>();

    public MyApplicationFragment() {}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SubmitedData receivedDataFilled = bundle.getParcelable("dataFilled");
            Log.i("ReceivedDataFilled555: ", receivedDataFilled.toString());

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_applications, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SubmitedData receivedDataFilled = bundle.getParcelable("dataFilled");

            Log.i("ReceivedDataFilled555: ", receivedDataFilled.toString());
            initLv(view, receivedDataFilled);

        }

        return view;
    }

    private void initLv(View view, SubmitedData receivedDataFilled) {
        submitedDataList.add(receivedDataFilled);
        if (getContext() != null) {
            lv_applications = view.findViewById(R.id.lv_applications);
            ApplicationsAdapter adapter = new ApplicationsAdapter(
                    getContext(),
                    R.layout.listview_applications,
                    submitedDataList, getLayoutInflater());
            lv_applications.setAdapter(adapter);

            adapter.notifyAdapter(lv_applications);
        }
    }
    private void notifyAdapter() {
        ApplicationsAdapter adapter = (ApplicationsAdapter) lv_applications.getAdapter();
        adapter.notifyDataSetChanged();
    }



}
