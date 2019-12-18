package zm.gov.moh.app;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import zm.gov.moh.app.view.HomeActivity;
import zm.gov.moh.cervicalcancer.CervicalCancerModule;
import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view.CervicalCancerActivity;
import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardActivity;
import zm.gov.moh.common.submodule.form.view.FormActivity;
import zm.gov.moh.common.submodule.login.view.LoginActivity;
import zm.gov.moh.common.submodule.register.view.RegisterActivity;
import zm.gov.moh.common.submodule.visit.view.Visit;
import zm.gov.moh.common.submodule.settings.view.Settings;
import zm.gov.moh.common.submodule.vitals.view.VitalsActivity;
import zm.gov.moh.core.model.Criteria;
import zm.gov.moh.core.model.submodule.BasicModule;
import zm.gov.moh.core.model.submodule.BasicModuleGroup;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.drugresistanttb.DrugResistantTbModule;
import zm.gov.moh.drugresistanttb.submodule.drugresistanttb.view.DrugResistantTbActivity;
import zm.gov.moh.drugresistanttb.submodule.enrollment.view.DrugResistantTbEnrollmentActivity;

public class ApplicationContext extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        //Load common modules
        registerModule(CoreModule.CLIENT_DASHOARD, new BasicModule("Client Dashboard",ClientDashboardActivity.class));
        registerModule(CoreModule.REGISTER, new BasicModule("Register",RegisterActivity.class));
        registerModule(CoreModule.HOME, new BasicModule("home",HomeActivity.class));
        registerModule(CoreModule.LOGIN, new BasicModule("Login",LoginActivity.class));
        registerModule(CoreModule.VITALS, new BasicModule("Vitals",VitalsActivity.class));
        registerModule(CoreModule.FORM, new BasicModule("FormModel", FormActivity.class));
        registerModule(CoreModule.VISIT, new BasicModule("Visit", Visit.class));

        registerModule(CoreModule.BOOTSTRAP, new BasicModule("Bootstrap", BootstrapActivity.class));

        registerModule(CoreModule.SETTINGS, new BasicModule("Settings", Settings.class));


        //Load healthcare service modules
        zm.gov.moh.core.model.submodule.Module cervicalCancerEnrollment = new BasicModule("Client Enrollment", CervicalCancerEnrollmentActivity.class);
        zm.gov.moh.core.model.submodule.Module cervicalCancerRegister = new BasicModule("Client Register", zm.gov.moh.cervicalcancer.submodule.register.view.RegisterActivity.class);
        zm.gov.moh.core.model.submodule.Module cervicalCancerPatientDashboard = new BasicModule("Patient Dashboard", zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardActivity.class);

        registerModule(CervicalCancerModule.Submodules.CLIENT_ENROLLMENT, cervicalCancerEnrollment);
        registerModule(CervicalCancerModule.Submodules.CLIENT_REGISTER, cervicalCancerRegister);
        registerModule(CervicalCancerModule.Submodules.PATIENT_DASHBOARD, cervicalCancerPatientDashboard);

        //Add to module group
        List<zm.gov.moh.core.model.submodule.Module> cervicalCancerModules = new ArrayList<>();
        cervicalCancerModules.add(cervicalCancerEnrollment);
        cervicalCancerModules.add(cervicalCancerRegister);
        cervicalCancerModules.add(cervicalCancerPatientDashboard);

        ModuleGroup cervicalCancer = new BasicModuleGroup("Cervical Cancer", CervicalCancerActivity.class, cervicalCancerModules);
        registerModule(CervicalCancerModule.MODULE, cervicalCancer);
        loadFirstPointOfCareSubmodule(cervicalCancer);

        // Load MDR service modules
        zm.gov.moh.core.model.submodule.Module mdrEnrollment = new BasicModule("MDR Client Enrollment", DrugResistantTbEnrollmentActivity.class);
        zm.gov.moh.core.model.submodule.Module mdrRegister = new BasicModule("MDR Client Register", zm.gov.moh.drugresistanttb.submodule.register.view.DrugResistantTbRegisterActivity.class);
        zm.gov.moh.core.model.submodule.Module mdrPatientDashboard = new BasicModule("MDR Patient Dashboard", zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view.DrugResistantTbPatientDashboardActivity.class);

        registerModule(DrugResistantTbModule.Submodules.MDR_CLIENT_ENROLLMENT, mdrEnrollment);
        registerModule(DrugResistantTbModule.Submodules.MDR_CLIENT_REGISTER, mdrRegister);
        registerModule(DrugResistantTbModule.Submodules.MDR_PATIENT_DASHBOARD, mdrPatientDashboard);

        //Add module to module group
        List<zm.gov.moh.core.model.submodule.Module> mdrModules = new ArrayList<>();
        mdrModules.add(mdrEnrollment);
        mdrModules.add(mdrRegister);
        mdrModules.add(mdrPatientDashboard);

        ModuleGroup mdrTuberculosis = new BasicModuleGroup("MultiDrug-Resistant TB", DrugResistantTbActivity.class, mdrModules);
        registerModule(DrugResistantTbModule.MODULE, mdrTuberculosis);
        loadFirstPointOfCareSubmodule(mdrTuberculosis);
    }
}

