package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import java.util.HashMap;

import androidx.annotation.Nullable;
import zm.gov.moh.core.model.BundleKey;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.fts.ClientNameFts;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class EncounterSubmission extends IntentService implements InjectableViewModel {

    public final static String FORM_DATA_KEY = "formdata";
    private Repository repository;

    public EncounterSubmission(){
        super("");


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        InjectorUtils.provideRepository(this, getApplication());
        HashMap<String,Object> formData = (HashMap<String,Object>)intent.getExtras().getSerializable(FORM_DATA_KEY);
        AndroidThreeTen.init(this);

        repository.consumeAsync(this::onSubmit,this::onError,formData);
    }

    public void onSubmit(HashMap<String,Object> formData){

        long providerId = (long)formData.get(BundleKey.PROVIDER_ID);
        long userId = (long)formData.get(BundleKey.USER_ID);
        long locationId = (long)formData.get(BundleKey.LOCATION_ID);

        for(Object value:formData.values())
            if(value instanceof ObsValue)
                switch (((ObsValue) value).getConceptDataType()){

                    case ConceptDataType.NUMERIC:

                        //Obs obs = new Obs()
                        break;
                }
    }



    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void onError(Throwable throwable){
        Exception e = new Exception(throwable);
    }
}