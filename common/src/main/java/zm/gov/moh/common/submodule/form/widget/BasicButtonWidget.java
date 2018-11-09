package zm.gov.moh.common.submodule.form.widget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zm.gov.moh.common.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicButtonWidget extends AbstractWidget {


    public BasicButtonWidget() {
        super();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_button_widget, container, false);
    }

}
