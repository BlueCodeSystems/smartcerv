package zm.gov.moh.common.submodule.registration.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import zm.gov.moh.common.BR;

public class RegistrationFormData extends BaseObservable {
    
    private CharSequence firstName;
    private CharSequence lastName;
    private CharSequence gender;
    private CharSequence dateOfBirth;
    private CharSequence address1;
    private CharSequence district;
    private CharSequence province;


    //getters
    @Bindable
    public CharSequence getLastName() {
        return lastName;
    }

    @Bindable
    public CharSequence getAddress1() {
        return address1;
    }

    @Bindable
    public CharSequence getDateOfBirth() {
        return dateOfBirth;
    }

    @Bindable
    public CharSequence getDistrict() {
        return district;
    }

    @Bindable
    public CharSequence getFirstName() {
        return firstName;
    }

    @Bindable
    public CharSequence getGender() {
        return gender;
    }

    @Bindable
    public CharSequence getProvince() {
        return province;
    }

    //setters
    public void setLastName(CharSequence secondName) {
        
        if(this.lastName != secondName){

            this.lastName = secondName;
            notifyPropertyChanged(BR.lastName);
        }
        
    }

    public void setFirstName(CharSequence firstName) {

        if(this.firstName != firstName){

            this.firstName = firstName;
            notifyPropertyChanged(BR.firstName);
        }
    }

    public void setAddress1(CharSequence address1) {

        if(this.address1 != address1){

            this.address1 = address1;
            notifyPropertyChanged(BR.address1);
        }
    }

    public void setDateOfBirth(CharSequence dateOfBirth) {

        if(this.dateOfBirth != dateOfBirth){

            this.dateOfBirth = dateOfBirth;
            notifyPropertyChanged(BR.dateOfBirth);
        }
    }

    public void setDistrict(CharSequence district) {

        if(this.district != district){

            this.district = district;
            notifyPropertyChanged(BR.district);
        }
    }

    public void setGender(CharSequence gender) {

        if(this.gender != gender){

            this.gender = gender;
            notifyPropertyChanged(BR.gender);
        }
    }

    public void setProvince(CharSequence province) {

        if(this.province != province){

            this.province = province;
            notifyPropertyChanged(BR.province);
        }
    }
}