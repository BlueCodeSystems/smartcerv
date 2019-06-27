package zm.gov.moh.common.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import zm.gov.moh.common.submodule.login.view.LoginActivity;
import zm.gov.moh.core.service.ServiceManager;

public class ToolBarEventHandler {

    Context context;
    String title;

    public ToolBarEventHandler(Context context) {
        this.context = context;
    }

    public void syncMetaData() {
        ServiceManager.getInstance(context)
                .setService(ServiceManager.Service.PULL_PATIENT_ID_REMOTE)
                .startOnComplete(ServiceManager.Service.PULL_PATIENT_ID_REMOTE, ServiceManager.Service.PULL_META_DATA_REMOTE)
                .startOnComplete(ServiceManager.Service.PULL_META_DATA_REMOTE, ServiceManager.Service.PULL_ENTITY_REMOTE)
                .startOnComplete(ServiceManager.Service.PULL_ENTITY_REMOTE, ServiceManager.Service.PUSH_ENTITY_REMOTE)
                .start();
    }

    public void onClicklogOut() {
        Intent i = new Intent(context,LoginActivity.class);
        i.putExtra("finish", true);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
        System.out.print("Clicked");
        finish();
    }

    private void finish() {
    }

    public void setTitle(String title) {
        this.title = title;
    }
        public void syncMetaData(){
            ServiceManager.getInstance(context)
                    .setService(ServiceManager.Service.PULL_PATIENT_ID_REMOTE)
                    .startOnComplete(ServiceManager.Service.PULL_PATIENT_ID_REMOTE, ServiceManager.Service.PULL_META_DATA_REMOTE)
                    .startOnComplete(ServiceManager.Service.PULL_META_DATA_REMOTE,ServiceManager.Service.PULL_ENTITY_REMOTE)
                    .startOnComplete(ServiceManager.Service.PULL_ENTITY_REMOTE, ServiceManager.Service.PUSH_ENTITY_REMOTE)
                    .startOnComplete(ServiceManager.Service.PUSH_ENTITY_REMOTE, ServiceManager.Service.SUBSTITUTE_LOCAL_ENTITY)
                    .start();
        }

    public String getTitle() {
        return title;
    }

}
