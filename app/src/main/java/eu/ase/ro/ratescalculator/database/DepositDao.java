package eu.ase.ro.ratescalculator.database;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface DepositDao {

    @Insert
    long insert(Deposit deposit);


}
