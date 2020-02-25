package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import zm.gov.moh.common.OpenmrsConfig;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.derived.FacilityDistrictCode;

public class DrugResistantTBEditTextWidget extends RepositoryWidget<CharSequence> {

    long facilityLocationId;
    FacilityDistrictCode code;
    final String OFFSET = "000001";
    private AppCompatEditText mEditText;
    private AppCompatTextView mTextView;
    private String mLabel;
    private String mIdentifier;

    public DrugResistantTBEditTextWidget(Context context) {
        super(context);
    }

    @Override
    public void onCreateView() {

        mEditText = new AppCompatEditText(mContext);
        mEditText.addTextChangedListener(WidgetUtils.createTextWatcher(this::setValue));
        mTextView = new AppCompatTextView(mContext);
        mTextView.setText((mLabel != null)?mLabel:"");

        this.addView(mTextView);
        this.addView(mEditText);

        Long patientId = mBundle.getLong(Key.PERSON_ID);
        String identifierType = mBundle.getString(Key.PATIENT_ID_TYPE);
        mRepository.getDatabase().patientIdentifierDao().findPatientIDByIdentifierType(patientId, identifierType).observe((AppCompatActivity)mContext, this::setIdentifier);
    }

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public void setIdentifier(String identifier){

        if(identifier != null){
            mEditText.setText(identifier);
        }
        else {
            facilityLocationId = mRepository.getDefaultSharePrefrences()
                    .getLong(Key.LOCATION_ID, 1);
            mRepository.getDatabase().facilityDistrictCodeDao().getFacilitydistrictCodeByLocationId(facilityLocationId).observe((AppCompatActivity) mContext, this::setFacilityDistrictCode);
        }
    }

    public void setFacilityDistrictCode(FacilityDistrictCode code){

        this.code = code;
        mRepository.getDatabase().patientIdentifierDao().getByLocationType(facilityLocationId, OpenmrsConfig.MDRPIZ_IDENTIFIER_TYPE).observe((AppCompatActivity)mContext, this::appendSerial);
    }

    @Override
    public CharSequence getValue() {
        return null;
    }

    @Override
    public void setValue(CharSequence value) {
        mIdentifier =value.toString();
    }

    @Override
    public boolean isValid() {
        return false;
    }

    public void appendSerial(List<String> identifiers) {

        // UTH/DR/45/19
        String districtFacilityCode = this.code.getFacilityCode() + "/DR/";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String shortYear = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);

        if(identifiers.size() > 0) {

            try {
                List<Long> serials = new ArrayList<>();
                for (String identifier : identifiers) {
                    int index = identifier.indexOf('/', 6);

                    if(!identifier.contains(districtFacilityCode))
                        continue;

                    try {
                        long serial = Long.valueOf(identifier.subSequence(index + 1, identifier.length()).toString());
                        serials.add(serial);
                    }catch (Exception e){
                        e.getMessage();
                    }
                }

                long preferredSerial = serials.isEmpty() ? 1 : Collections.max(serials) + 1;
                int identifierLength = String.valueOf(preferredSerial).length();

                if(OFFSET.length() >= identifierLength)
                    mEditText.setText(districtFacilityCode + OFFSET.substring(0,
                            OFFSET.length() - identifierLength) + preferredSerial + "/" + shortYear);
                else
                    mEditText.setText(districtFacilityCode + preferredSerial + "/" + shortYear);

            }catch (Exception e){
                mEditText.setText(districtFacilityCode+"-"+OFFSET);
            }
        } else {
            mEditText.setText(districtFacilityCode+"-"+OFFSET);
        }
    }

    public static class Builder extends RepositoryWidget.Builder {
        private String mLabel;

        public Builder setLabel(String mLabel) {
            this.mLabel = mLabel;
            return this;
        }

        public Builder(Context context) {
            super(context);
        }

        @Override
        public BaseWidget build() {
            DrugResistantTBEditTextWidget widget = new DrugResistantTBEditTextWidget(mContext);

            if(mBundle != null)
                widget.setBundle(mBundle);
            if(mTag != null)
                widget.setTag(mTag);
            if( mRepository != null)
                widget.setRepository(mRepository);
            if(mLabel != null)
                widget.setLabel(mLabel);
            if(mErrorMessage != null)
                widget.setErrorMessage(mErrorMessage);
            if(mRequired != null)
                widget.setRequired(mRequired);
            if(mRegex != null)
                widget.setRegex(mRegex);

            widget.onCreateView();

            return widget;
        }
    }
}
