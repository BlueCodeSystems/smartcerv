package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

public class BasicDrugWidget extends BasicConceptWidget {

    protected String uuid;

    public BasicDrugWidget(Context context) {
        super(context);
        mContext = context;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BasicDrugWidget build() {

        return null;
    }
}
