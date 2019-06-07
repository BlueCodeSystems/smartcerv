package zm.gov.moh.core.repository.database.dao;

import java.util.List;

import androidx.room.Query;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

public interface Synchronizable <T>{

    T[] findEntityNotWithId(long offset, long... id);
}
