package zm.gov.moh.core.repository.database.dao.domain;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import zm.gov.moh.core.repository.database.entity.domain.Allergy;
import zm.gov.moh.core.repository.database.entity.domain.Concept;

@Dao
public interface AllergyDao {

    @Query("SELECT * FROM allergy")
    Allergy getAllergy();

    @Insert
    void insert(Allergy allergy);
}
