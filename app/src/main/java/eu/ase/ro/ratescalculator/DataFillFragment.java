package eu.ase.ro.ratescalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import java.time.LocalDate;
import java.util.ArrayList;

import eu.ase.ro.ratescalculator.util.ApplicationsAdapter;
import eu.ase.ro.ratescalculator.util.Credit;
import eu.ase.ro.ratescalculator.util.SubmitedData;

public class DataFillFragment extends Fragment {

    private EditText tv_first_name;
    private EditText tv_last_name;
    private EditText tv_phone;
    private EditText tv_email;
    private DatePicker dtp;
    private TextView tv_credit_details;
    private Button btn_Cancel;
    private Button btn_Sumbit;
    private int day;
    private int month;
    private int year;
    private Credit receivedCredit;





    private SubmitedData receivedDataFilled;
    private ListView lv_applications;
    private ArrayList<SubmitedData>  submitedDataList = new ArrayList<>();

    public DataFillFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Credit receivedCredit = bundle.getParcelable("creditDetails");

            Log.i("creditDetailsReceived: ", receivedCredit.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fill_data, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedCredit = bundle.getParcelable("creditDetails");
            initCreditDetailsView(receivedCredit, view);
            Log.i("creditReceivedview: ", receivedCredit.toString());
        }
        initComponents(view, receivedCredit, inflater, container);

        return view;
    }


    private void initCreditDetailsView(Credit receivedCredit, View view) {
        tv_credit_details = view.findViewById(R.id.tv_credit_details);
        tv_credit_details.setText(receivedCredit.toString());
    }

    private void initComponents(View view, Credit receivedCredit, LayoutInflater inflater, ViewGroup container) {
        if (getContext() != null) {
            initFirstName(view);
            initLastName(view);
            initPhoneNumber(view);
            initEmail(view);
            initDatePicker(view);
            initCancelButton(view);
            initSubmitButton(view, receivedCredit);




        }
    }

//    private void initAdapter(LayoutInflater inflater, ViewGroup container) {
//
//        View view = inflater.inflate(R.layout.fragment_my_applications, container, false);
//        lv_applications =  (ListView) view.findViewById(R.id.lv_applications);
//        Object[] obj = null;
//        SubmitedData submitedData = new SubmitedData("firstName", "lastName",
//                "07xxxxxxxx", "emal@email.com", "10/10/2022",
//                obj);
//        submitedDataList.add(submitedData);
//        ApplicationsAdapter adapter = new ApplicationsAdapter(
//                view.getContext(),
//                R.layout.fragment_my_applications,
//                submitedDataList, getLayoutInflater());
//        lv_applications.setAdapter(adapter);
//    }

