package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.util.TypedValue;

import java.util.HashMap;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.domain.Location;

public class DistrictLabelWidget extends LinearLayoutCompat{

    private AppCompatTextView label;
    private AppCompatTextView value;
    private Bundle bundle;
    private Repository repository;
    private Context context;

    public DistrictLabelWidget(Context context, Repository repository, Bundle bundle){
        super(context);

        long facilityLocationId = repository.getDefaultSharePrefrences()
                .getLong(Key.LOCATION_ID, 1);
        this.repository = repository;
        this.context = context;
        this.bundle = bundle;

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


    public void setFormData(Bundle bundle){
        this.bundle = bundle;
    }

    public void setDistrictLocation(Location location){
       setTextValue(location.getName());
        bundle.putLong((String)getTag(),location.getLocationId());
    }

    public void setFacilityLocation(Location location){

        if(location != null)
            populateFacilityDistrictLocation(location.getParentLocation());

    }

    public void populateFacilityDistrictLocation(Long id){

        if(id == null)
            id = (long)1;

        repository.getDatabase().locationDao().findById(id).observe((AppCompatActivity)context, this::setDistrictLocation);
    }
}
