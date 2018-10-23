package zm.gov.moh.app.submodule.first.point.of.care.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;

import java.util.EnumMap;
import zm.gov.moh.common.component.login.view.LoginActivity;
import zm.gov.moh.core.utils.SerializedClassInstance;
import zm.gov.moh.smartcerv.component.submoduledrawer.view.SmartCervSubModulesActivity;
import zm.gov.moh.app.submodule.first.point.of.care.model.Submodules;

public class FirstPointOfCareViewModel extends AndroidViewModel {

    private EnumMap<Submodules,Class> submodules;
    Context context;


    public FirstPointOfCareViewModel(Application application){
        super(application);

        submodules = new EnumMap<>(Submodules.class);
        context = application;
        submodules.put(Submodules.SMARTCERV, SmartCervSubModulesActivity.class);

    }


    public void startSubModule(Submodules submodule){

        Intent intent = new Intent(context, LoginActivity.class);

        intent.putExtra(SerializedClassInstance.KEY, new SerializedClassInstance(submodules.get(submodule)));
        context.startActivity(intent);
    }
}


