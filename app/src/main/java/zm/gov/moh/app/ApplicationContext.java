package zm.gov.moh.app;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;
import java.util.ArrayList;
import java.util.Formatter;
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
import static androidx.core.content.ContextCompat.getSystemService;
public class ApplicationContext extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        this.buildName =  BuildConfig.VERSION_NAME +" "+ zm.gov.moh.core.BuildConfig.BUILD_DATE;
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
    }
    /**
     * Measure used memory and give garbage collector time to free up some
     * space.
     *
     * @param callback Callback operations to be done when memory is free.
     */
    public static void waitForGarbageCollector(final Runnable callback) {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long usedMemory=runtime.totalMemory() - runtime.freeMemory();
        long availableMemory=maxMemory-usedMemory;
        //String formattedMemorySize= Formatter.formatShortFileSize(context,memorySize);
        Log.v("onCreate", "maxMemory:" + Long.toString(maxMemory));
        Debug.getNativeHeapSize();
        double availableMemoryPercentage = 1.0;
        final double MIN_AVAILABLE_MEMORY_PERCENTAGE = 0.1;
        final int DELAY_TIME = 5 * 1000;
        runtime =
                Runtime.getRuntime();
        maxMemory =
                runtime.maxMemory();
        usedMemory =
                runtime.totalMemory() -
                        runtime.freeMemory();
        availableMemoryPercentage =
                1 -
                        (double) usedMemory /
                                maxMemory;
        if (availableMemoryPercentage < MIN_AVAILABLE_MEMORY_PERCENTAGE) {
            try {
                Thread.sleep(DELAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitForGarbageCollector(
                    callback);
        } else {
            // Memory resources are available, go to next operation:
            callback.run();
        }
    }
}