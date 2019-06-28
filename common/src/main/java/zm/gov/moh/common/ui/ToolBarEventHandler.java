package zm.gov.moh.common.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import zm.gov.moh.common.submodule.login.view.LoginActivity;
import zm.gov.moh.core.service.ServiceManager;

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
        Intent AppIntent = context.getPackageManager().getLaunchIntentForPackage("zm.gov.moh.app");
        ((AppCompatActivity)context).startActivity(AppIntent);
        System.out.print("clicked");
    }

    public PackageManager getPackageManager() {
        return context.getPackageManager();
    }

    public void setTitle(String title) {
        this.title = title;
    }
       

    public String getTitle() {
        return title;
    }

}
