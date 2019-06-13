package zm.gov.moh.common.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import zm.gov.moh.core.model.IntentAction;
import zm.gov.moh.core.service.ServiceManager;

public class BaseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();

                if(action.equals(IntentAction.INSUFFICIENT_IDENTIFIERS_FAILD_REGISTRATION))
                    Toast.makeText(context,"Registration unsuccessful, please sync and try again",Toast.LENGTH_LONG).show();

        }
}