package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;

public class BasicDrugWidget extends BaseWidget {

    protected String mValue;
    protected Bundle mBundle;
    protected String mUuid;
    protected AppCompatTextView mTextViewWidget;

    public BasicDrugWidget(Context context) {
        super(context);
        mContext = context;
    }

    public String getUuid() {
        return mUuid;
    }

    public BasicDrugWidget setUuid(String uuid) {
        this.mUuid = uuid;
        return this;
    }


    public void onCreateView(){

    }

    public static class Builder extends BaseWidget.Builder {

        public Builder(Context context) {
            super(context);
        }

        public BasicDrugWidget build() {

            return  null;
        }
    }
}
