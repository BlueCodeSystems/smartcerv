package zm.gov.moh.common.submodule.form.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.R;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.utils.BaseActivity;
import zm.gov.moh.core.utils.BaseFragment;
import zm.gov.moh.common.submodule.form.adapter.FormAdapter;
import zm.gov.moh.common.submodule.form.adapter.FormModelWidgetAdapter;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.widget.WidgetModel;
import zm.gov.moh.common.submodule.form.model.widget.WidgetSectionModel;
import zm.gov.moh.common.submodule.form.widget.FormSectionWidget;
import zm.gov.moh.common.submodule.form.widget.FormSubmitButtonWidget;

public class FormFragment extends BaseFragment {

    private LinearLayout container;
    private View rootView;
    private HashMap<String,Object> formData;
    private AtomicBoolean renderWidgets;
    private Form formModel;
    private String formJson;
    private BaseActivity context;
    private Bundle bundle;

    public FormFragment() {
        // Required empty public constructor
        formData = new HashMap<>();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        renderWidgets = new AtomicBoolean();

        renderWidgets.set(true);

        bundle = getArguments();

        rootView = inflater.inflate(R.layout.fragment_form, container, false);

        this.container = rootView.findViewById(R.id.form_container);

        context = (BaseActivity)getContext();

        try {

            this.formJson = bundle.getString(BaseFragment.JSON_FORM_KEY);
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
                HashMap f = formData;

                bundle.putSerializable(BaseActivity.FORM_DATA_KEY, formData);

                Submodule submodule = (Submodule) bundle.getSerializable(BaseActivity.START_SUBMODULE_ON_FORM_RESULT_KEY);
                context.startSubmodule(submodule, bundle);

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
