package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import zm.gov.moh.core.repository.database.entity.domain.Drug;

@Dao
public interface DrugDao {

    @Query("SELECT * FROM drug WHERE uuid = :uuid")
    LiveData<Drug> getDrugNameByUuid(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Drug... drugNames);
}
