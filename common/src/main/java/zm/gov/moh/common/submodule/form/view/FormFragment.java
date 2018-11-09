package zm.gov.moh.common.submodule.form.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.BaseFragment;
import zm.gov.moh.common.submodule.form.adapter.FormAdapter;
import zm.gov.moh.common.submodule.form.adapter.FormModelWidgetAdapter;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.WidgetModel;
import zm.gov.moh.common.submodule.form.model.FormData;
import zm.gov.moh.common.submodule.form.model.WidgetSectionModel;
import zm.gov.moh.common.submodule.form.widget.FormSectionWidget;
import zm.gov.moh.common.submodule.form.widget.FormSubmitButtonWidget;

public class FormFragment extends BaseFragment {

    private LinearLayout container;
    private View rootView;
    private FormData<String,Object> formData;
    private AtomicBoolean renderWidgets;
    private Form formModel;
    private String formJson;

    public FormFragment() {
        // Required empty public constructor
        formData = new FormData<>();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        renderWidgets = new AtomicBoolean();

        renderWidgets.set(true);

        rootView = inflater.inflate(R.layout.fragment_form, container, false);

        this.container = rootView.findViewById(R.id.form_container);

        try {

            this.formJson = getArguments().getString(BaseFragment.JSON_FORM_KEY);
            formModel = FormAdapter.getAdapter().fromJson(this.formJson);

        }catch (Exception e){
            Exception ex = e;
        }

        if(renderWidgets.get()) {

            for(WidgetSectionModel section : formModel.getWidgetGroup()){

                FormSectionWidget formSection = new FormSectionWidget(getContext());
                formSection.setHeading(section.getTitle());

                for(WidgetModel widgetModel : section.getChildren()){

                    View view = FormModelWidgetAdapter.getWidget(getContext(), widgetModel, this.formData);
                    formSection.addView(view);
                }

                this.container.addView(formSection);
            }

            FormSubmitButtonWidget formSubmitButtonWidget = new FormSubmitButtonWidget(getContext());
            formSubmitButtonWidget.setText(formModel.getAttributes().getSubmitLabel());
            formSubmitButtonWidget.setFormData(this.formData);
            formSubmitButtonWidget.setOnSubmit(formData -> {
                FormData f = formData;
                f.getMap();
            });

            this.container.addView(formSubmitButtonWidget);
        }
        renderWidgets.set(false);
        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
