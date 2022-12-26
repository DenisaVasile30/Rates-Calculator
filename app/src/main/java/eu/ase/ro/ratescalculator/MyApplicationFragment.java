package eu.ase.ro.ratescalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


import eu.ase.ro.ratescalculator.asyncTask.Callback;
import eu.ase.ro.ratescalculator.database.DepositContactService;
import eu.ase.ro.ratescalculator.database.DepositService;
import eu.ase.ro.ratescalculator.util.ApplicationsAdapter;
import eu.ase.ro.ratescalculator.database.SubmitedData;
import eu.ase.ro.ratescalculator.util.Credit;

public class MyApplicationFragment extends Fragment {

    private SubmitedData receivedDataFilled;
    private ListView lv_applications;
    private ArrayList<SubmitedData>  submitedDataList;

    private RadioButton rg_btn_credit;
    private RadioButton rg_btn_deposit;

    DepositContactService depositContactService;
    private List<SubmitedData> depositContactList = new ArrayList<>();

    SubmitedData selectedItemPosition = new SubmitedData();
    private DepositService depositService;


    public MyApplicationFragment() {}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depositContactService = new DepositContactService(getContext());
        depositService = new DepositService(getContext());
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
        lv_applications = view.findViewById(R.id.lv_applications);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
//            initLV(submitedDataList);
            initRadioButtons(view, submitedDataList);
            depositContactService.getAll(getAllDepositContactsCallback());
            Log.i("putExtraIntent:", String.valueOf(submitedDataList.size()));
        }

        return view;
    }

    private AdapterView.OnItemClickListener getSelectedItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SubmitedData selectedItem = (SubmitedData) lv_applications.getItemAtPosition(i);
                long position = lv_applications.getItemIdAtPosition(i);
                Log.i("selected value: ", selectedItem.toStringContacts() );
                Log.i("selected position: ", String.valueOf(position));

                ImageView btn_delete =  view.findViewById(R.id.ib_delete);
                btn_delete.setOnClickListener(deleteClickedItem(position));
            }
        };
    }

    private View.OnClickListener deleteClickedItem(long position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String applicationType = "";
                Toast.makeText(getContext(), "Button",Toast.LENGTH_LONG).show();
                Log.i("button clicked at: ", String.valueOf(position));

                if (rg_btn_credit.isChecked()) {
                    Log.i("on credit delete", "!!");
                    applicationType = "Credit";
                    Log.i("size before del: ", String.valueOf(submitedDataList.size()));
                    submitedDataList.remove((int)position);
                    Log.i("size after del: ", String.valueOf(submitedDataList.size()));
                    generateAlertDialog(R.string.successfully_deleted, "Info");
                    initLV(submitedDataList);
                }   else {
                    applicationType = "Deposit";
                    SubmitedData submitedData = depositContactList.get((int) position);
                    Log.i("id depo: " , String.valueOf(submitedData.getId_deposit()));
                    depositContactService.deleteDepositContact(
                            submitedData.getId_deposit(),
                            deleteDepositContactItemCallback(submitedData.getId_deposit()));
                    Log.i("on deposit", "!!");
                    initLVDeposits(depositContactList);

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
                    generateAlertDialog(R.string.successfully_deleted, "Info");
                } else {
                    Log.i("sters cu succes: ", "!");
                    generateAlertDialog(R.string.error_deleted, "Info");
                }
            }
        };
    }

    private Callback<Boolean> deleteDepositItemCallback() {
        return new Callback<Boolean>() {
            @Override
            public void runResultOnUiThread(Boolean result) {
                if (result) {
                    Log.i("sters cu succes: ", "!");
                } else {
                    Log.i("eroare depo: ", "!");
                }
            }
        };
    }

    private void initRadioButtons(View view, ArrayList<SubmitedData> submitedDataList) {
        initBtnCredit(view, submitedDataList);
        initBtnDeposit(view);
    }

    private void initBtnDeposit(View view) {
        rg_btn_deposit = view.findViewById(R.id.rg_deposit);
        rg_btn_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depositContactService.getAll(getAllDepositContactsCallback());
            }
        });
    }

    private Callback<List<SubmitedData>> getAllDepositContactsCallback() {
        return new Callback<List<SubmitedData>>() {
            @Override
            public void runResultOnUiThread(List<SubmitedData> result) {
                if (result != null) {
                    depositContactList.clear();
                    depositContactList.addAll(result);
                    Log.i("depositContact: ", String.valueOf(depositContactList.size()));

                    initLVDeposits(depositContactList);

                } else {
                    generateAlertDialog(R.string.no_data_found, "Info");

                }
            }
        };
    }

    private void initLVDeposits(List<SubmitedData> depositContactList) {
        Log.i("list size initLVDepo:", String.valueOf(this.depositContactList.size()));

        if (getContext() != null) {
//            lv_applications = view.findViewById(R.id.lv_applications);
            ApplicationsAdapter adapter = new ApplicationsAdapter(
                    getContext(),
                    R.layout.listview_applications,
                    (ArrayList<SubmitedData>) this.depositContactList);

            lv_applications.setOnItemClickListener(getSelectedItem());

            lv_applications.setAdapter(adapter);
        }
    }

    private void initBtnCredit(View view, ArrayList<SubmitedData> submitedDataList) {
        rg_btn_credit = view.findViewById(R.id.rg_credit);
        rg_btn_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (submitedDataList.size() > 0) {
                    initLV(submitedDataList);
                } else {
                    generateAlertDialog(R.string.no_data_found, "Info");
                    rg_btn_deposit.setChecked(true);

                }
            }
        });
    }


    private void generateAlertDialog(int stringResId, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getContext());
        builder.setMessage(
                getString(stringResId));
        builder.setTitle(title);
        builder.setNegativeButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();;
    }


    private void initLV(ArrayList<SubmitedData> submitedDataList) {
        Log.i("list size initLV:", String.valueOf(this.submitedDataList.size()));

        if (getContext() != null) {
//            lv_applications = view.findViewById(R.id.lv_applications);
            ApplicationsAdapter adapter = new ApplicationsAdapter(
                    getContext(),
                    R.layout.listview_applications,
                    this.submitedDataList);
            lv_applications.setAdapter(adapter);
        }
    }

    private void notifyAdapter() {
        ApplicationsAdapter adapter = (ApplicationsAdapter) lv_applications.getAdapter();
        adapter.notifyDataSetChanged();
    }
}
