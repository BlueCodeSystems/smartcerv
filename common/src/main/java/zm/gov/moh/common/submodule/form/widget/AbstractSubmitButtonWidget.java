package zm.gov.moh.common.submodule.form.widget;

import android.support.v4.util.Consumer;

import zm.gov.moh.common.submodule.form.model.FormData;

public abstract class AbstractSubmitButtonWidget extends AbstractButtonWidget implements SubmitButtonWidget {

    protected Consumer callback;
    public AbstractSubmitButtonWidget(){
        super();
    }

    public void onSubmit(Consumer<FormData<String,String>> callback){

       this.callback = callback;
    }
}