// varianta ok
//    private void initSubmitButton(View view, Credit receivedCredit) {
//        btn_Sumbit = view.findViewById(R.id.btn_submit);
//        btn_Sumbit.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onClick(View view) {
//                if (checkValidData()) {
//                    SubmitedData submitedData = buildFromComponents(receivedCredit);
//
//                   // initLv(view,submitedData);
//
//                    Toast.makeText(getContext().getApplicationContext(),
//                            submitedData.toString(),
//                            Toast.LENGTH_LONG).show();
//                    Log.i("newObjectCreated:", submitedData.toString());
//
//                    MyApplicationFragment fragment = new MyApplicationFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("dataFilled", submitedData);
//                    fragment.setArguments(bundle);
//                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                    activity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_container,
//                                    fragment).addToBackStack(null).commit();
//                }
//            }
//        });
//        // de creat listview si de migrat in fragment nou: my applications
//    }

    // test
    private void initSubmitButton(View view, Credit receivedCredit) {
        btn_Sumbit = view.findViewById(R.id.btn_submit);

        btn_Sumbit.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (checkValidData()) {
                    SubmitedData submitedData = buildFromComponents(receivedCredit);
                    submitedDataList.add(receivedDataFilled);
                    // initLv(view,submitedData);

                    Toast.makeText(getContext().getApplicationContext(),
                            submitedData.toString(),
                            Toast.LENGTH_LONG).show();
                    Log.i("newObjectCreated:", submitedData.toString());
                    MyApplicationFragment fragment = new MyApplicationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("dataFilled", submitedData);
                    fragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,
                                    fragment).addToBackStack(null).commit();

                }
            }
        });

    }
    private void notifyAdapter() {
        ApplicationsAdapter adapter = (ApplicationsAdapter) lv_applications.getAdapter();
        adapter.notifyDataSetChanged();
    }


    private SubmitedData buildFromComponents(Credit receivedCredit) {
        String firstName = tv_first_name.getText().toString();
        String lastName = tv_last_name.getText().toString();
        String phoneNumber = tv_phone.getText().toString();
        String email = tv_email.getText().toString();
        String dateToBeContacted = (String.valueOf(dtp.getDayOfMonth()) + "/"
                + String.valueOf(dtp.getMonth()) + "/"
                + String.valueOf(dtp.getYear()) + "/"
        );
        Log.i("receivedObject:", receivedCredit.toString());
        return new SubmitedData(firstName, lastName, phoneNumber,
                email, dateToBeContacted, new Credit[]{receivedCredit});
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkValidData() {
        if (isValidFirstName() && isValidLastName()
            && isValidPhone() && isValidEmail()
            && isValidDate()) {

            return true;

        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isValidDate() {
        day = dtp.getDayOfMonth();
        month = dtp.getMonth() + 1;
        year = dtp.getYear();

        LocalDate currentDate = LocalDate.now();
        if (day >= currentDate.getDayOfMonth()
            && month >= currentDate.getMonthValue()
            && year >= currentDate.getYear()) {

            return true;
        }

        return false;
    }

    private void initCancelButton(View view) {
        btn_Cancel = view.findViewById(R.id.btn_cancel);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreditsFragment fragment = new CreditsFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                fragment).addToBackStack(null).commit();
            }
        });

    }

    private void initDatePicker(View view) {
        dtp = view.findViewById(R.id.dtp);
    }

    private void initEmail(View view) {
        tv_email = view.findViewById(R.id.tv_email);
        tv_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!tv_email.hasFocus() && !tv_email.getText().toString().equals("")) {
                    isValidEmail();
                }
            }
        });
    }

    private boolean isValidEmail() {
        String email = tv_email.getText().toString();
        if (email.contains("@")) {
            if ((email.indexOf("@") >= 5) && (email.length() > (email.indexOf("@") + 4))
                && !(Character.isDigit(email.charAt(0)))
            ){
                return true;
            }
        }
        generateAlertDialog(R.string.invalid_field,
                " e-mail ", "E-mail format: xxxxx@******");
        tv_email.setText("");

        return false;
    }

    private void initPhoneNumber(View view) {
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!tv_phone.hasFocus() && !tv_phone.getText().toString().equals("")) {
                    isValidPhone();
                }
            }
        });


    }

    private boolean isValidPhone() {
        if (((tv_phone.getText().toString().trim()).length() != 10) ) {
            if (!((tv_phone.getText().toString()).trim().substring(0, 1)).equals("07")) {
                generateAlertDialog(R.string.invalid_field,
                        " phone number ", "Phone number format: 07xxxxxxxx");
                tv_phone.setText("");
                return false;
            }
        }

        return true;
    }

    private void initFirstName(View view) {
        tv_first_name = view.findViewById(R.id.tv_first_name);
        tv_first_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!tv_first_name.hasFocus() && !tv_first_name.getText().toString().equals("")) {
                    isValidFirstName();
                }
            }
        });
    }

    private boolean isValidFirstName() {
        if (((tv_first_name.getText().toString()).trim()).equals("")
            || tv_first_name.getText().toString().length() <= 3) {
            generateAlertDialog(R.string.invalid_field,
                    " first name ", "Invalid first name");
            tv_first_name.setText("");
            return false;
        }
        return true;
    }

    private void initLastName(View view) {
        tv_last_name = view.findViewById(R.id.tv_last_name);
        tv_last_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!tv_last_name.hasFocus() && !tv_last_name.getText().toString().equals("")) {
                    isValidLastName();
                }
            }
        });
    }

    private boolean isValidLastName() {
        if (((tv_last_name.getText().toString()).trim()).equals("")
            || tv_last_name.getText().toString().length() <= 3) {
            generateAlertDialog(R.string.invalid_field,
                    " last name ", "Invalid last name");

            tv_last_name.setText("");
            return false;
        }
        Log.i("hereeeeeeeee", "df");
        return true;
    }

    private void generateAlertDialog(int stringResId, String arg1, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getContext());
        builder.setMessage(
                getString(stringResId, arg1));
        builder.setTitle(title);
        builder.setNegativeButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();;
    }
}
