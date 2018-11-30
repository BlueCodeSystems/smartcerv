package zm.gov.moh.common.submodule.form.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import zm.gov.moh.common.R;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseFragment;
import zm.gov.moh.common.submodule.form.adapter.FormAdapter;
import zm.gov.moh.common.submodule.form.adapter.WidgetModelToWidgetAdapter;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetSectionModel;
import zm.gov.moh.common.submodule.form.widget.FormSectionWidget;
import zm.gov.moh.common.submodule.form.widget.FormSubmitButtonWidget;

public class FormFragment extends BaseFragment {

    private LinearLayout container;
    private View rootView;
    private HashMap<String,Object> formData;
    private AtomicBoolean renderWidgets;
    private Form formModel;
    private String formJson;
    private FormActivity context;
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

        context = (FormActivity) getContext();

        try {

            this.formJson = bundle.getString(BaseFragment.JSON_FORM_KEY);
            formModel = FormAdapter.getAdapter().fromJson(this.formJson);

        }catch (Exception e){
            Exception ex = e;
        }

        if(renderWidgets.get()) {

            WidgetModelToWidgetAdapter WidgetModelToWidgetAdapter = new WidgetModelToWidgetAdapter(getContext(),context.getViewModel().getRepository(),formData);

            for(WidgetSectionModel section : formModel.getWidgetGroup()){

                FormSectionWidget formSection = new FormSectionWidget(getContext());
                formSection.setHeading(section.getTitle());

                for(WidgetModel widgetModel : section.getChildren()){

                    View view = WidgetModelToWidgetAdapter.getWidget(widgetModel);
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

        context.getViewModel().getRepository().getDatabase().locationDao().getAll().observe(this, this::setLocations);
        return rootView;
    }


    public void populateChildLocations(Long parentLocationId){

        context.getViewModel().getRepository().getDatabase().locationDao().getChild(parentLocationId).observe(this, this::setChildLocations);
    }

    public void setLocations(List<Location> locations){

    }

    public void setChildLocations(List<Location> locations){

    }

    public ArrayAdapter getLocationAdapter(){
        return null;
    }

    public ArrayAdapter getChildLocationAdapter(){
        return null;
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
