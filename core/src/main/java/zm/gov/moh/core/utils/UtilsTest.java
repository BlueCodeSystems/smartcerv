package zm.gov.moh.core.utils;

import zm.gov.moh.core.model.User;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.RemoteWorker;

public class UtilsTest {
    private static Object User;

    public static User createUser(int i) {
        return (zm.gov.moh.core.model.User) User;
    }

    public long[] getData()

    { long[] unpushedVisitEntityID;
        Database db = null;
        unpushedVisitEntityID = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), RemoteWorker.Status.PUSHED.getCode());
        return unpushedVisitEntityID;
    }
}
