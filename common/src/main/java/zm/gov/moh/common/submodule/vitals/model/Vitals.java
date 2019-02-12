package zm.gov.moh.common.submodule.vitals.model;

import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import zm.gov.moh.common.BR;
import zm.gov.moh.core.repository.database.entity.domain.Obs;

public class Vitals extends BaseObservable {

    CharSequence height;
    CharSequence weight;
    CharSequence temperature;
    CharSequence pulse;
    CharSequence respiratory;
    CharSequence systolicBloodPressure;
    CharSequence diastolicBloodPressure;
    CharSequence bloodOxygen;

    @Bindable
    public CharSequence getHeight() {
        return height;
    }

    public void setHeight(CharSequence height) {

        if(this.height != height) {
            this.height = height;

            notifyPropertyChanged(BR.height);
        }
    }

    @Bindable
    public CharSequence getWeight() {
        return weight;
    }

    public void setWeight(CharSequence weight) {

        if(this.weight != weight) {
            this.weight = weight;
            notifyPropertyChanged(BR.weight);
        }
    }

    @Bindable
    public CharSequence getTemperature() {
        return temperature;
    }

    public void setTemperature(CharSequence temperature) {

        if(this.temperature != temperature) {
            this.temperature = temperature;
            notifyPropertyChanged(BR.temperature);
        }
    }

    @Bindable
    public CharSequence getPulse() {
        return pulse;
    }

    public void setPulse(CharSequence pulse) {

        if(this.pulse != pulse) {
            this.pulse = pulse;
            notifyPropertyChanged(BR.pulse);
        }
    }

    @Bindable
    public CharSequence getRespiratory() {
        return respiratory;
    }

    public void setRespiratory(CharSequence respiratory) {

        if(this.respiratory != respiratory) {
            this.respiratory = respiratory;
            notifyPropertyChanged(BR.respiratory);
        }
    }

    @Bindable
    public CharSequence getSystolicBloodPressure() {
        return systolicBloodPressure;
    }

    public void setSystolicBloodPressure(CharSequence systolicBloodPressure) {

        if(this.systolicBloodPressure != systolicBloodPressure) {
            this.systolicBloodPressure = systolicBloodPressure;
            notifyPropertyChanged(BR.systolicBloodPressure);
        }
    }

    @Bindable
    public CharSequence getDiastolicBloodPressure() {
        return diastolicBloodPressure;
    }

    public void setDiastolicBloodPressure(CharSequence diastolicBloodPressure) {

        if(this.diastolicBloodPressure != diastolicBloodPressure){
            this.diastolicBloodPressure = diastolicBloodPressure;
            notifyPropertyChanged(BR.diastolicBloodPressure);
        }
    }

    @Bindable
    public CharSequence getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(CharSequence bloodOxygen) {

        if(this.bloodOxygen != bloodOxygen){
            this.bloodOxygen = bloodOxygen;
            notifyPropertyChanged(BR.bloodOxygen);
        }
    }
}