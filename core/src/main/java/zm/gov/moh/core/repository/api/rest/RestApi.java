package zm.gov.moh.core.repository.api.rest;

import java.util.List;

import io.reactivex.functions.Consumer;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierType;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;
import zm.gov.moh.core.repository.database.entity.domain.User;

public interface RestApi {

    public void session(final String credentials, final Consumer<Authentication> success, final Consumer<Throwable> failure);

    public void location(final Consumer<Location[]> success, final Consumer<Throwable> failure);

    public void user(final Consumer<User[]> success, final Consumer<Throwable> failure);

    public void provider(final Consumer<Provider[]> success, final Consumer<Throwable> failure);

    public void personName(final Consumer<PersonName[]> success, final Consumer<Throwable> failure);

    public void person(final Consumer<Person[]> success, final Consumer<Throwable> failure);

    public void patient(final Consumer<Patient[]> success, final Consumer<Throwable> failure);

    public void patientIdentifier(final Consumer<PatientIdentifier[]> success, final Consumer<Throwable> failure);

    public void patientIdentifierType(final Consumer<PatientIdentifierType[]> success, final Consumer<Throwable> failure);

     void personAddress(final Consumer<PersonAddress[]> success, final Consumer<Throwable> failure);

    String getAccessToken();

    void onClear();
}
