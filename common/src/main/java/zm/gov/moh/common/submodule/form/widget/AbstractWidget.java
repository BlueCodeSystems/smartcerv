package zm.gov.moh.common.submodule.form.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zm.gov.moh.common.submodule.form.model.FormData;

public class AbstractWidget extends Fragment implements BaseWidget {

    protected String tag;
    protected FormData<String,String> formData;

    protected String TAG_KEY;
    protected String TAG_VALUE_MAP_KEY;

    public AbstractWidget(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        savedInstanceState.putString(TAG_KEY, this.tag);
        savedInstanceState.putSerializable(TAG_VALUE_MAP_KEY, this.formData);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setTag(String tag) {

        this.tag = tag;
    }

    public void setFormData(FormData<String,String> formData){

        this.formData = formData;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null){
            this.tag = savedInstanceState.getString(TAG_KEY);
            this.formData = (FormData<String, String>) savedInstanceState.getSerializable(TAG_VALUE_MAP_KEY);
        }
    }
}