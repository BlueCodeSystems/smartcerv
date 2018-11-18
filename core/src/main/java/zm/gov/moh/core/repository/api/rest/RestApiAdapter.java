package zm.gov.moh.core.repository.api.rest;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

public interface RestApiAdapter {

    @GET("session/")
    Maybe<Authentication> session(@Header("Authorization") String credentials);

    @GET("location/")
    Maybe<Location[]> location(@Header("x-access-token") String accesstoken);

    @GET("provider/")
    Maybe<Provider[]> provider(@Header("x-access-token") String accesstoken);

    @GET("user/")
    Maybe<User[]> user(@Header("x-access-token") String accesstoken);

    @GET("person/name/")
    Maybe<PersonName[]> personName(@Header("x-access-token") String accesstoken);

    @GET("person/address/")
    Maybe<PersonAddress[]> personAddress(@Header("x-access-token") String accesstoken);

    @GET("person/")
    Maybe<Person[]> person(@Header("x-access-token") String accesstoken);

    @GET("patient/")
    Maybe<Patient[]> patient(@Header("x-access-token") String accesstoken);

    @GET("patient/identifier/")
    Maybe<PatientIdentifier[]> patientIdentifier(@Header("x-access-token") String accesstoken);

    @GET("patient/identifier/type")
    Maybe<PatientIdentifierType[]> patientIdentifierType(@Header("x-access-token") String accesstoken);
}