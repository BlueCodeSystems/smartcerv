package zm.gov.moh.cervicalcancer.submodule.cervicalcancer.viewmodel;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

import zm.gov.moh.cervicalcancer.CervicalCancerModule;
import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.LineChartVisitItem;
import zm.gov.moh.core.model.Submodules;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.BaseApplication;

public class CervicalCancerViewModel extends BaseAndroidViewModel {

    EnumMap<Submodules, Class> submodules;
    Context context;
    MutableLiveData<Module> startSubmodule;



    public CervicalCancerViewModel(Application application){
        super(application);

        submodules = new EnumMap<>(Submodules.class);
        context = application;
        submodules.put(Submodules.REGISTRATION, CervicalCancerEnrollmentActivity.class);
    }

    public void startSubmodule(int index){

        ModuleGroup cervicalCancerSubmodule = (ModuleGroup)((BaseApplication)getApplication()).getModule(CervicalCancerModule.MODULE);

        Module module1 = cervicalCancerSubmodule.getModules().get(index);
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

    //get all registered clients from current location
    public LiveData <Long> getAllRegisteredClients()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return  getRepository().getDatabase().patientDao().getTotalRegistered(location_id);

    }

    //get all patients seen from current location.

    public LiveData <Long> getAllseenClients()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return getRepository().getDatabase().patientDao().getTotalSeenClients(location_id);
    }

    public  LiveData<Long> getAllScreenedClients()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return getRepository().getDatabase().patientDao().getTotalScreened(location_id);

    }

    //get all patients Screened from current location for line chart

    public LiveData<List<LineChartVisitItem>> getAllScreenedInLastSevenDays()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return getRepository().getDatabase().patientDao().getTotalScreenedLineChart(location_id);
    }


    //patents today

    public LiveData<Long> getClientsRegisteredToday()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return  getRepository().getDatabase().patientDao().getTotalRegisteredClientsByDate(location_id,"-0 days");
    }

    public LiveData<Long>getClientsSeenToday()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return getRepository().getDatabase().patientDao().getTotalSeenClientsByDays(location_id,"-0 days");
    }

    public LiveData<Long>getClientsScreenedToday()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return getRepository().getDatabase().patientDao().getTotalSeenClientsByDays(location_id,"-0 days");
    }

    //get day of the month


    //client data for this month


    public LiveData<Long>getClientsRegisteredThisMonth()
    {
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);

        return getRepository().getDatabase().patientDao().getTotalRegisteredClientsByDate(location_id,"-"+String.valueOf(day)+" days");
    }


    public LiveData<Long>getClientsSeenThisMonth()
    {
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);

        return getRepository().getDatabase().patientDao().getTotalSeenClientsByDays(location_id,"-"+String.valueOf(day)+" days");

    }

    public LiveData<Long>getClientsScreenedThisMonth()
    {

        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);

        return getRepository().getDatabase().patientDao().getTotalScreenedByDays(location_id,"-"+String.valueOf(day)+" days");
    }


    //patient Data  for last three months
    public LiveData<Long>getRegisteredInLast3Months()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return  getRepository().getDatabase().patientDao().getTotalRegisteredClientsByDate(location_id,"-90 days");
    }

    public LiveData<Long> getScreenedInlast3Months()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return  getRepository().getDatabase().patientDao().getTotalScreenedByDays(location_id,"-90 days");
    }

    public LiveData<Long> getSeenInlast3Months()
    {
        long location_id=getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
        return  getRepository().getDatabase().patientDao().getTotalSeenClientsByDays(location_id,"-90 days");
    }


}






/*
*   private Repository mRepository;
    private MutableLiveData<Module> startModule;

    public BaseAndroidViewModel(Application application){
        super(application);

        InjectorUtils.provideRepository(this, application);
        startModule = new MutableLiveData<>();
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRepository = repository;
    }

    public Repository getRepository() {
        return mRepository;
    }

    public LiveData<Module> getStartSubmodule() {

        if(startModule == null)
            startModule = new MutableLiveData<>();

        return startModule;
    }

    public void startModule(Module submodule){
        startModule.setValue(submodule);
    }
* **/

