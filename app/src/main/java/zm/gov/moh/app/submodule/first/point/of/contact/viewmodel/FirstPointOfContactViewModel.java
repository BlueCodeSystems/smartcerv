package zm.gov.moh.app.submodule.first.point.of.contact.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;

import java.util.EnumMap;

import zm.gov.moh.app.submodule.first.point.of.care.view.FirstPointOfCareActivity;
import zm.gov.moh.common.submodule.registration.view.RegistrationActivity;
import zm.gov.moh.core.model.Submodules;

public class FirstPointOfContactViewModel extends AndroidViewModel {

    private EnumMap<Submodules, Class> submodules;
    Context context;

    public FirstPointOfContactViewModel(Application application){
        super(application);

        submodules = new EnumMap<>(Submodules.class);
        context = application;
        submodules.put(Submodules.REGISTRATION, RegistrationActivity.class);
        submodules.put(Submodules.FIRST_POINT_OF_CARE, FirstPointOfCareActivity.class);
    }

    public void startSubmodule(Submodules subModule){

        context.startActivity(new Intent(context, submodules.get(subModule)));
    }
}

