package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Relationship;

@Dao
public interface RelationshipDao {

    @Query("SELECT * FROM relationship")
    Relationship getAllRelationship();

    @Insert
    void insert(Relationship relationship);
}
