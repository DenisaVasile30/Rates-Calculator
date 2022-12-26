package eu.ase.ro.ratescalculator.util;

import android.util.Log;

import androidx.annotation.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class BankProductsJsonParser {

    public static List<BankProduct> fromJson(String json) {

        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            JSONArray array = new JSONArray(json);
            return getBankProductsFromJSON(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @NonNull
    private static List<BankProduct> getBankProductsFromJSON(JSONArray array) throws JSONException {
        List<BankProduct> results = new ArrayList<>();
        Log.i("array:", array.toString());

        for (int i = 0; i < array.length(); i++) {
            List<String> period = new ArrayList<>();
            JSONObject object = array.getJSONObject(i);
            String type = object.getString("Type");
            JSONArray arrayPeriod = object.getJSONArray("Period");
            for ( int j = 0; j < arrayPeriod.length(); j++) {
                period.add(arrayPeriod.getString(j));
            }
            JSONObject bankName = object.getJSONObject("BankName");
            getBankInnerObjects(results, period, object, type, bankName);
        }

        return results;
    }

    private static List<BankProduct> getBankInnerObjects(
            List<BankProduct> results, List<String> period, JSONObject object, String type,
            JSONObject bankName
    ) throws JSONException
    {
        for (int l = 0; l < 3; l++ ){
            List<String> interest = new ArrayList<>();
            JSONObject bank = bankName.getJSONObject("Name" + (l+1));
            String bankNam = bank.getString("Name");
            String email = bank.getString("Email");
            JSONArray interestArray = bank.getJSONArray("Interest");
            for ( int z = 0; z < interestArray.length(); z++) {
                interest.add(interestArray.getString(z));
            }
            String contactNo = bank.getString("ContactNo");
            String maxAmount = object.getString("MaximumAmount");
            String minAmount = object.getString("MinimumAmount");

            Bank bankObject = new Bank(bankNam, interest, contactNo, email);
            BankProduct bankProduct = new BankProduct(type, bankObject, period, minAmount, maxAmount);
            Log.i("bank prod: ", bankProduct.toString());

            results.add(bankProduct);
        }

        return results;
    }
}
