package zm.gov.moh.common.submodule.form.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import zm.gov.moh.common.R;
import zm.gov.moh.common.R2;

public class BasicSubmitButtonWidget extends AbstractSubmitButtonWidget {

    @BindView(R2.id.button_widget)
    Button button;

    public BasicSubmitButtonWidget(){
        super();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);


        View view = inflater.inflate(R.layout.fragment_button_widget, container, false);
        ButterKnife.bind(this, view);
        button.setOnClickListener(view1 -> {

            callback.accept(super.formData);
        });

        return view;
    }
}
