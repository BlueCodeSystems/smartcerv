package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.Orders;

@Dao
public interface OrdersDao {

    @Insert
    void insert(Orders orders);
}
