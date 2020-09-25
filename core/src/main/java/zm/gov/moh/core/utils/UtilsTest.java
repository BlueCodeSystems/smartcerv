package zm.gov.moh.core.utils;

import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.RemoteWorker;

import static zm.gov.moh.core.service.worker.BaseWorker.db;

public class UtilsTest {
    public long[] getData()

    { long[] unpushedVisitEntityID;
        unpushedVisitEntityID =db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), RemoteWorker.Status.PUSHED.getCode());
        return unpushedVisitEntityID;
    }
}
