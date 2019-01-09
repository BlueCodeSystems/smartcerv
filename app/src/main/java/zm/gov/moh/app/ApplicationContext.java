package zm.gov.moh.app;

import java.util.ArrayList;
import java.util.List;

import zm.gov.moh.app.submodule.first.point.of.care.view.FirstPointOfCareActivity;
import zm.gov.moh.app.submodule.first.point.of.contact.view.FirstPointOfContactActivity;
import zm.gov.moh.cervicalcancer.CervicalCancerModule;
import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view.CervicalCancerActivity;
import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardActivity;
import zm.gov.moh.common.submodule.form.view.FormActivity;
import zm.gov.moh.common.submodule.login.view.LoginActivity;
import zm.gov.moh.common.submodule.registration.view.RegistrationActivity;
import zm.gov.moh.common.submodule.register.view.RegisterActivity;
import zm.gov.moh.common.submodule.vitals.view.VitalsActivity;
import zm.gov.moh.core.model.submodule.BasicSubmodule;
import zm.gov.moh.core.model.submodule.BasicSubmoduleGroup;
import zm.gov.moh.core.model.submodule.SubmoduleGroup;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class ApplicationContext extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //Load common submodules
        loadSubmodule(CoreSubmodules.CLIENT_DASHOARD, new BasicSubmodule("Client Dashboard",ClientDashboardActivity.class));
        loadSubmodule(CoreSubmodules.REGISTER, new BasicSubmodule("Register",RegisterActivity.class));
        loadSubmodule(CoreSubmodules.REGISTRATION, new BasicSubmodule("Register Patient",RegistrationActivity.class));
        loadSubmodule(CoreSubmodules.FIRST_POINT_OF_CARE, new BasicSubmodule("First Point Of Care", FirstPointOfCareActivity.class));
        loadSubmodule(CoreSubmodules.FIRST_POINT_OF_CONTACT, new BasicSubmodule("First Point Of Contact",FirstPointOfContactActivity.class));
        loadSubmodule(CoreSubmodules.LOGIN, new BasicSubmodule("Login",LoginActivity.class));
        loadSubmodule(CoreSubmodules.VITALS, new BasicSubmodule("Vitals",VitalsActivity.class));
        loadSubmodule(CoreSubmodules.FORM, new BasicSubmodule("FormModel", FormActivity.class));


        //Load care service submodules
        Submodule cervicalCancerEnrollment = new BasicSubmodule(CervicalCancerModule.Submodules.CLIENT_ENROLLMENT, CervicalCancerEnrollmentActivity.class);
        Submodule cervicalCancerRegister = new BasicSubmodule(CervicalCancerModule.Submodules.CLIENT_REGISTER, zm.gov.moh.cervicalcancer.submodule.register.view.RegisterActivity.class);
        Submodule cervicalCancerPatientDashboard = new BasicSubmodule(CervicalCancerModule.Submodules.PATIENT_DASHBOARD, zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardActivity.class);

        List<Submodule> cervicalCancerSubmodules = new ArrayList<>();
        cervicalCancerSubmodules.add(cervicalCancerEnrollment);
        cervicalCancerSubmodules.add(cervicalCancerRegister);
        cervicalCancerSubmodules.add(cervicalCancerPatientDashboard);

        SubmoduleGroup cervicalCancer = new BasicSubmoduleGroup("Cervical Cancer", CervicalCancerActivity.class, cervicalCancerSubmodules);
        loadSubmodule(CervicalCancerModule.SUBMODULE, cervicalCancer);
        loadFirstPointOfCareSubmodule(cervicalCancer);
    }
}

