package zm.gov.moh.common.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.service.SearchIndex;
import zm.gov.moh.core.service.ServiceManager;

public class BaseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            Bundle bundle;

            if(action.equals(IntentAction.REMOTE_SERVICE_COMPLETE)){

                bundle = intent.getExtras();
                String serviceName = bundle.getString(Key.SERVICE_NAME);

                switch (serviceName){

                    case ServiceManager.SERVICE_PULL_META_DATA_REMOTE:
                        ServiceManager.getInstance(context).setService(ServiceManager.SERVICE_PULL_ENTITY_REMOTE).start();
                        Toast.makeText(context,"Pulling data",Toast.LENGTH_LONG).show();
                        break;


                    case ServiceManager.SERVICE_PULL_ENTITY_REMOTE:
                        ServiceManager.getInstance(context).setService(ServiceManager.SERVICE_PUSH_ENTITY_REMOTE).start();
                        Toast.makeText(context,"Pushing data",Toast.LENGTH_LONG).show();
                        break;


                    case ServiceManager.SERVICE_PUSH_ENTITY_REMOTE:
                        Toast.makeText(context,"Sync Complete",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    }