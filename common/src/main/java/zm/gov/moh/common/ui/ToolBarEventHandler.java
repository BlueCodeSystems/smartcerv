package zm.gov.moh.common.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import zm.gov.moh.common.submodule.form.widget.DatePickerWidget;
import zm.gov.moh.core.BuildConfig;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.ServiceManager;

public class ToolBarEventHandler{

        Context context;
        String title;

        Bundle bundle = new Bundle();

        public ToolBarEventHandler(Context context){
            this.context = context;
        }

        public void syncMetaData(){
            bundle.putSerializable(Key.ENTITY_TYPE, EntityType.PATIENT);


            ServiceManager.getInstance(context)
                    .setService(ServiceManager.Service.PULL_PATIENT_ID_REMOTE)
                    .startOnComplete(ServiceManager.Service.PULL_PATIENT_ID_REMOTE, ServiceManager.Service.PULL_META_DATA_REMOTE)
                    .startOnComplete(ServiceManager.Service.PULL_META_DATA_REMOTE,ServiceManager.Service.PUSH_ENTITY_REMOTE)
                    .start();
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }