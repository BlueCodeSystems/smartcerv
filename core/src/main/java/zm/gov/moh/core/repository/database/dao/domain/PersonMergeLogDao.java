package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.PersonMergeLog;

@Dao
public interface PersonMergeLogDao {

    @Insert
    void insert(PersonMergeLog personMergeLog);
}
