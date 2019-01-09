package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

public abstract class AbstractTextViewWidget<T extends TextView> extends AbstractWidget {

    protected T view;

    AbstractTextViewWidget(Context context){
        super(context);

        this.addView(view);

        view.setAllCaps(false);

        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                formData.put((String) getTag(),charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public AbstractTextViewWidget injectView(T view){
        this.view = view;
        return this;
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        view.setLayoutParams(params);
    }

    public void setText(CharSequence hint){
        view.setText(hint);
    }

    public void setHint(CharSequence hint){
        view.setHint(hint);
    }

}
