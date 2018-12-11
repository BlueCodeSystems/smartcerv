package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import androidx.core.util.Consumer;
import android.widget.RelativeLayout;

import java.util.HashMap;

public class FormSubmitButtonWidget extends androidx.appcompat.widget.AppCompatButton {

    private HashMap<String, Object> formData;

    public FormSubmitButtonWidget(Context context){
        super(context);

        this.setAllCaps(false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(layoutParams);
    }

    public void setOnSubmit(Consumer<HashMap<String, Object>> onSubmit) {

        this.setOnClickListener(view -> onSubmit.accept(this.formData));
    }

    public void setFormData(HashMap<String, Object> formData) {

        this.formData = formData;
    }
}