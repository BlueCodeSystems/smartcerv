package zm.gov.moh.common.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.service.DataSync;
import zm.gov.moh.core.service.MetaDataSync;
import zm.gov.moh.core.service.SearchIndex;
import zm.gov.moh.core.service.ServiceName;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.BaseApplication;

import static zm.gov.moh.core.model.Key.PERSON_ADDRESS;

public class BaseActivity extends AppCompatActivity {


    protected static final String START_SUBMODULE_KEY = "start_submodule";
    protected static final String CALLING_SUBMODULE_KEY = "calling_submodule";
    protected static final String FORM_FRAGMENT_KEY = "FORM_FRAGMENT_KEY";
    public static final String CLIENT_ID_KEY = "PERSON_ID";
    public static final String JSON_FORM_KEY = "JSON_FORM_KEY";
    public static final String ACTION_KEY = "ACTION_KEY";
    public static final String FORM_DATA_KEY = "FORM_DATA_KEY";
    public static final String START_SUBMODULE_ON_FORM_RESULT_KEY = "START_SUBMODULE_ON_FORM_RESULT_KEY";
    protected LocalBroadcastManager broadcastManager;
    BroadcastReceiver broadcastReceiver;
    protected BaseAndroidViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        broadcastReceiver = new BaseReceiver();
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter("zm.gov.moh.common.SYNC_COMPLETE_NOTIFICATION");

        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    public void startModule(Module module, Bundle bundle){

        Intent intent = new Intent(this, module.getClassInstance());

        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    public void startModule(String moduleName, Bundle bundle){

        BaseApplication baseApplication = (BaseApplication)getApplication();

        Intent intent = new Intent(this,   baseApplication.getSubmodule(moduleName).getClassInstance());

        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    public void startModule(String moduleName){

        BaseApplication baseApplication = (BaseApplication)getApplication();
        Intent intent = new Intent(this,baseApplication.getSubmodule(moduleName).getClassInstance());
        this.startActivity(intent);
    }

    public void startModule(Module module){

        this.startActivity( new Intent(this,module.getClassInstance()));
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public ToolBarEventHandler getToolbarHandler(){

        return new ToolBarEventHandler(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    public class ToolBarEventHandler{

        Context context;
        String title;

        public ToolBarEventHandler(Context context){
            this.context = context;
        }

        public void syncMetaData(){
            Intent intent = new Intent(context, MetaDataSync.class);
            startService(intent);
            Toast.makeText(context,"Syncing",Toast.LENGTH_LONG).show();
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    public class BaseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            Bundle bundle;

            startService(new Intent(context, SearchIndex.class));

            if(action.equals(IntentAction.SYNC_COMPLETE)){

                bundle = intent.getExtras();
                String serviceName = bundle.getString(Key.SERVICE_NAME);

                if(serviceName.equals(ServiceName.META_DATA_SYNC)){

                    Intent intentService = new Intent(context, DataSync.class);
                    startService(intentService);
                }else if(serviceName.equals(ServiceName.DATA_SYNC)){

                    Toast.makeText(context,"Sync Complete",Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public BaseAndroidViewModel getViewModel(){
       return viewModel;
    }

    protected void setViewModel(BaseAndroidViewModel viewModel){
        this.viewModel = viewModel;
        Bundle bundle = getIntent().getExtras();

        if(bundle != null )
            viewModel.setBundle(bundle);
    }

    public void initBundle(Bundle bundle){

        final long SESSION_LOCATION_ID = getViewModel().getRepository().getDefaultSharePrefrences()
                .getLong(this.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);
        final String USER_UUID = getViewModel().getRepository().getDefaultSharePrefrences()
                .getString(this.getResources().getString(zm.gov.moh.core.R.string.logged_in_user_uuid_key), "null");
        bundle.putLong(Key.LOCATION_ID, SESSION_LOCATION_ID);

        Long personId = bundle.getLong(Key.PERSON_ID);


        getViewModel()
                .getRepository()
                .getDatabase()
                .providerUserDao()
                .getAllByUserUuid(USER_UUID)
                .observe(this, providerUser -> {

                    bundle.putLong(Key.PROVIDER_ID, providerUser.provider_id);
                    bundle.putLong(Key.USER_ID, providerUser.user_id);
                });

        if(personId != null){

            getViewModel()
                    .getRepository()
                    .getDatabase()
                    .clientDao()
                    .findById(personId)
                    .observe(this, client -> {
                        bundle.putString(Key.PERSON_GIVEN_NAME, client.given_name);
                        bundle.putString(Key.PERSON_FAMILY_NAME, client.family_name);
                    });

            getViewModel()
                    .getRepository()
                    .getDatabase()
                    .personAddressDao()
                    .findByPersonId(personId)
                    .observe(this, personAddress -> {
                       bundle.putString(PERSON_ADDRESS,personAddress.address1+" "+personAddress.city_village+" "+personAddress.state_province);
                    });
        }
    }
}
