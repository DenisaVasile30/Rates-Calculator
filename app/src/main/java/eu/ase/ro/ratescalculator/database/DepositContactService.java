package eu.ase.ro.ratescalculator.database;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.ratescalculator.asyncTask.AsyncTaskRunner;
import eu.ase.ro.ratescalculator.asyncTask.Callback;

public class DepositContactService {

    private final DepositContactDao depositContactDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public DepositContactService(Context context) {
        depositContactDao = DatabaseManager.getInstance(context).getDepositContactDao();
        asyncTaskRunner = new AsyncTaskRunner();
    }

    public void insert(SubmitedData submitedData, Callback<SubmitedData> activityThread) {
        Callable<SubmitedData> insertOperation = new Callable<SubmitedData>() {
            @Override
            public SubmitedData call() throws Exception {
                if (submitedData == null || submitedData.getId() > 0) {
                    return null;
                }
                long id = depositContactDao.insert(submitedData);
                if (id < 0) {
                    return null;
                }
                submitedData.setId_deposit(id);
                return submitedData;
            }
        };
        asyncTaskRunner.executeAsync(insertOperation, activityThread);
    }

    public void getAll(Callback<List<SubmitedData>> activityThread) {
        Callable<List<SubmitedData>> getAllOperation = new Callable<List<SubmitedData>>() {
            @Override
            public List<SubmitedData> call() throws Exception {
                return depositContactDao.getAll();
            }
        };
        asyncTaskRunner.executeAsync(getAllOperation, activityThread);
    }

    public void getItemForEdit(long id_deposit_for_edit, Callback<SubmitedData> activityThread) {
        Callable<SubmitedData> getItemForEdit = new Callable<SubmitedData>() {
            @Override
            public SubmitedData call() throws Exception {
                return depositContactDao.getItemForEdit(id_deposit_for_edit);
            }
        };
        asyncTaskRunner.executeAsync(getItemForEdit, activityThread);
    }

    public void delete(SubmitedData submitedData, Callback<Boolean> activityThread) {
        Callable<Boolean> deleteOperation = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (submitedData == null || submitedData.getId() > 0) {
                    return false;
                }
                int count = depositContactDao.delete(submitedData);
                return count > 0;
            }
        };
        asyncTaskRunner.executeAsync(deleteOperation, activityThread);
    }

    public void deleteDepositContact(long id_deposit, Callback<Boolean> activityThread) {
        Callable<Boolean> deleteItemOperation = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (id_deposit < 0) {
                    return false;
                }
                int count = depositContactDao.deleteDepositContact(id_deposit);
                return count > 0;
            }
        };
        asyncTaskRunner.executeAsync(deleteItemOperation, activityThread);
    }

    public void update(SubmitedData submitedData, Callback<Boolean> activityThread) {
        Callable<Boolean> updateItemOperation = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (submitedData == null && submitedData.getId_deposit() <= 0) {
                    return false;
                }
                int count = depositContactDao.update(submitedData);
                Log.i("count", String.valueOf(count));
                if (count <= 0) {
                    return false;
                }
                return count > 0;
            }
        };
        asyncTaskRunner.executeAsync(updateItemOperation, activityThread);
    }
}
