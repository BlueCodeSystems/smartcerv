package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;

public class BasicDrugWidget extends BaseWidget {

    protected String mValue;
    protected Bundle mBundle;
    protected String mUuid;
    protected TextViewWidget mTextView;

    private BasicDrugWidget(Context context) {
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

    public void addViewToViewGroup(){

        mTextView = new TextViewWidget(mContext);
        mTextView.setLabel("Ceftriaxone 125-250mg IM");// retrieve name using uuid here
        mTextView.setTextSize(18);
        mTextView.addViewToViewGroup();

        addView(mTextView);

    }

    public static class Builder extends BaseWidget.Builder {

        protected String mUuid;

        public Builder(Context context) {
            super(context);
        }


        public String getUuid() {
            return mUuid;
        }

        public Builder setUuid(String uuid) {
            this.mUuid = uuid;
            return this;
        }

        public BasicDrugWidget build() {
            BasicDrugWidget widget = new BasicDrugWidget(mContext);

            if(mUuid != null)
                widget.setUuid(mUuid);

            widget.addViewToViewGroup();

            return  widget;
        }
    }
}
