package zm.gov.moh.common.submodule.form.widget;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import zm.gov.moh.common.R;
import zm.gov.moh.common.R2;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTextWiget extends AbstractWidget {

    View rootView;
    String hint;

    @BindView(R2.id.edit_text)
    EditText editText;

    public EditTextWiget() {
        // Required empty public constructor
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit_text_wiget, container, false);



        ButterKnife.bind(this, rootView);
        editText.setHint(hint);
        return rootView;
    }

    public void addHint(String hint){

        this.hint = hint;
    }

    @OnTextChanged(R2.id.edit_text)
    public void onTextChange(){

        if(tag != null && formData != null){
            formData.put(tag, editText.getText().toString());
            formData.get("ff");
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
