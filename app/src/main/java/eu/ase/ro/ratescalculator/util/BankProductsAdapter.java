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
import java.util.List;

import eu.ase.ro.ratescalculator.R;

public class BankProductsAdapter extends ArrayAdapter<BankProduct> {

    private Context context;
    private int resource;
    private List<BankProduct> bankProductArrayList;


    public BankProductsAdapter(@NonNull Context context, int resource,
                               List<BankProduct> bankProductArrayList)
    {
        super(context, resource, bankProductArrayList);
        this.context = context;
        this.resource = resource;
        this.bankProductArrayList = new ArrayList<BankProduct>(bankProductArrayList);

        Log.i("data list adapteer:", String.valueOf(bankProductArrayList.size()));
        //Log.i("data list elem ", String.valueOf(this.submitedDataList.get(0)));

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initListViewItems(position, parent);
    }

    private View initListViewItems(int position, @NonNull ViewGroup parent) {
        @Nullable View convertView;
        String productType = getItem(position).getType();
        String bankName = getItem(position).getBankName().getName();
        List<String> interest = getItem(position).getBankName().getInterest();
        String interestValues = interest.get(0) + " - "
                                + interest.get(interest.size()-1) + " %";
        String amountValues = getItem(position).getMinimumAmount() + " - ";
        amountValues += getItem(position).getMaximumAmount() + " RON";
        String contactInfo = "Email: " + getItem(position).getBankName().getEmail() + "\n"
                + "Contact number: " + getItem(position).getBankName().getContactNo();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        if ( productType != null && !productType.trim().isEmpty()) {
            addProductType(convertView, productType);
        }
        if (bankName != null && !bankName.trim().isEmpty()) {
            addBankName(convertView, bankName);
        }
        if (interestValues != null && !interestValues.trim().isEmpty()) {
            addInterestValues(convertView, interestValues);
        }
        if (amountValues != null && !amountValues.trim().isEmpty()) {
            addAmountValues(convertView, amountValues);
        }
        if (contactInfo != null && !contactInfo.trim().isEmpty()) {
            addContactInfo(convertView, contactInfo);
        }

        return convertView;
    }

    private void addContactInfo(View view, String contactInfo) {
        TextView tv = view.findViewById(R.id.tv_contact_info);
        tv.setText(contactInfo);
    }

    private void addAmountValues(View view, String amountValues) {
        TextView tv = view.findViewById(R.id.tv_amount_values);
        tv.setText(amountValues);

    }

    private void addInterestValues(View view, String interestValues) {
        TextView tv = view.findViewById(R.id.tv_interest_values);
        tv.setText(interestValues);
    }

    private void addBankName(View view, String bankName) {
        TextView tv = view.findViewById(R.id.tv_bank_name);
        tv.setText(bankName);
    }

    private void addProductType(View view, String productType) {
        TextView tv = view.findViewById(R.id.tv_product_type);
        tv.setText(productType);
    }
}
