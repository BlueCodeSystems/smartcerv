package zm.gov.moh.core.utils;

import zm.gov.moh.core.repository.api.Repository;

public interface InjectableViewModel {

    void setRepository(Repository repository);
}
