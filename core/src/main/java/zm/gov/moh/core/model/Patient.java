package zm.gov.moh.core.model;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeEntity;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

public class Patient {

    private LocalDateTime birthdate;
    private String gender;
    private String givenName;
    private String middleName;
    private String familyName;
    private List<PersonAttribute> attributes;
    private List<PatientIdentifier> identifiers;
    private String address1;
    private String cityVillage;
    private String stateProvince;
    private String uuid;
    private short voided;


    public void setPerson(Person person){
        this.gender = person.getGender();
        this.birthdate = person.getBirthDate();
        this.uuid = person.getUuid();
        this.voided = person.getVoided();
    }

    public void setPersonName(PersonName personName){
        this.givenName = personName.getGivenName();
        this.familyName = personName.getFamilyName();
        this.middleName = personName.getMiddleName();
    }

    public void setPersonAddress(PersonAddress personAddress){
        this.address1 = personAddress.getAddress1();
        this.cityVillage = personAddress.getCityVillage();
        this.stateProvince = personAddress.getStateProvince();
    }

    public void setIdentifiers(List<PatientIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    public void setAttributes(List<PersonAttribute> attributes) {
        this.attributes = attributes;
    }

    public static class Builder{

        protected Person person;
        protected PersonName personName;
        protected PersonAddress personAddress;
        protected List<PersonAttribute> attributes;
        protected List<PatientIdentifier> identifiers;

        public Person getPerson() {
            return person;
        }

        public Builder setPerson(Person person) {
            this.person = person;
            return this;
        }

        public PersonName getPersonName() {
            return personName;
        }

        public Builder setPersonName(PersonName personName) {
            this.personName = personName;
            return this;
        }

        public PersonAddress getPersonAddress() {
            return personAddress;
        }

        public Builder setPersonAddress(PersonAddress personAddress) {
            this.personAddress = personAddress;
            return this;
        }

        public Builder setIdentifiers(List<PatientIdentifier> identifiers) {
            this.identifiers = identifiers;
            return this;
        }

        public Builder setAttributes(List<PersonAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        public Patient build(){

            if(person != null || personName != null || personAddress != null || identifiers != null || attributes != null){

                Patient client = new Patient();

                if(person != null)
                    client.setPerson(person);
                if(personName != null)
                    client.setPersonName(personName);
                if(personAddress != null)
                    client.setPersonAddress(personAddress);
                if(identifiers != null)
                    client.setIdentifiers(identifiers);
                if(attributes != null)
                    client.setAttributes(attributes);

                return client;
            }
            else return null;
        }
    }
}