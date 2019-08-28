package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;

public abstract class SubmittableWidget<T> extends BaseWidget implements Submittable<T>{

    protected Bundle mBundle;
    protected String mErrorMessage;
    protected String mRegex;
    protected Boolean mRequired;

    public SubmittableWidget(Context context){
        super(context);
    }

    @Override
    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public void setErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }

    public void setRegex(String mRegex) {
        this.mRegex = mRegex;
    }

    public void setRequired(Boolean mRequired) {
        this.mRequired = mRequired;
    }

    public abstract static class Builder extends BaseWidget.Builder {

        protected Bundle mBundle;
        protected String mRegex;
        protected String mErrorMessage;
        protected Boolean mRequired;

        public Builder(Context context){
            super(context);
        }


        public Builder setBundle(Bundle bundle){

            mBundle = bundle;
            return this;
        }

        public Builder setRegex(String regex){

            mRegex = regex;
            return this;
        }

        public Builder setErrorMessage(String errorMessage){

            mErrorMessage = errorMessage;
            return this;
        }

        public Builder setRequired(Boolean required) {
            this.mRequired = required;
            return this;
        }

        @Override
        public abstract BaseWidget build();
    }
}
