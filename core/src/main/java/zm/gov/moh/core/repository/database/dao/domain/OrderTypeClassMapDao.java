package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.OrderTypeClassMap;

@Dao
public interface OrderTypeClassMapDao {

    @Insert
    void insert(OrderTypeClassMap orderTypeClassMap);
}
