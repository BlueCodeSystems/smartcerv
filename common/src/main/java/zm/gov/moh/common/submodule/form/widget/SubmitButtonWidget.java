package zm.gov.moh.common.submodule.form.widget;

import android.support.v4.util.Consumer;

import zm.gov.moh.common.submodule.form.model.FormData;

public interface SubmitButtonWidget extends ButtonWidget {

    void onSubmit(Consumer<FormData<String,String>> callback);
}