package eu.ase.ro.ratescalculator.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DepositContactDao {

    @Insert
    long insert(SubmitedData submitedData);

    @Query("SELECT * FROM contacts c JOIN deposits d ON c.id_deposit = d.id_deposit")
    List<SubmitedData> getAll();

    @Delete
    int delete(SubmitedData submitedData);

}
