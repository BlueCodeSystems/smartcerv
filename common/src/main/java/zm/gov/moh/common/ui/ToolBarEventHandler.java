package zm.gov.moh.common.ui;

import android.content.Context;
import android.widget.Toast;

import zm.gov.moh.core.service.ServiceManager;

public class ToolBarEventHandler{

        Context context;
        String title;

        public ToolBarEventHandler(Context context){
            this.context = context;
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

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }