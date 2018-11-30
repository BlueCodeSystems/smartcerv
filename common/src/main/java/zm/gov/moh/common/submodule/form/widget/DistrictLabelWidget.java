package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;

import java.util.HashMap;

import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.derived.ProviderUser;
import zm.gov.moh.core.repository.database.entity.domain.Location;

public class DistrictLabelWidget extends LinearLayoutCompat{

    private AppCompatTextView label;
    private AppCompatTextView value;
    private HashMap<String,Object> formaData;
    private Repository repository;
    private Context context;

    public DistrictLabelWidget(Context context, Repository repository, HashMap<String, Object> formaData){
        super(context);

        long facilityLocationId = repository.getDefaultSharePrefrences()
                .getLong(context.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);
        this.repository = repository;
        this.context = context;
        this.formaData = formaData;

        LinearLayoutCompat.LayoutParams textViewlayoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        LinearLayoutCompat.LayoutParams linearlayoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        label = new AppCompatTextView(context);
        value = new AppCompatTextView(context);
        label.setLayoutParams(textViewlayoutParams);
        value.setLayoutParams(textViewlayoutParams);
        value.setTextColor(Color.BLACK);
        this.setLayoutParams(linearlayoutParams);
        this.setOrientation(LinearLayoutCompat.HORIZONTAL);
        this.addView(label);
        this.addView(value);

        repository.getDatabase().locationDao().findById(facilityLocationId).observe((AppCompatActivity)context,this::setFacilityLocation);
    }

    public void setLabelText(String text) {
        this.label.setText(text);
    }

    private void setTextValue(String value) {

        this.value.setText("  "+value);
    }

    public void setLabelTextSize(int size){

        label.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }

    public void setValueTextSize(int size){
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }


    public void setFormData(HashMap<String,Object> formData){
        this.formaData = formData;
    }

    public void setDistrictLocation(Location location){
       setTextValue(location.name);
        formaData.put((String)getTag(),location.location_id);
    }

    public void setFacilityLocation(Location location){

        if(location != null)
            populateFacilityDistrictLocation(location.parent_location);

    }

    public void populateFacilityDistrictLocation(Long id){

        if(id == null)
            id = (long)1;

        repository.getDatabase().locationDao().findById(id).observe((AppCompatActivity)context, this::setDistrictLocation);
    }
}
