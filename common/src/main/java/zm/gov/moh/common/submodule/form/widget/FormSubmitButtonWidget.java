package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.support.v4.util.Consumer;
import android.widget.RelativeLayout;
import zm.gov.moh.common.submodule.form.model.FormData;

public class FormSubmitButtonWidget extends android.support.v7.widget.AppCompatButton {

    private FormData<String, Object> formData;

    public FormSubmitButtonWidget(Context context){
        super(context);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setOnSubmit(Consumer<FormData<String, Object>> onSubmit) {

        this.setOnClickListener(view -> onSubmit.accept(this.formData));
    }

    public void setFormData(FormData<String, Object> formData) {

        this.formData = formData;
    }
}