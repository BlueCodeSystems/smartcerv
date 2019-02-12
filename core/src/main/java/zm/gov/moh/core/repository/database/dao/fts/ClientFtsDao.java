package zm.gov.moh.core.repository.database.dao.fts;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.fts.ClientNameFts;

@Dao
public interface ClientFtsDao {
    //get by patient and encouter type
    @Query("SELECT DISTINCT id FROM client_name_fts WHERE name MATCH :term")
    LiveData<List<Long>> findClientByTerm(String term);

    @Query("SELECT DISTINCT id FROM client_name_fts WHERE name MATCH :term")
    List<Long> findClientByTerms(String term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ClientNameFts... clientNameFts);
}
