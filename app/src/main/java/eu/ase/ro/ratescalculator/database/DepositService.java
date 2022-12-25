package eu.ase.ro.ratescalculator.database;

import android.content.Context;

import java.util.concurrent.Callable;

import eu.ase.ro.ratescalculator.asyncTask.AsyncTaskRunner;
import eu.ase.ro.ratescalculator.asyncTask.Callback;

public class DepositService {

    private final DepositDao depositDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public DepositService(Context context) {
        depositDao = DatabaseManager.getInstance(context).getDepositDao();
        asyncTaskRunner = new AsyncTaskRunner();
    }

    public void insert(Deposit deposit, Callback<Deposit> activityThread) {
        Callable<Deposit> insertOperation = new Callable<Deposit>() {
            @Override
            public Deposit call() throws Exception {
                if (deposit == null || deposit.getId() > 0) {
                    return null;
                }
                long id = depositDao.insert(deposit);
                if (id < 0) {
                    return null;
                }
                deposit.setId(id);
                return deposit;
            }
        };
        asyncTaskRunner.executeAsync(insertOperation, activityThread);
    }
}
