package eu.ase.ro.ratescalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import eu.ase.ro.ratescalculator.util.Credit;

public class DataFillFragment extends Fragment {

    private EditText tv_first_name;
    private EditText tv_last_name;
    private EditText tv_phone;
    private EditText tv_email;
    private DatePicker dtp;
    private TextView tv_credit_details;

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
        initComponents(view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Credit receivedCredit = bundle.getParcelable("creditDetails");
            initCreditDetailsView(receivedCredit, view);
            Log.i("creditReceivedview: ", receivedCredit.toString());
        }


        return view;
    }

    private void initCreditDetailsView(Credit receivedCredit, View view) {
        tv_credit_details = view.findViewById(R.id.tv_credit_details);
        tv_credit_details.setText(receivedCredit.toString());
    }

    private void initComponents(View view) {
        if (getContext() != null) {
            initFirstName(view);
            initLastName(view);
            initPhoneNumber(view);
            initEmail(view);
            initDatePicker(view);
        }
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
        tv_first_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isValidFirstName();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private boolean isValidFirstName() {
        if (((tv_first_name.getText().toString()).trim()).equals("")) {
            generateAlertDialog(R.string.invalid_field,
                    " first name ", "Invalid first name");
            return false;
        }
        return true;
    }

    private void initLastName(View view) {
        tv_last_name = view.findViewById(R.id.tv_last_name);
        tv_last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isValidLastName();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private boolean isValidLastName() {
        if (((tv_last_name.getText().toString()).trim()).equals("")) {
            generateAlertDialog(R.string.invalid_field,
                    " last name ", "Invalid last name");

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
