package eu.ase.ro.ratescalculator.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DepositDao {

    @Insert
    long insert(Deposit deposit);

    @Query("DELETE FROM deposits where id_deposit= :id_deposit")
    int deleteDeposit(long id_deposit);

}
