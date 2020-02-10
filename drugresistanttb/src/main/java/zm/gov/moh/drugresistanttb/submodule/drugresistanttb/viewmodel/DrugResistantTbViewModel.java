package zm.gov.moh.drugresistanttb.submodule.drugresistanttb.viewmodel;

import android.app.Application;
import android.content.Context;

import java.util.EnumMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
//import zm.gov.moh.core.model.Submodules;
import zm.gov.moh.drugresistanttb.model.Submodules;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.drugresistanttb.DrugResistantTbModule;
import zm.gov.moh.drugresistanttb.submodule.enrollment.view.DrugResistantTbEnrollmentActivity;

public class DrugResistantTbViewModel extends BaseAndroidViewModel {

    EnumMap<Submodules, Class> submodules;
    Context context;
    MutableLiveData<Module> startSubmodule;

    public DrugResistantTbViewModel(Application application) {
        super(application);
        submodules = new EnumMap<Submodules, Class>(Submodules.class);
        context = application;
        submodules.put(Submodules.REGISTRATION, DrugResistantTbEnrollmentActivity.class);
    }

    public void startSubmodule(int index){

        ModuleGroup drugResistantTbSubmodule = (ModuleGroup)((BaseApplication)getApplication()).getModule(DrugResistantTbModule.MODULE);

        Module module1 = drugResistantTbSubmodule.getModules().get(index);
        startSubmodule.setValue(module1);
    }

    public LiveData<Module> getStartSubmodule() {

        if(startSubmodule == null)
            startSubmodule = new MutableLiveData<>();

        return startSubmodule;
    }

    public void startSubmodule(Module module){
        startSubmodule.setValue(module);
    }
}
