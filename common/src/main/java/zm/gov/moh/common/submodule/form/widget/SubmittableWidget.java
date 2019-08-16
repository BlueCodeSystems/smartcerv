package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;

public abstract class SubmittableWidget<T> extends BaseWidget implements Submittable<T>{

    protected Bundle mBundle;

    public SubmittableWidget(Context context){
        super(context);
    }

    public abstract boolean isValid();

    @Override
    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public abstract static class Builder extends BaseWidget.Builder {

        protected Bundle mBundle;

        public Builder(Context context){
            super(context);
        }


        public Builder setBundle(Bundle bundle){

            mBundle = bundle;
            return this;
        }

        @Override
        public abstract BaseWidget build();
    }
}
