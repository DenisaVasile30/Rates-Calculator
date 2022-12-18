package eu.ase.ro.ratescalculator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.app.FragmentManager;
import java.util.ArrayList;


import eu.ase.ro.ratescalculator.util.ApplicationsAdapter;
import eu.ase.ro.ratescalculator.util.SubmitedData;

public class MyApplicationFragment extends Fragment {

    private SubmitedData receivedDataFilled;
    private ListView lv_applications;
    private ArrayList<SubmitedData>  submitedDataList;

    public MyApplicationFragment() {}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SubmitedData receivedDataFilled = bundle.getParcelable("dataFilled");
            submitedDataList = bundle.getParcelableArrayList("submitedDataList");
            Log.i("list size on create:", String.valueOf(submitedDataList.size()));
        }
    }

    public static MyApplicationFragment newInstance(ArrayList<SubmitedData>  submitedDataList) {
        MyApplicationFragment fragment = new MyApplicationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("submitedDataList", submitedDataList);
        fragment.setArguments(args);
        Log.i("list size new instance:", String.valueOf(submitedDataList.size()));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_applications, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            initLV(view, submitedDataList);

            Log.i("putExtraIntent:", String.valueOf(submitedDataList.size()));
        }

        return view;
    }
    private void initLV(View view, ArrayList<SubmitedData> submitedDataList) {
        Log.i("list size initLV:", String.valueOf(this.submitedDataList.size()));

        if (getContext() != null) {
            lv_applications = view.findViewById(R.id.lv_applications);
//            ApplicationsAdapter adapter = new ApplicationsAdapter(
//                    getContext(),
//                    R.layout.listview_applications,
//                    this.submitedDataList, getLayoutInflater());
//            lv_applications.setAdapter(adapter);

            ApplicationsAdapter adapter = new ApplicationsAdapter(
                    getContext(),
                    R.layout.listview_applications,
                    this.submitedDataList);
            lv_applications.setAdapter(adapter);

            //adapter.notifyAdapter(lv_applications);
        }
    }

    private void notifyAdapter() {
        ApplicationsAdapter adapter = (ApplicationsAdapter) lv_applications.getAdapter();
        adapter.notifyDataSetChanged();
    }



}
