/*package zm.gov.moh.core;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;

import com.bumptech.glide.load.engine.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import zm.gov.moh.core.model.Patient;
import zm.gov.moh.core.model.PatientIdentifier;
import zm.gov.moh.core.model.PersonAttribute;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;
*/
/*@PrepareForTest({Context.class,
        ContentResolver.class, ContentValues.class})
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.activeandroid.content.ContentProvider")
public abstract class
ACUnitTestBase {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().silent();

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    protected void mockActiveAndroidContext() {
        Context context = PowerMockito.mock(Context.class);
        ContentResolver resolver = PowerMockito.mock(ContentResolver.class);
        ContentValues vals = PowerMockito.mock(ContentValues.class);

        try {
            PowerMockito.whenNew(ContentValues.class).withNoArguments().thenReturn(vals);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Mockito.lenient().when(context.getContentResolver()).thenReturn(resolver);
        doNothing().when(resolver).notifyChange(any(Uri.class), any(ContentObserver.class));
    }

    protected abstract Mockito doNothing();

    protected Patient createPatient(Long id) {
        Patient patient = new Patient(id, "",
                Collections.singletonList(createIdentifier(id)));
        patient.setUuid("patient_one_uuid" + id);
        updatePatientData(id, patient);
        return patient;
    }

    protected PatientIdentifier createIdentifier(Long id) {
        PatientIdentifier identifier = new PatientIdentifier();
        identifier.setIdentifier("some_identifier_" + id);
        return identifier;
    }

    protected Patient updatePatientData(Long id, Patient patient) {
        patient.setNames(Collections.singletonList(createPersonName(id)));
        patient.setAddresses(Collections.singletonList(createPersonAddress(id)));
        patient.setGender("M");
        patient.setBirthdate("25-02-2016");
        patient.setDeceased(false);
        patient.setCauseOfDeath(new Resource());
        return patient;
    }

    protected PersonAddress createPersonAddress(Long id) {
        PersonAddress personAddress = new PersonAddress();
        personAddress.setAddress1("address_1_" + id);
        personAddress.setAddress2("address_2_" + id);
        personAddress.setCityVillage("city_" + id);
        personAddress.setStateProvince("state_" + id);
        personAddress.setCountry("country_" + id);
        personAddress.setPostalCode("postal_code_" + id);
        return personAddress;
    }

    protected PersonName createPersonName(Long id) {
        PersonName personName = new PersonName();
        char alphabetic_id = (char) (id.intValue() + 'a' - 1);
        personName.setGivenName("given_name_" + alphabetic_id);
        personName.setMiddleName("middle_name_" + alphabetic_id);
        personName.setFamilyName("family_name_" + alphabetic_id);
        return personName;
    }

    protected Patient createPatient(Long id, String identifier) {
        Patient patient = createPatient(id);
        PatientIdentifier patientIdentifier = new PatientIdentifier();
        patientIdentifier.setIdentifier(identifier);
        patient.setIdentifiers(Collections.singletonList(patientIdentifier));
        return patient;
    }

    protected Person createPerson(Long id) {
        return new Person(Collections.singletonList(createPersonName(id)), "M", "25-02-2016", false, Collections.singletonList(createPersonAddress(id)), Collections.singletonList(createPersonAttributes(id)), null, new Resource(), false);
    }

    private PersonAttribute createPersonAttributes(Long id) {
        PersonAttribute personAttribute = new PersonAttribute();
        personAttribute.setValue("value");
        return personAttribute;
    }

    protected Provider createProvider(Long id, String identifier) {
        Provider provider = new Provider();
        provider.setPerson(createPerson(id));
        provider.setId(id);
        provider.setUuid(id.toString());
        provider.setRetired(false);
        provider.setIdentifier(identifier);

        return provider;
    }


    protected <T> Call<Results<T>> mockSuccessCall(List<T> list) {
        return new MockSuccessResponse<>(list);
    }


    protected <T> Call<T> mockSuccessCall(T object) {
        return new MockSuccessResponse<>(object);
    }

    protected <T> Call<T> mockErrorCall(int code) {
        return new MockErrorResponse<>(code);
    }

    protected <T> Call<T> mockFailureCall() {
        Throwable throwable = Mockito.mock(Throwable.class);
        return new MockFailure<>(throwable);
    }
}
*/