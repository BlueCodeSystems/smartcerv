package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.AllergyReaction;

@Dao
public interface AllergyReactionDao {

    @Query("SELECT * FROM allergy_reaction")
    AllergyReaction getAllergyReaction();

    @Insert
    void insert(AllergyReaction allergyReaction);
}
