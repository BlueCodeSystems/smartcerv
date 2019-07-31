package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.OrderFrequency;

@Dao
public interface OrderTypeDao {

    @Insert
    void insert(OrderFrequency orderFrequency);
}
