package eu.ase.ro.ratescalculator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.ratescalculator.asyncTask.AsyncTaskRunner;
import eu.ase.ro.ratescalculator.asyncTask.Callback;
import eu.ase.ro.ratescalculator.network.HttpManager;
import eu.ase.ro.ratescalculator.util.BankProduct;
import eu.ase.ro.ratescalculator.util.BankProductsAdapter;
import eu.ase.ro.ratescalculator.util.BankProductsJsonParser;

public class HomeFragment extends Fragment {

    private static final String URL_BANK_PRODUCTS = "https://api.npoint.io/f5b36fb8a09a04f1d290";
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private List<BankProduct> bankProducts = new ArrayList<>();
    private ListView lv_bank_products;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBankProductsFromNetwork();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    private void getBankProductsFromNetwork() {
        Callable<String> asyncOperation = new HttpManager(URL_BANK_PRODUCTS);
        Callback<String> mainThreadOperation = getMainThreadOperationForBankProducts();

        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
    }

    @NonNull
    private Callback<String> getMainThreadOperationForBankProducts() {
        return new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                Toast.makeText(getContext(),
                        result,
                        Toast.LENGTH_LONG).show();
                bankProducts.addAll(BankProductsJsonParser.fromJson(result));
                setBankProductsAdapter(getView());
            }
        };
    }

    private void setBankProductsAdapter(View view) {
        if (getContext() != null) {
            lv_bank_products = view.findViewById(R.id.lv_bank_products);
            BankProductsAdapter adapter = new BankProductsAdapter(
                    getContext(),
                    R.layout.listview_bank_products,
                    this.bankProducts);
            lv_bank_products.setAdapter(adapter);
        }
    }
}
