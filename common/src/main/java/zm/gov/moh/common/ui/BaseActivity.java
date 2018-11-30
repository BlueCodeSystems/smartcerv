package zm.gov.moh.common.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.BaseToolbarBinding;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.service.MetaDataSync;

public class BaseActivity extends AppCompatActivity {


    protected static final String START_SUBMODULE_KEY = "start_submodule";
    protected static final String CALLING_SUBMODULE_KEY = "calling_submodule";
    protected static final String FORM_FRAGMENT_KEY = "FORM_FRAGMENT_KEY";
    protected static final String CLIENT_ID_KEY = "CLIENT_ID_KEY";
    protected static final String JSON_FORM_KEY = "JSON_FORM_KEY";
    public static final String ACTION_KEY = "ACTION_KEY";
    public static final String FORM_DATA_KEY = "FORM_DATA_KEY";
    public static final String START_SUBMODULE_ON_FORM_RESULT_KEY = "START_SUBMODULE_ON_FORM_RESULT_KEY";
    protected LocalBroadcastManager broadcastManager;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        broadcastReceiver = new BaseReceiver();
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter("zm.gov.moh.common.SYNC_COMPLETE_NOTIFICATION");

        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    public void startSubmodule(Submodule submodule, Bundle bundle){

        Intent intent = new Intent(this, submodule.getClassInstance());

        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    public void startSubmodule(Submodule submodule){

        this.startActivity( new Intent(this,submodule.getClassInstance()));
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

        public static final String SYNC_COMPLETE_NOFICATION = "zm.gov.moh.common.SYNC_COMPLETE_NOFICATION";

        @Override
        public void onReceive(Context context, Intent intent) {

            Toast.makeText(context,"Sync Complete",Toast.LENGTH_LONG).show();
        }
    }
}
