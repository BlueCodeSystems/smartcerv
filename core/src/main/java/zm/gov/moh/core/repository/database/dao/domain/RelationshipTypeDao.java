package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.RelationshipType;

@Dao
public interface RelationshipTypeDao {

    @Query("SELECT * FROM relationship_type")
    RelationshipType getAllRelationshipTypeDao();

    @Insert
    void Insert(RelationshipType relationshipType);
}
