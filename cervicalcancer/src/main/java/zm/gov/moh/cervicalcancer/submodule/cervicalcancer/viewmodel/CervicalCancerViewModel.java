package zm.gov.moh.cervicalcancer.submodule.cervicalcancer.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import java.util.EnumMap;
import zm.gov.moh.common.submodule.registration.view.RegistrationActivity;
import zm.gov.moh.core.model.Submodules;

public class CervicalCancerViewModel extends AndroidViewModel {

    EnumMap<Submodules, Class> submodules;
    Context context;

    public CervicalCancerViewModel(Application application){
        super(application);

        submodules = new EnumMap<>(Submodules.class);
        context = application;
        submodules.put(Submodules.REGISTRATION, RegistrationActivity.class);
    }

    public void startSubmodule(Submodules subModule){

        context.startActivity(new Intent(context, submodules.get(subModule)));
    }

}

