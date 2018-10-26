package zm.gov.moh.core.repository.api;

import java.util.List;

import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.dao.domain.PersonAddressDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonNameDao;
import zm.gov.moh.core.repository.database.entity.derived.Client;


public interface Repository {

    RestApi getRestApi(String token);
    PersonDao getPersonDao();
    PersonNameDao getPersonNameDao();
    PersonAddressDao getPersonAddressDao();
    PersonAttributeDao getPersonAttributeDao();
    PersonAttributeTypeDao getPersonAttributeTypeDao();

    List<Client> getAllClients();
    Client getClientById(long id);
}
