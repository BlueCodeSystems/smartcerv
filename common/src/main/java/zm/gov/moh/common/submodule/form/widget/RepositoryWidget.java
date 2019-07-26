package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;

import zm.gov.moh.core.repository.api.Repository;

public abstract class RepositoryWidget<T> extends SubmittableWidget<T> {

    protected Repository mRepository;

    public RepositoryWidget(Context context){
        super(context);
    }

    public void setRepository(Repository repository) {
        mRepository = repository;
    }

    public Repository getRepository() {
        return mRepository;
    }

    public abstract static class Builder extends SubmittableWidget.Builder {

        protected Repository mRepository;

        public Builder(Context context){
            super(context);
        }


        public RepositoryWidget.Builder setRepository(Repository repository){

            mRepository = repository;
            return this;
        }

        @Override
        public abstract BaseWidget build();
    }
}
