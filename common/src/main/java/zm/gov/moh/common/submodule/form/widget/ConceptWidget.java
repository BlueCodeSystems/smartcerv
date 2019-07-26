package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ConceptWidget <T> extends RepositoryWidget<T>
{
    protected String mUuid;
    protected long mConceptId;
    public ConceptWidget(Context context) {
        super(context);
    }

    public void setUuid(String uuid){
        mUuid = uuid;
    }

    @Override
    public void onCreateView() {

        getRepository().getDatabase().conceptDao()
                .getConceptIdByUuidAsync(mUuid)
                .observe((AppCompatActivity)mContext, this::onConceptIdRetrieved);
    }

    public void onConceptIdRetrieved(Long conceptId){
        mConceptId = conceptId;
    }

    @Override
    public void setValue(T value) {

    }

    @Override
    public T getValue() {
        return null;
    }

    public static class Builder<T> extends RepositoryWidget.Builder {

        protected String mUuid;


        public Builder(Context context){
            super(context);
        }

        public Builder setUuid(String uuid){
            mUuid = uuid;
            return this;
        }

        @Override
        public  BaseWidget build(){

            ConceptWidget<T> widget = new ConceptWidget<>(mContext);
            if(mUuid != null)
                widget.setUuid(mUuid);
            if(mRepository != null)
                widget.setRepository(mRepository);
            return widget;
        }
    }
}
