package zm.gov.moh.common.submodule.form.widget;

import zm.gov.moh.core.repository.database.entity.domain.Obs;

public interface Retainable {

    void onLastObsRetrieved(Obs obs);
    String getUuid();
}
