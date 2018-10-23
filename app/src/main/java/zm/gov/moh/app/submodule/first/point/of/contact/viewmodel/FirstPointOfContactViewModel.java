package zm.gov.moh.app.submodule.first.point.of.contact.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;

import java.util.EnumMap;

import zm.gov.moh.app.submodule.first.point.of.contact.model.Submodules;
import zm.gov.moh.common.component.registration.view.RegistrationActivity;

public class FirstPointOfContactViewModel extends AndroidViewModel {

    EnumMap<Submodules, Class> submodules;
    Context context;

    public FirstPointOfContactViewModel(Application application){
        super(application);

        submodules = new EnumMap<>(Submodules.class);
        context = application;
        submodules.put(Submodules.REGISTRATION, RegistrationActivity.class);
    }

    public void startSubmodule(Submodules subModule){

        context.startActivity(new Intent(context, submodules.get(subModule)));
    }
}

