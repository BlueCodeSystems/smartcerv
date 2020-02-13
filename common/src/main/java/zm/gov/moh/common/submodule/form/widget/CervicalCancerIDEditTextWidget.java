package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import zm.gov.moh.common.OpenmrsConfig;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.derived.FacilityDistrictCode;
import zm.gov.moh.core.utils.BaseApplication;


public class CervicalCancerIDEditTextWidget extends RepositoryWidget<CharSequence> {

    long facilityLocationId;
    FacilityDistrictCode code;
    final String OFFSET = "000001";
    private AppCompatEditText mEditText;
    private AppCompatTextView mTextView;
    private String mLabel;
    private String mIdentifier;

    public CervicalCancerIDEditTextWidget(Context context){
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
      mRepository.getDatabase().patientIdentifierDao().getByLocationType(facilityLocationId, OpenmrsConfig.CCPIZ_IDENTIFIER_TYPE).observe((AppCompatActivity)mContext, this::appendSerial);
    }

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(CharSequence value) {

        mIdentifier = value.toString();
        mBundle.putString((String)getTag(), value.toString());
    }

    @Override
    public boolean isValid() {


        if(mRequired != null && mRequired) {
            mIdentifier = mBundle.getString((String) getTag());

            if (mIdentifier != null && mIdentifier.matches(mRegex))
                return true;
            else {
                mEditText.setError(mErrorMessage);
                return false;
            }
        }else
            return false;
    }

    public void  appendSerial(List<String> identifiers){

        String districtFacilityCode = this.code.getDistrictCode()+"-"+this.code.getFacilityCode();
        if(identifiers.size() > 0) {

            try {
                List<Long> serials = new ArrayList<>();
                for (String identifier : identifiers) {
                    int index = identifier.lastIndexOf('-');

                    if(!identifier.contains(districtFacilityCode))
                        continue;

                        try {
                            long serial = Long.valueOf(identifier.subSequence(index + 1, identifier.length()).toString());
                            serials.add(serial);
                        }catch (Exception e){

                        }
                }

                long preferredSerial = Collections.max(serials) + 1;
                int identifierLength = String.valueOf(preferredSerial).length();

                if(OFFSET.length() >= identifierLength)
                    mEditText.setText(districtFacilityCode + "-"+OFFSET.substring(0,OFFSET.length()- identifierLength) + preferredSerial);
                else
                    mEditText.setText(districtFacilityCode + "-"+preferredSerial);

            }catch (Exception e){
                mEditText.setText(districtFacilityCode+"-"+OFFSET);
            }
        }
        else {
            mEditText.setText(districtFacilityCode+"-"+OFFSET);
        }
    }

    public static class Builder extends RepositoryWidget.Builder{

        private String mLabel;
        public Builder(Context context){
            super(context);
        }

        public Builder setLabel(String mLabel) {
            this.mLabel = mLabel;
            return this;
        }

        @Override
        public BaseWidget build() {

            CervicalCancerIDEditTextWidget widget = new CervicalCancerIDEditTextWidget(mContext);

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

            return  widget;
        }
    }
}
