package zm.gov.moh.common.submodule.form.widget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SectionWidget extends AbstractWidget {

    AtomicBoolean enableToAddWidget;

    List<AbstractWidget> widgets;
    View rootView;

    public SectionWidget() {
        // Required empty public constructor
        widgets = new ArrayList<>();
        enableToAddWidget = new AtomicBoolean();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        enableToAddWidget.set(true);
        // Inflate the layout for this fragment
       rootView = inflater.inflate(R.layout.fragment_section_widget, container, false);

       return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();


        if(enableToAddWidget.get()) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            for (AbstractWidget widget : widgets)
                ft.add(R.id.wiget_container_section, widget, widget.getTag());

            ft.commit();
        }
        enableToAddWidget.set(false);
    }

    public void addWidget(AbstractWidget widget){

        widget.setFormData(super.formData);
        widgets.add(widget);
    }

    public void addWidget(List<AbstractWidget> widget){

        for(AbstractWidget widget1: widget)
            widget.add(widget1);
    }
}
