package zm.gov.moh.core.repository.api.rest;

import io.reactivex.Maybe;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.model.IdentifiersResponseBody;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.repository.database.entity.domain.Concept;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;
import zm.gov.moh.core.repository.database.entity.domain.ConceptName;
import zm.gov.moh.core.repository.database.entity.domain.Drug;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.EncounterType;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttribute;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.LocationTag;
import zm.gov.moh.core.repository.database.entity.domain.LocationTagMap;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierEntity;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierType;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;
import zm.gov.moh.core.repository.database.entity.domain.ProviderAttribute;
import zm.gov.moh.core.repository.database.entity.domain.ProviderAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.User;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitType;

public interface RestApi {

    @GET("session/")
    Maybe<Authentication> session(@Header("Authorization") String credentials);

    @GET("idgen/{source}/{batchSize}/")
    Maybe<IdentifiersResponseBody> getIdentifiers(@Header("x-access-token") String accessToken, @Path("source") int source, @Path("batchSize") int batchSize);

    @GET("location/")
    Maybe<Location[]> getLocations(@Header("x-access-token") String accessToken);

    @GET("location/tag/")
    Maybe<LocationTag[]> getLocationTags(@Header("x-access-token") String accessToken);

    @GET("location/tag/map/")
    Maybe<LocationTagMap[]> getLocationTagMaps(@Header("x-access-token") String accessToken);

    @GET("location/attribute/")
    Maybe<LocationAttribute[]> getLocationAttributes(@Header("x-access-token") String accessToken);

    @GET("location/attribute/type/")
    Maybe<LocationAttributeType[]> getLocationAttributeTypes(@Header("x-access-token") String accessToken);

    @GET("provider/")
    Maybe<Provider[]> getProviders(@Header("x-access-token") String accessToken);

    @GET("user/")
    Maybe<User[]> getUsers(@Header("x-access-token") String accessToken);

    @GET("person/name/")
    Maybe<PersonName[]> getPersonNames(@Header("x-access-token") String accessToken);

    @GET("person/name/{datetime}")
    Maybe<PersonName[]> getPersonNames(@Header("x-access-token") String accessToken, @Path("datetime") String datetime);

    @GET("person/address/")
    Maybe<PersonAddress[]> getPersonAddresses(@Header("x-access-token") String accessToken);

    @GET("person/address/{datetime}")
    Maybe<PersonAddress[]> getPersonAddresses(@Header("x-access-token") String accessToken, @Path("datetime") String datetime);

    @GET("person/{datetime}")
    Maybe<Person[]> getPersons(@Header("x-access-token") String accessToken, @Path("datetime") String datetime);

    @GET("patient/{datetime}")
    Maybe<PatientEntity[]> getPatients(@Header("x-access-token") String accessToken, @Path("datetime") String datetime);

    @GET("patient/identifier/{datetime}")
    Maybe<PatientIdentifierEntity[]> getPatientIdentifiers(@Header("x-access-token") String accessToken, @Path("datetime") String datetime);

    @GET("patient/identifier/type")
    Maybe<PatientIdentifierType[]> getPatientIdentifierTypes(@Header("x-access-token") String accessToken);

    @GET("obs/")
    Maybe<ObsEntity[]> getObs(@Header("x-access-token") String accessToken);

    @GET("obs/{datetime}")
    Maybe<ObsEntity[]> getObs(@Header("x-access-token") String accessToken,  @Path("datetime") String datetime);

    @GET("concept/name/")
    Maybe<ConceptName[]> getConceptNames(@Header("x-access-token") String accessToken);

    @GET("concept/answer/")
    Maybe<ConceptAnswer[]> getConceptAnswers(@Header("x-access-token") String accessToken);

    @GET("concept/")
    Maybe<Concept[]> getConcept(@Header("x-access-token") String accessToken);

    @GET("encounter/type/")
    Maybe<EncounterType[]> getEncounterTypes(@Header("x-access-token") String accessToken);

    @GET("encounter/")
    Maybe<EncounterEntity[]> getEncounters(@Header("x-access-token") String accessToken);

    @GET("encounter/{datetime}")
    Maybe<EncounterEntity[]> getEncounters(@Header("x-access-token") String accessToken, @Path("datetime") String datetime);

    @GET("visit/type/")
    Maybe<VisitType[]> getVisitTypes(@Header("x-access-token") String accessToken);

    @GET("visit/")
    Maybe<VisitEntity[]> getVisit(@Header("x-access-token") String accessToken);

    @GET("visit/{datetime}")
    Maybe<VisitEntity[]> getVisit(@Header("x-access-token") String accessToken,  @Path("datetime") String datetime);

    @GET("drug/")
    Maybe<Drug[]> getDrugs(@Header("x-access-token") String accessToken);

    @GET("provider/attribute")
    Maybe<ProviderAttribute[]> getProviderAttribute(@Header("x-access-token") String accessToken);

    @GET("provider/attribute/type")
    Maybe<ProviderAttributeType[]> getProviderAttributeType(@Header("x-access-token") String accessToken);

    //PUT
    @Headers("Content-Type: application/json")
    @PUT("patient/{batchVersion}")
    Maybe<Response[]> putPatients(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body Patient... patients);

    @Headers("Content-Type: application/json")
    @PUT("visit/{batchVersion}")
    Maybe<Response[]> putVisit(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body Visit... visits);

    @Headers("Content-Type: application/json")
    @PUT("encounter/{batchVersion}")
    Maybe<Response[]> putEncounter(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body EncounterEntity... encounters);
}