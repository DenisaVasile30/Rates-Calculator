package eu.ase.ro.ratescalculator.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Deposit.class, SubmitedData.class}, exportSchema = false, version = 2)
public abstract class DatabaseManager extends RoomDatabase {

    private static DatabaseManager databaseManager;
    private static final String DB_NAME = "rates_calculator_db";

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(
                            context,
                            DatabaseManager.class,
                            DB_NAME
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }

        return databaseManager;
    }

    public abstract DepositDao getDepositDao();
    public abstract DepositContactDao getDepositContactDao();
}
