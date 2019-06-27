package zm.gov.moh.core.repository.database.dao.custom;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.custom.Identifier;

@Dao
public interface IdentifierDao {

    @Query("SELECT * FROM identifier WHERE assigned = :assigned")
    Identifier getIdentifier(short assigned);

    @Query("SELECT * FROM identifier WHERE assigned != 1")
    Identifier getIdentifierNotAssigned();

    @Query("SELECT COUNT(identifier) FROM identifier WHERE assigned = :assigned")
    int count(final short assigned);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Identifier... identifier);
}
