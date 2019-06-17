package zm.gov.moh.core.repository.api.rest;

import java.util.List;

import io.reactivex.Maybe;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.model.IdentifiersResponseBody;
import zm.gov.moh.core.model.Response;
import zm.gov.moh.core.repository.database.entity.domain.Concept;
import zm.gov.moh.core.repository.database.entity.domain.ConceptAnswer;
import zm.gov.moh.core.repository.database.entity.domain.ConceptName;
import zm.gov.moh.core.repository.database.entity.domain.Drug;
import zm.gov.moh.core.repository.database.entity.domain.Encounter;
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
import zm.gov.moh.core.repository.database.entity.domain.Visit;
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

    @GET("person/address/")
    Maybe<PersonAddress[]> getPersonAddresses(@Header("x-access-token") String accessToken);

    @GET("person/")
    Maybe<Person[]> getPersons(@Header("x-access-token") String accessToken);

    @GET("patient/")
    Maybe<Patient[]> getPatients(@Header("x-access-token") String accessToken);

    @GET("patient/identifier/")
    Maybe<PatientIdentifier[]> getPatientIdentifiers(@Header("x-access-token") String accessToken);

    @GET("patient/identifier/type")
    Maybe<PatientIdentifierType[]> getPatientIdentifierTypes(@Header("x-access-token") String accessToken);

    @GET("obs/")
    Maybe<Obs[]> getObs(@Header("x-access-token") String accessToken);

    @GET("concept/name/")
    Maybe<ConceptName[]> getConceptNames(@Header("x-access-token") String accessToken);

    @GET("concept/answer/")
    Maybe<ConceptAnswer[]> getConceptAnswers(@Header("x-access-token") String accessToken);

    @GET("concept/")
    Maybe<Concept[]> getConcept(@Header("x-access-token") String accessToken);

    @GET("encounter/type/")
    Maybe<EncounterType[]> getEncounterTypes(@Header("x-access-token") String accessToken);

    @GET("visit/type/")
    Maybe<VisitType[]> getVisitTypes(@Header("x-access-token") String accessToken);

    @GET("drug/")
    Maybe<Drug[]> getDrugs(@Header("x-access-token") String accessToken);

    //PUT
    @Headers("Content-Type: application/json")
    @PUT("person/name/{batchVersion}")
    Maybe<Response[]> putPersonNames(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body PersonName... personNames);

    @Headers("Content-Type: application/json")
    @PUT("person/{batchVersion}")
    Maybe<Response[]> putPersons(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body Person... persons);

    @Headers("Content-Type: application/json")
    @PUT("person/address/{batchVersion}")
    Maybe<Response[]> putPersonAddresses(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body PersonAddress... personAddresses );

    @Headers("Content-Type: application/json")
    @PUT("patient/{batchVersion}")
    Maybe<Response[]> putPatients(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body Patient... patients);

    @Headers("Content-Type: application/json")
    @PUT("patient/identifier/{batchVersion}")
    Maybe<Response[]> putPatientIdentifiers(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body PatientIdentifier... patientIdentifiers);

    @Headers("Content-Type: application/json")
    @PUT("obs/{batchVersion}")
    Maybe<Response[]> putObs(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body Obs... obs);

    @Headers("Content-Type: application/json")
    @PUT("visit/{batchVersion}")
    Maybe<Response[]> putVisit(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body Visit... visits);

    @Headers("Content-Type: application/json")
    @PUT("encounter/{batchVersion}")
    Maybe<Response[]> putEncounter(@Header("x-access-token") String accessToken, @Path("batchVersion") long batchVersion, @Body Encounter... encounters);
}