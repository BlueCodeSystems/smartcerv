package zm.gov.moh.app.submodule.first.point.of.care.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;

import java.util.EnumMap;

import zm.gov.moh.common.submodule.register.view.RegisterActivity;
import zm.gov.moh.core.model.Submodules;
import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view.CervicalCancerActivity;

public class FirstPointOfCareViewModel extends AndroidViewModel {

    private EnumMap<Submodules,Class> submodules;
    Context context;


    public FirstPointOfCareViewModel(Application application){
        super(application);

        submodules = new EnumMap<>(Submodules.class);
        context = application;
        submodules.put(Submodules.CERVICAL_CANCER, CervicalCancerActivity.class);
        submodules.put(Submodules.FIND_PATIENT, RegisterActivity.class);
    }


    public void startSubmodule(Submodules submodule){

        context.startActivity(new Intent(context,(submodules.get(submodule))));
    }
}


