package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LiveData;
import zm.gov.moh.common.OpenmrsConfig;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.FacilityDistrictCode;


public class CervicalCancerIDEditTextWidget extends FormEditTextWidget {

    long facilityLocationId;
    Repository repository;
    Context context;
    FacilityDistrictCode code;

    public CervicalCancerIDEditTextWidget(Context context, int weight, Repository repository){
        super(context, weight);

        this.repository = repository;
        this.context = context;

        facilityLocationId = repository.getDefaultSharePrefrences()
                .getLong(Key.LOCATION_ID, 1);

        repository.getDatabase().facilityDistrictCodeDao().getFacilitydistrictCodeByLocationId(facilityLocationId).observe((AppCompatActivity)context,this::setFacilityDistrictCode);
    }

    public void setFacilityDistrictCode(FacilityDistrictCode code){

      this.code = code;
      repository.getDatabase().patientIdentifierDao().getByLocationType(facilityLocationId, OpenmrsConfig.CCPIZ_IDENTIFIER_TYPE).observe((AppCompatActivity)context, this::appendSerial);
    }

    public void  appendSerial(List<String> identifiers){

        if(identifiers.size() > 0) {

            List<Long> serials = new ArrayList<>();
            for (String identifier : identifiers) {
                int index = identifier.lastIndexOf('-');

                long serial = Long.valueOf(identifier.subSequence(index + 1, identifier.length()).toString());

                serials.add(serial);
            }

            long preferredSerial = Collections.max(serials) + 1;
            this.setText(this.code.getDistrictCode()+"-"+this.code.getFacilityCode() +"-"+preferredSerial);
        }
    }
}
