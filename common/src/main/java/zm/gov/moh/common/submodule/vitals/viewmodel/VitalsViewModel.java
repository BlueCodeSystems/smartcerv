package zm.gov.moh.common.submodule.vitals.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.util.LongSparseArray;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.arch.core.util.Function;
import zm.gov.moh.common.OpenmrsConfig;
import zm.gov.moh.common.submodule.vitals.model.Vitals;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;

public class VitalsViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    HashMap<Long,Double> values;
    Vitals vitals;
    Bundle bundle;
    Function<String,Long> conceptUuidToId, visitTypeUuidToId, encounterTypeUuidToId;

    public VitalsViewModel(Application application){
        super(application);

        values = new HashMap<>();
        conceptUuidToId = getRepository().getDatabase().conceptDao()::getConceptIdByUuid;
        visitTypeUuidToId = getRepository().getDatabase().visitTypeDao()::getIdByUuid;
        encounterTypeUuidToId = getRepository().getDatabase().encounterTypeDao()::getIdByUuid;
    }

    public Vitals getVitals() {

        if(vitals == null)
            vitals = new Vitals();

        return vitals;
    }

    public Function<Vitals,Bundle> extractVitalsData(Bundle bundle){

       return vitals -> {

           LongSparseArray<Double> conceptIdVitalValueMap = new LongSparseArray<>();
           ArrayList<String> tags = new ArrayList<>();

           bundle.putStringArrayList(Key.FORM_TAGS, tags);
           bundle.putLong(Key.ENCOUNTER_TYPE_ID, encounterTypeUuidToId.apply(OpenmrsConfig.ENCOUNTER_TYPE_UUID_VITALS));
           bundle.putLong(Key.VISIT_TYPE_ID, 1);



           //Additional If Statements to Enable single field Update
           //Checks if Vitals are not null and updates them with the entered value

           if(vitals.getHeight() != null)
                conceptIdVitalValueMap.put(conceptUuidToId.apply(OpenmrsConfig.CONCEPT_UUID_HEIGHT), Double.valueOf(vitals.getHeight().toString()));

           if(vitals.getWeight() != null)
                conceptIdVitalValueMap.put(conceptUuidToId.apply(OpenmrsConfig.CONCEPT_UUID_WEIGHT), Double.valueOf(vitals.getWeight().toString()));

           if (vitals.getTemperature() !=null)
           conceptIdVitalValueMap.put(conceptUuidToId.apply(OpenmrsConfig.CONCEPT_UUID_TEMPERATURE), Double.valueOf(vitals.getTemperature().toString()));

           if(vitals.getPulse() !=null)
           conceptIdVitalValueMap.put(conceptUuidToId.apply(OpenmrsConfig.CONCEPT_UUID_PULSE), Double.valueOf(vitals.getPulse().toString()));

           if(vitals.getRespiratory() !=null)
           conceptIdVitalValueMap.put(conceptUuidToId.apply(OpenmrsConfig.CONCEPT_UUID_RESPIRATORY_RATE), Double.valueOf(vitals.getRespiratory().toString()));

           if(vitals.getSystolicBloodPressure() !=null)
           conceptIdVitalValueMap.put(conceptUuidToId.apply(OpenmrsConfig.CONCEPT_UUID_SYSTOLIC_BLOOD_PRESSURE), Double.valueOf(vitals.getSystolicBloodPressure().toString()));

           if(vitals.getDiastolicBloodPressure() !=null)
           conceptIdVitalValueMap.put(conceptUuidToId.apply(OpenmrsConfig.CONCEPT_UUID_DIASTOLIC_BLOOD_PRESSURE), Double.valueOf(vitals.getDiastolicBloodPressure().toString()));
           //conceptIdVitalValueMap.put(conceptUuidToId.apply(OpenmrsConfig.CONCEPT_UUID_BLOOD_OXYGEN_SATURATION),Double.valueOf(vitals.getBloodOxygen().toString()));

           for (int i = 0; i < conceptIdVitalValueMap.size(); i++) {

               Long key = conceptIdVitalValueMap.keyAt(i);
               Double value = conceptIdVitalValueMap.valueAt(i);

               ObsValue<Double> obsValue = new ObsValue<>(key, ConceptDataType.NUMERIC, value);

               bundle.putSerializable(key.toString(), obsValue);
               tags.add(key.toString());
           }

           return bundle;
       };
    }

    public void onVitalsDataReceived(Bundle bundle){

        ServiceManager.getInstance(getApplication()).setService(ServiceManager.Service.PERSIST_ENCOUNTERS).putExtras(bundle).start();
    }

    public void onSubmit(Bundle bundle){

        if(vitals != null)
            getRepository().asyncFunction(extractVitalsData(bundle)::apply,this::onVitalsDataReceived, vitals,this::onError);
    }

    public void onError(Throwable throwable){

    }

}