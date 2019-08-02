package zm.gov.moh.common.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;


import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.utils.BaseApplication;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class ToolBarEventHandler {

    Context context;
    String title;
    private ToolBarEventHandler mContext;

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
        ((BaseActivity)context).startModule(BaseApplication.CoreModule.BOOTSTRAP);
    }
    public PackageManager getPackageManager() {
        return context.getPackageManager();
    }

    public void setTitle(String title) {
        this.title = title;
    }

        Bundle bundle = new Bundle();



    public String getTitle() {
        return title;
    }

}


