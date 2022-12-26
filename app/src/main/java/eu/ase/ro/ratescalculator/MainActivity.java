package eu.ase.ro.ratescalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import eu.ase.ro.ratescalculator.database.SubmitedData;

public class MainActivity extends AppCompatActivity implements DataFillFragment.sendData {

    private ArrayList<SubmitedData> submitedDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_credits:
                            selectedFragment = new CreditsFragment();
                            break;
                        case R.id.nav_deposits:
                            selectedFragment = new DepositsFragment();
                            break;
                        case R.id.nav_my_applications:

                            selectedFragment = MyApplicationFragment.newInstance(submitedDataList);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
    };

    @Override
    public void sendFilledData(ArrayList<SubmitedData> submitedDataList) {
        this.submitedDataList.addAll(submitedDataList);
    }
}