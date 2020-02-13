package zm.gov.moh.core.repository.database.entity;

import org.threeten.bp.LocalDateTime;

public abstract class SynchronizableEntity {
    public abstract long getId();
    public abstract LocalDateTime getDateCreated();
    public abstract LocalDateTime getDateChanged();

}
