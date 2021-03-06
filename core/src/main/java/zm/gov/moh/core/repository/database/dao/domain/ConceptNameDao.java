package zm.gov.moh.core.repository.database.dao.domain;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.domain.Concept;
import zm.gov.moh.core.repository.database.entity.domain.ConceptName;

@Dao
public interface ConceptNameDao {

    @Query("SELECT * FROM concept_name")
    List<ConceptName> getAll();

    @Query("SELECT name FROM concept_name WHERE concept_id = :id AND locale = :locale AND locale_preferred = :localePreferred")
    String getConceptNameByConceptId(long id,String locale, short localePreferred);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ConceptName... conceptAnswers);
}
