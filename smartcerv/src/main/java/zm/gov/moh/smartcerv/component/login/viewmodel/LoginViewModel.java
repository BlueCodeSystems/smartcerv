package zm.gov.moh.smartcerv.component.login.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.util.Log;

import zm.gov.moh.smartcerv.component.login.model.Credintials;

public class LoginViewModel extends AndroidViewModel {

    public LoginViewModel(Application application){
        super(application);
    }

    public void submitCredentials(Credintials credintials){
        Log.d("credentials submitted", "\"Are these the credentials you submitted: ");
    }

}