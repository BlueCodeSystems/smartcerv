package zm.gov.moh.core.repository.api.rest;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.repository.database.entity.domain.Concept;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;
import zm.gov.moh.core.repository.database.entity.domain.ConceptName;
import zm.gov.moh.core.repository.database.entity.domain.EncounterType;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttribute;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.LocationTag;
import zm.gov.moh.core.repository.database.entity.domain.LocationTagMap;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierType;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;
import zm.gov.moh.core.repository.database.entity.domain.User;
import zm.gov.moh.core.repository.database.entity.domain.VisitType;

public interface RestApiAdapter {

    @GET("session/")
    Maybe<Authentication> session(@Header("Authorization") String credentials);

    @GET("location/")
    Maybe<Location[]> getLocations(@Header("x-access-token") String accesstoken);

    @GET("location/tag/")
    Maybe<LocationTag[]> getLocationTags(@Header("x-access-token") String accesstoken);

    @GET("location/tag/map/")
    Maybe<LocationTagMap[]> getLocationTagMaps(@Header("x-access-token") String accesstoken);

    @GET("location/attribute/")
    Maybe<LocationAttribute[]> getLocationAttributes(@Header("x-access-token") String accesstoken);

    @GET("location/attribute/type/")
    Maybe<LocationAttributeType[]> getLocationAttributeTypes(@Header("x-access-token") String accesstoken);

    @GET("provider/")
    Maybe<Provider[]> getProviders(@Header("x-access-token") String accesstoken);

    @GET("user/")
    Maybe<User[]> getUsers(@Header("x-access-token") String accesstoken);

    @GET("person/name/")
    Maybe<PersonName[]> getPersonNames(@Header("x-access-token") String accesstoken);

    @GET("person/address/")
    Maybe<PersonAddress[]> getPersonAddresses(@Header("x-access-token") String accesstoken);

    @GET("person/")
    Maybe<Person[]> getPersons(@Header("x-access-token") String accesstoken);

    @GET("patient/")
    Maybe<Patient[]> getPatients(@Header("x-access-token") String accesstoken);

    @GET("patient/identifier/")
    Maybe<PatientIdentifier[]> getPatientIdentifiers(@Header("x-access-token") String accesstoken);

    @GET("patient/identifier/type")
    Maybe<PatientIdentifierType[]> getPatientIdentifierTypes(@Header("x-access-token") String accesstoken);

    @GET("obs/")
    Maybe<Obs[]> getObs(@Header("x-access-token") String accesstoken);

    @GET("concept/name/")
    Maybe<ConceptName[]> getConceptNames(@Header("x-access-token") String accesstoken);

    @GET("concept/answer/")
    Maybe<ConceptAnswer[]> getConceptAnswers(@Header("x-access-token") String accesstoken);

    @GET("concept/")
    Maybe<Concept[]> getConcept(@Header("x-access-token") String accesstoken);

    @GET("encounter/type/")
    Maybe<EncounterType[]> getEncounterTypes(@Header("x-access-token") String accesstoken);

    @GET("visit/type/")
    Maybe<VisitType[]> getVisitTypes(@Header("x-access-token") String accesstoken);
}