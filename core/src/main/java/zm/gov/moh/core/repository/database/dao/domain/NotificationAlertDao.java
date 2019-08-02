package zm.gov.moh.core.repository.database.dao.domain;

import androidx.room.*;

import zm.gov.moh.core.repository.database.entity.domain.NotificationAlert;

@Dao
public interface NotificationAlertDao {

    @Insert
    void insert(NotificationAlert notificationAlert);
}
