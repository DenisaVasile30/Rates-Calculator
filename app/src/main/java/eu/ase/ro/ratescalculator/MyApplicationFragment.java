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
import androidx.appcompat.app.AppCompatActivity;
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

    private final String DATA_FILLED = "dataFilled";
    private static final String SUBMITED_DATA_LIST = "submitedDataList";
    public static final String ID_DEPOSIT = "idDeposit";
    public static final String ID_DEPOSIT_FOR_EDIT = "idDepositForEdit";
    public static final String ID = "id";

    public MyApplicationFragment() {}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depositContactService = new DepositContactService(getContext());
        depositService = new DepositService(getContext());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            SubmitedData receivedDataFilled = bundle.getParcelable(DATA_FILLED);
            submitedDataList = bundle.getParcelableArrayList(SUBMITED_DATA_LIST);
        }
        generateAlertDialog(R.string.info_operations,
                getString(R.string.info));
    }

    public static MyApplicationFragment newInstance(ArrayList<SubmitedData>  submitedDataList) {
        MyApplicationFragment fragment = new MyApplicationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(SUBMITED_DATA_LIST, submitedDataList);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_applications, container, false);
        lv_applications = view.findViewById(R.id.lv_applications);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            initRadioButtons(view, submitedDataList);
            depositContactService.getAll(getAllDepositContactsCallback());
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

                ImageView btn_edit =  view.findViewById(R.id.ib_edit);
                btn_edit.setOnClickListener(editClickedItem(position));
            }
        };
    }

    private View.OnClickListener editClickedItem(long position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Edit",Toast.LENGTH_LONG).show();
                if (rg_btn_credit.isChecked()) {
                    generateAlertDialog(R.string.update_credit_error, getString(R.string.info));

                }   else {
                    SubmitedData submitedData = depositContactList.get((int) position);
                    Log.i("id depo: " , String.valueOf(submitedData.getId_deposit()));
                    AppCompatActivity activity = (AppCompatActivity) getContext();
                    Fragment fragment = DataFillFragment.newInstance(null);
                    Bundle bundle = new Bundle();
                    bundle.putLong(ID_DEPOSIT_FOR_EDIT, submitedData.getId_deposit());
                    bundle.putLong(ID, submitedData.getId());
                    Log.i("id: ", String.valueOf(submitedData.getId()));
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,
                                    fragment).addToBackStack(null).commit();

                }
            }
        };
    }

    private View.OnClickListener deleteClickedItem(long position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String applicationType = "";
                Log.i("button clicked at: ", String.valueOf(position));

                if (rg_btn_credit.isChecked()) {
                    submitedDataList.remove((int)position);
                    generateAlertDialog(R.string.successfully_deleted, getString(R.string.info));
                    initLV(submitedDataList);
                }   else {
                    SubmitedData submitedData = depositContactList.get((int) position);
                    depositContactService.deleteDepositContact(
                            submitedData.getId_deposit(),
                            deleteDepositContactItemCallback(submitedData.getId_deposit()));
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
                    generateAlertDialog(R.string.successfully_deleted,
                            getString(R.string.info)
                    );
                } else {
                    generateAlertDialog(R.string.error_deleted,
                            getString(R.string.info)
                    );
                }
            }
        };
    }

    private Callback<Boolean> deleteDepositItemCallback() {
        return new Callback<Boolean>() {
            @Override
            public void runResultOnUiThread(Boolean result) {

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
                    generateAlertDialog(R.string.no_data_found,
                            getString(R.string.info)
                    );
                }
            }
        };
    }

    private void initLVDeposits(List<SubmitedData> depositContactList) {

        if (getContext() != null) {
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
                    generateAlertDialog(R.string.no_data_found,
                            getString(R.string.info)
                    );
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
        builder.setNegativeButton(getString(R.string.text_OK),
                (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();;
    }


    private void initLV(ArrayList<SubmitedData> submitedDataList) {

        if (getContext() != null) {
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
