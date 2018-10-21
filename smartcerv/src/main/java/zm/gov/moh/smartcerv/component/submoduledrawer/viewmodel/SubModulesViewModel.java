package zm.gov.moh.smartcerv.component.submoduledrawer.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import java.util.EnumMap;
import zm.gov.moh.common.component.registration.view.RegistrationActivity;

public class SubModulesViewModel extends AndroidViewModel {

    EnumMap<SubModules, Class> submodules;
    Context context;

    public SubModulesViewModel(Application application){
        super(application);

        submodules = new EnumMap<>(SubModules.class);
        context = application;
        submodules.put(SubModules.REGISTRATION, RegistrationActivity.class);
    }

    public void launchSubmodule(SubModules subModule){

        context.startActivity(new Intent(context, submodules.get(subModule)));
    }

    public enum SubModules{
        REGISTRATION,
        FIND_PATIENT
    }
}

