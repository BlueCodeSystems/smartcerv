package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

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
                .getLong(context.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);

        repository.getDatabase().facilityDistrictCodeDao().getFacilitydistrictCodeByLocationId(facilityLocationId).observe((AppCompatActivity)context,this::setFacilityDistrictCode);
    }

    public void setFacilityDistrictCode(FacilityDistrictCode code){

      this.code = code;
      repository.getDatabase().cervicalCancerDao().countPatientsByLocationId(facilityLocationId).observe((AppCompatActivity)context, this::appendSerial);
    }

    public void  appendSerial(Long serial){
        this.setText(this.code.district_code+"-"+this.code.facility_code +"-000"+serial);
    }
}
