package zm.gov.moh.core.mocks;

import java.util.List;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.utils.InjectableViewModel;

public class SQLiteDatabaseMock implements InjectableViewModel {
    protected Repository repository;
    protected Database db;


    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }
    @Override
    public Repository getRepository() {
        return repository;
    }

    public List<Long> getIDs()
    {
        return  repository.getDatabase().visitDao().getIds();
    }
}
