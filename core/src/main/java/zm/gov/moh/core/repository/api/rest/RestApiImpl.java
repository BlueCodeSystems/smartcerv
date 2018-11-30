package zm.gov.moh.core.repository.api.rest;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.model.Authentication;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierType;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;
import zm.gov.moh.core.repository.database.entity.domain.User;

public class RestApiImpl implements RestApi {

    private RestApiAdapter restAPI;
    private Disposable disposable;
    private String accessToken;
    private final int TIME_OUT = 30000;

    public RestApiImpl(RestApiAdapter restAPI, final String accessToken) {

        this.restAPI = restAPI;
        this.accessToken = accessToken;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void session(final String credentials, final Consumer<Authentication> success, final Consumer<Throwable> failure) {

           disposable = restAPI.session(credentials)
                    .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success, failure);

    }

    public void location(final Consumer<Location[]> success, final Consumer<Throwable> failure) {

        disposable = restAPI.getLocations(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    public void user(final Consumer<User[]> success, final Consumer<Throwable> failure) {

        disposable = restAPI.getUsers(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    public void provider(final Consumer<Provider[]> success, final Consumer<Throwable> failure) {

        disposable = restAPI.getProviders(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    public void personName(final Consumer<PersonName[]> success, final Consumer<Throwable> failure) {

        disposable = restAPI.getPersonNames(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    public void person(final Consumer<Person[]> success, final Consumer<Throwable> failure) {

        disposable = restAPI.getPersons(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    public void patient(final Consumer<Patient[]> success, final Consumer<Throwable> failure){

        disposable = restAPI.getPatients(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    public void patientIdentifier(final Consumer<PatientIdentifier[]> success, final Consumer<Throwable> failure){

        disposable = restAPI.getPatientIdentifiers(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    public void patientIdentifierType(final Consumer<PatientIdentifierType[]> success, final Consumer<Throwable> failure){

        disposable = restAPI.getPatientIdentifierTypes(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    public void personAddress(final Consumer<PersonAddress[]> success, final Consumer<Throwable> failure){

        disposable = restAPI.getPersonAddresses(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    @Override
    public void obs(Consumer<Obs[]> success, Consumer<Throwable> failure) {

        disposable = restAPI.getObs(this.accessToken)
                .timeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, failure);
    }

    public void onClear(){

    }
}
