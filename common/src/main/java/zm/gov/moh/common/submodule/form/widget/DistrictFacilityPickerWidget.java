package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.util.TypedValue;


import zm.gov.moh.common.R;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.entity.domain.Location;

public class DistrictFacilityPickerWidget extends LinearLayoutCompat {


    AppCompatTextView facilityTextView;
    AppCompatTextView districtTextView;
    AppCompatTextView facilityValue;
    AppCompatTextView districtValue;
    private String facilityText = "Facility";
    private String districtText = "District";
    private int textSize = 18;//dp
    private Context context;
    Repository repository;
    long facilityLocationId;
    final String WHITE_SPACE = "  ";


    public DistrictFacilityPickerWidget(Context context, String districtText, String facilityText, Repository repository){
        super(context);

        this.context = context;
        this.districtText = districtText;
        this.facilityText = facilityText;
        this.repository = repository;
        facilityLocationId = repository.getDefaultSharePrefrences()
                .getLong(context.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);

        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LinearLayoutCompat.LayoutParams layoutParams1 = new LinearLayoutCompat.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(layoutParams);
        this.setOrientation(LinearLayoutCompat.VERTICAL);


        facilityTextView = new AppCompatTextView(context);
        districtTextView = new AppCompatTextView(context);

        facilityValue = new AppCompatTextView(context);
        districtValue = new AppCompatTextView(context);

        facilityTextView.setLayoutParams(layoutParams1);
        districtTextView.setLayoutParams(layoutParams1);

        facilityValue.setLayoutParams(layoutParams1);
        districtValue.setLayoutParams(layoutParams1);

        facilityValue.setTextColor(context.getResources().getColor(R.color.black));
        districtValue.setTextColor(context.getResources().getColor(R.color.black));

        facilityTextView.setText(facilityText+WHITE_SPACE);
        districtTextView.setText(districtText+WHITE_SPACE);

        facilityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        districtTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);

        districtValue.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        facilityValue.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);

        this.addView(WidgetUtils.createLinearLayout(context, LinearLayoutCompat.HORIZONTAL, districtTextView, districtValue));
        this.addView(WidgetUtils.createLinearLayout(context, LinearLayoutCompat.HORIZONTAL, facilityTextView, facilityValue));

        repository.getDatabase().locationDao().findById(facilityLocationId).observe((AppCompatActivity)context,this::setFacilityLocation);
    }

    public void setDistrictLocation(Location location){
        districtValue.setText(location.name);
    }

    public void setFacilityLocation(Location location){

        if(location != null){
            facilityValue.setText(location.name);
            populateFacilityDistrictLocation(location.parent_location);
        }

    }

    public void populateFacilityDistrictLocation(Long id){

        if(id == null)
            id = (long)1;

        repository.getDatabase().locationDao().findById(id).observe((AppCompatActivity)context, this::setDistrictLocation);
    }

}
