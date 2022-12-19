package eu.ase.ro.ratescalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Callable;

import eu.ase.ro.ratescalculator.asyncTask.AsyncTaskRunner;
import eu.ase.ro.ratescalculator.asyncTask.Callback;
import eu.ase.ro.ratescalculator.network.HttpManager;

public class HomeFragment extends Fragment {

    private static final String URL_BANK_PRODUCTS = "https://jsonkeeper.com/b/CT32";
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBankProductsFromNetwork();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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
                Toast.makeText(getContext().getApplicationContext(),
                        result,
                        Toast.LENGTH_LONG).show();
            }
        };
    }
}
