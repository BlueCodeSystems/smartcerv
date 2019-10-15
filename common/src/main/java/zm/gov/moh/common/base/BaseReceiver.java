package zm.gov.moh.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import zm.gov.moh.common.R;
import zm.gov.moh.core.model.IntentAction;

public class BaseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();

                if(action != null)
                    switch (action){

                        case IntentAction.INSUFFICIENT_IDENTIFIERS_FAILED_REGISTRATION:
                            Toast.makeText(context,context.getString(R.string.insufficient_identifiers),Toast.LENGTH_LONG).show();
                            break;
                        case IntentAction.REMOTE_SYNC_COMPLETE:
                            Toast.makeText(context,"Sync complete",Toast.LENGTH_LONG).show();
                            break;
                        case IntentAction.REMOTE_SERVICE_INTERRUPTED:
                            Toast.makeText(context,"Sync interrupted",Toast.LENGTH_LONG).show();
                            break;
                    }

        }
}