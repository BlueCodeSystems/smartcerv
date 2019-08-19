package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import zm.gov.moh.common.OpenmrsConfig;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.Location;

public class DistrictPickerWidget extends RepositoryWidget<Map.Entry<Long,Long>> {

    protected String mDistrictLabel;
    protected String mProvinceLabel;
    protected Map<String,Long> mDistrictData;
    protected Long mDistrictId;
    protected Long mProvinceId;
    protected AppCompatTextView mProvinceName;
    protected AppCompatTextView mProvinceTextView = new AppCompatTextView(mContext);
    protected AppCompatTextView mDistrictTextView = new AppCompatTextView(mContext);
    int textSize = 18;
    final String WHITE_SPACE = " ";


    public DistrictPickerWidget(Context context){
        super(context);

        mDistrictData = new LinkedHashMap<>();
    }

    public DistrictPickerWidget setDistrictLabel(String label){

        mDistrictLabel = label;
        return this;
    }

    public DistrictPickerWidget setProvinceLabel(String label){

        mProvinceLabel = label;
        return this;
    }

    @Override
    public void setValue(Map.Entry<Long, Long> value) {

        mBundle.putLong(Key.PERSON_DISTRICT_LOCATION_ID,value.getKey());
        mBundle.putLong(Key.PERSON_PROVINCE_LOCATION_ID,value.getValue());
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Map.Entry<Long, Long> getValue() {
        return null;
    }

    @Override
    public void onCreateView() {

        this.setGravity(Gravity.CENTER_VERTICAL);

        mProvinceName = new AppCompatTextView(mContext);
        mProvinceTextView = new AppCompatTextView(mContext);
        mDistrictTextView = new AppCompatTextView(mContext);

        mDistrictTextView.setText(mDistrictLabel);
        mProvinceTextView.setText(mProvinceLabel + WHITE_SPACE);

        mProvinceName.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        mProvinceName.setTextColor(Color.BLACK);
        mDistrictTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        mProvinceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        getRepository().getDatabase().locationDao()
                .getByTagUuid(OpenmrsConfig.LOCATION_TAG_UUID_DISTRICT)
                .observe((AppCompatActivity)mContext, this::onDistrictListRetrieved);
    }

    public void onProvinceRetrieved(Location province){

        mProvinceId = province.getLocationId();
        mProvinceName.setText(province.getName());
        setValue(new AbstractMap.SimpleEntry<>(mDistrictId,mProvinceId));
    }

    public void onDistrictListRetrieved(List<Location> districts){

        for(Location district : districts)
            mDistrictData.put(district.getName(), district.getLocationId());

        AppCompatSpinner districtSelector = WidgetUtils.createSpinner(mContext, mDistrictData,this::onDistrictSelected,WidgetUtils.WRAP_CONTENT,WidgetUtils.WRAP_CONTENT,mWeight);
        AppCompatTextView districtTextView = new AppCompatTextView(mContext);
        districtTextView.setText(mDistrictLabel);

        this.removeAllViews();
        this.addView(WidgetUtils.createLinearLayout(mContext,WidgetUtils.HORIZONTAL,mDistrictTextView, districtSelector));
        this.addView(WidgetUtils.createLinearLayout(mContext,WidgetUtils.HORIZONTAL,mProvinceTextView, mProvinceName));
    }

    public void onDistrictSelected(Long locationId){

        mDistrictId = locationId;

        getRepository().getDatabase().locationDao().getParentByChildId(locationId).observe((AppCompatActivity)mContext, this::onProvinceRetrieved);
    }



    public static class Builder extends RepositoryWidget.Builder{

        protected String mDistrictLabel;
        protected String mProvinceLabel;

        public Builder(Context context){
            super(context);
        }

        public DistrictPickerWidget.Builder setDistrictLabel(String label){

            mDistrictLabel = label;
            return this;
        }

        public DistrictPickerWidget.Builder setProvinceLabel(String label){

            mProvinceLabel = label;
            return this;
        }

        @Override
        public BaseWidget build() {

            DistrictPickerWidget widget = new DistrictPickerWidget(mContext);

            if(mBundle != null)
                widget.setBundle(mBundle);
            if(mRepository != null)
                widget.setRepository(mRepository);
            if(mDistrictLabel != null)
                widget.setDistrictLabel(mDistrictLabel);
            if(mProvinceLabel != null)
                widget.setProvinceLabel(mProvinceLabel);

            widget.onCreateView();

            return  widget;
        }
    }

}
