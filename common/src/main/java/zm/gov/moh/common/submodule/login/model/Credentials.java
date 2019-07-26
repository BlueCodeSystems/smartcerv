package zm.gov.moh.common.submodule.login.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import zm.gov.moh.common.BR;

public class Credentials extends BaseObservable {

    private CharSequence username = "";
    private CharSequence password = "";

    @Bindable
    public CharSequence getUsername() {
        return username;
    }

    @Bindable
    public CharSequence getPassword() {
        return password;
    }

    public void setPassword(final CharSequence password) {

        if(this.password != password) {

            this.password = password;
            notifyPropertyChanged(BR.password);
        }

    }

    public void setUsername(final CharSequence username) {

        if(this.username != username){

            this.username = username;
            notifyPropertyChanged(BR.username);
        }
    }

    public void clear(){
        this.setPassword("");
        this.setUsername("");
    }


    public Credentials(){

    }
}
