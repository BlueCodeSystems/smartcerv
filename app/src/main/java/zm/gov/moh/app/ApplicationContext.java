package zm.gov.moh.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import zm.gov.moh.core.model.Criteria;
import zm.gov.moh.core.model.submodule.BasicSubmodule;
import zm.gov.moh.core.model.submodule.BasicSubmoduleGroup;
import zm.gov.moh.core.model.submodule.SubmoduleGroup;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class ApplicationContext extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //Enrollment criteria
        Map<String,String> condition = new HashMap<>();
        condition.put("enrollment","yes");

        Criteria enrollmentCriteria = new Criteria(condition);

        //Load common submodules
        registerModule(CoreModule.CLIENT_DASHOARD, new BasicSubmodule("Client Dashboard",ClientDashboardActivity.class));
        registerModule(CoreModule.REGISTER, new BasicSubmodule("Register",RegisterActivity.class));
        registerModule(CoreModule.REGISTRATION, new BasicSubmodule("Register Patient",RegistrationActivity.class));
        registerModule(CoreModule.FIRST_POINT_OF_CARE, new BasicSubmodule("First Point Of Care", FirstPointOfCareActivity.class));
        registerModule(CoreModule.FIRST_POINT_OF_CONTACT, new BasicSubmodule("Point Of Contact",FirstPointOfContactActivity.class));
        registerModule(CoreModule.LOGIN, new BasicSubmodule("Login",LoginActivity.class));
        registerModule(CoreModule.VITALS, new BasicSubmodule("Vitals",VitalsActivity.class));
        registerModule(CoreModule.FORM, new BasicSubmodule("FormModel", FormActivity.class));

        //Load care service submodules
        Submodule cervicalCancerEnrollment = new BasicSubmodule("Client Enrollment", CervicalCancerEnrollmentActivity.class);
        Submodule cervicalCancerRegister = new BasicSubmodule("Client Register", zm.gov.moh.cervicalcancer.submodule.register.view.RegisterActivity.class);
        Submodule cervicalCancerPatientDashboard = new BasicSubmodule("Patient Dashboard", zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardActivity.class);

        registerModule(CervicalCancerModule.Submodules.CLIENT_ENROLLMENT, cervicalCancerEnrollment);
        registerModule(CervicalCancerModule.Submodules.CLIENT_REGISTER, cervicalCancerRegister);
        registerModule(CervicalCancerModule.Submodules.PATIENT_DASHBOARD, cervicalCancerPatientDashboard);

        //Add to module group
        List<Submodule> cervicalCancerSubmodules = new ArrayList<>();
        cervicalCancerSubmodules.add(cervicalCancerEnrollment);
        cervicalCancerSubmodules.add(cervicalCancerRegister);
        cervicalCancerSubmodules.add(cervicalCancerPatientDashboard);

        SubmoduleGroup cervicalCancer = new BasicSubmoduleGroup("Cervical Cancer", CervicalCancerActivity.class, cervicalCancerSubmodules);
        registerModule(CervicalCancerModule.MODULE, cervicalCancer);
        loadFirstPointOfCareSubmodule(cervicalCancer);
    }
}

