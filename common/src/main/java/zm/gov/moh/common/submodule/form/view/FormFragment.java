package zm.gov.moh.common.submodule.form.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.databinding.DataBindingUtil;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.FragmentFormBinding;
import zm.gov.moh.common.model.FormJson;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.FormContext;
import zm.gov.moh.common.submodule.form.model.FormDataBundleKey;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.service.EncounterSubmission;
import zm.gov.moh.core.utils.BaseFragment;
import zm.gov.moh.common.submodule.form.adapter.FormAdapter;
import zm.gov.moh.common.submodule.form.adapter.WidgetModelToWidgetAdapter;
import zm.gov.moh.common.submodule.form.model.FormModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetSectionModel;
import zm.gov.moh.common.submodule.form.widget.FormSectionWidget;
import zm.gov.moh.common.submodule.form.widget.FormSubmitButtonWidget;

public class FormFragment extends BaseFragment {

    private Form form;
    private View rootView;
    private HashMap<String,Object> formData;
    private AtomicBoolean renderWidgets;
    private FormModel formModel;
    private FormJson formJson;
    private FormActivity context;
    private Bundle bundle;

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = (FormActivity) getContext();
        renderWidgets = new AtomicBoolean();
        this.form = new Form();

        renderWidgets.set(true);

        bundle = getArguments();

        formData = (HashMap<String, Object>)bundle.getSerializable(Key.FORM_STATE);
        FragmentFormBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_form,container,false);

        rootView = binding.getRoot();
        this.form.setRootView(rootView.findViewById(R.id.form_container));
        this.form.setFormContext(new FormContext());

        BaseActivity.ToolBarEventHandler toolBarEventHandler = context.getToolbarHandler();
        binding.setToolbarhandler(toolBarEventHandler);

        try {

            this.formJson =(FormJson) bundle.getSerializable(BaseFragment.JSON_FORM_KEY);
            formModel = FormAdapter.getAdapter().fromJson(this.formJson.getJson());

            toolBarEventHandler.setTitle(formJson.getName());

        }catch (Exception e){
            Exception ex = e;
        }

        initFormData(formData);

        if(renderWidgets.get()) {

            WidgetModelToWidgetAdapter WidgetModelToWidgetAdapter = new WidgetModelToWidgetAdapter(getContext(),context.getViewModel().getRepository(),formData,form);

            for(WidgetSectionModel section : formModel.getWidgetGroup()){

                FormSectionWidget formSection = new FormSectionWidget(context);
                formSection.setHeading(section.getTitle());

                for(WidgetModel widgetModel : section.getChildren()){

                    View view = WidgetModelToWidgetAdapter.getWidget(widgetModel);
                    formSection.addView(view);
                }

                this.form.getRootView().addView(formSection);
            }

            FormSubmitButtonWidget formSubmitButtonWidget = new FormSubmitButtonWidget(getContext());
            formSubmitButtonWidget.setText(formModel.getAttributes().getSubmitLabel());
            formSubmitButtonWidget.setFormData(this.formData);

            formSubmitButtonWidget.setOnSubmit(formData -> {

                bundle.putSerializable(EncounterSubmission.FORM_DATA_KEY, formData);
                Intent formSubmission = new Intent(context,EncounterSubmission.class);
                formSubmission.putExtras(bundle);
                context.startService(formSubmission);

                context.onBackPressed();
               // Submodule submodule = (Submodule) bundle.getSerializable(BaseActivity.START_SUBMODULE_ON_FORM_RESULT_KEY);
                //context.startSubmodule(submodule, bundle);
            });

            this.form.getRootView().addView(formSubmitButtonWidget);
        }
        renderWidgets.set(false);
        // Inflate the layout for this fragment


        return rootView;
    }

    public void initFormData(HashMap<String,Object> formData){

        final long SESSION_LOCATION_ID = context.getViewModel().getRepository().getDefaultSharePrefrences()
                .getLong(context.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);
        final String USER_UUID = context.getViewModel().getRepository().getDefaultSharePrefrences()
                .getString(context.getResources().getString(zm.gov.moh.core.R.string.logged_in_user_uuid_key), "null");
        final Long PERSON_ID = bundle.getLong(Key.PERSON_ID);

        final long ENCOUNTER_ID = formModel.getAttributes().getEncounterId();

        formData.put(Key.LOCATION_ID, SESSION_LOCATION_ID);
        formData.put(Key.PERSON_ID, PERSON_ID);

        if(formModel.getAttributes().getFormType().equals("Encounter"))
            formData.put(Key.ENCOUNTER_TYPE_ID, ENCOUNTER_ID);

        context.getViewModel()
                .getRepository()
                .getDatabase()
                .providerUserDao()
                .getAllByUserUuid(USER_UUID)
                .observe(context, providerUser -> {

                    formData.put(FormDataBundleKey.PROVIDER_ID, providerUser.provider_id);
                    formData.put(FormDataBundleKey.USER_ID, providerUser.user_id);
                });
        //formData.put(Key.PERSON_ID,)
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