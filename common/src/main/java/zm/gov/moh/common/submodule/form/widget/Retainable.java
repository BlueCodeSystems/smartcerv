package zm.gov.moh.common.submodule.form.widget;

import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;

public interface Retainable {

    void onLastObsRetrieved(ObsEntity... obs);
    String getUuid();
}
