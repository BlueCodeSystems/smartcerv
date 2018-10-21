package zm.gov.moh.app.submodule.modulesdrawer.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;

import java.util.EnumMap;
import zm.gov.moh.common.component.login.view.LoginActivity;
import zm.gov.moh.core.utils.ClassHolder;
import zm.gov.moh.smartcerv.component.submoduledrawer.view.SmartCervSubModulesActivity;

public class ModulesDrawerViewModel extends AndroidViewModel {

    private EnumMap<Submodules,Class> modules;
    Context context;


    public ModulesDrawerViewModel(Application application){
        super(application);

        modules = new EnumMap<>(Submodules.class);
        context = application;
        modules.put(Submodules.SMARTCERV, SmartCervSubModulesActivity.class);

    }


    public void startSubModule(Submodules module){

        Intent intent = new Intent(context, LoginActivity.class);

        intent.putExtra(ClassHolder.KEY, new ClassHolder(modules.get(module)));
        context.startActivity(intent);
    }

    public enum Submodules {
        SMARTCERV
    }
}


