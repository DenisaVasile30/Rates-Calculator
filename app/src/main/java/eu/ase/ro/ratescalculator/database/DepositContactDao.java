package eu.ase.ro.ratescalculator.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DepositContactDao {

    @Insert
    long insert(SubmitedData submitedData);

    @Query("SELECT * FROM contacts c JOIN deposits d ON c.id_deposit = d.id_deposit")
    List<SubmitedData> getAll();

    @Delete
    int delete(SubmitedData submitedData);

    @Query("DELETE FROM contacts where id_deposit= :id_deposit")
    int deleteDepositContact(long id_deposit);

    @Query("SELECT * FROM contacts WHERE id_deposit= :id_deposit_for_edit")
    SubmitedData getItemForEdit(long id_deposit_for_edit);

    @Update
    int update(SubmitedData submitedData);
}
