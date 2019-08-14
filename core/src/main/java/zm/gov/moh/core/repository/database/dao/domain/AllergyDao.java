package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Allergy;

@Dao
public interface AllergyDao {

    @Query("SELECT * FROM allergy")
    Allergy getAllergy();

    @Insert
    void insert(Allergy allergy);
}
