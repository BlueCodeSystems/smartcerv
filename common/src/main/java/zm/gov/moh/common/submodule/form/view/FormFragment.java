package zm.gov.moh.common.submodule.form.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.databinding.DataBindingUtil;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.FragmentFormBinding;
import zm.gov.moh.common.model.FormJson;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.FormContext;
import zm.gov.moh.common.submodule.form.model.FormDataBundleKey;
import zm.gov.moh.common.submodule.form.model.FormType;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.service.DemographicsPersist;
import zm.gov.moh.core.service.EncounterPersist;
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

        FragmentFormBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_form,container,false);

        rootView = binding.getRoot();
        this.form.setRootView(rootView.findViewById(R.id.form_container));
        this.form.setFormContext(new FormContext());

        BaseActivity.ToolBarEventHandler toolBarEventHandler = context.getToolbarHandler();
        binding.setToolbarhandler(toolBarEventHandler);

        try {

            this.formJson =(FormJson) bundle.getSerializable(Key.JSON_FORM);
            formModel = FormAdapter.getAdapter().fromJson(this.formJson.getJson());

            toolBarEventHandler.setTitle(formJson.getName());

        }catch (Exception e){
            Exception ex = e;
        }

        initFormData(bundle);

        if(renderWidgets.get()) {

            WidgetModelToWidgetAdapter WidgetModelToWidgetAdapter = new WidgetModelToWidgetAdapter(getContext(),context.getViewModel().getRepository(),bundle,form);

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
            //formSubmitButtonWidget.setBundle(this.bundle);

            formSubmitButtonWidget.setOnSubmit(bundle -> {

                //bundle.putSerializable(EncounterPersist.FORM_DATA_KEY, bundle);
                Intent intent = new Intent(context,EncounterPersist.class);

                this.bundle.putStringArrayList(Key.FORM_TAGS, form.getFormContext().getTags());


                if(formModel.getAttributes().getFormType().equals(FormType.ENCOUNTER)) {
                    intent = new Intent(context, EncounterPersist.class);
                }
                else if(formModel.getAttributes().getFormType().equals(FormType.DEMOGRAPHICS)){
                    intent = new Intent(context, DemographicsPersist.class);
                }
                else{

                    String moduleName = this.bundle.getString(Key.START_MODULE_ON_RESULT);
                    context.startModule(moduleName,this.bundle);
                    context.onBackPressed();
                    return;
                }

                intent.putExtras(this.bundle);
                context.startService(intent);
                context.onBackPressed();
               // Module submodule = (Module) bundle.getSerializable(BaseActivity.START_SUBMODULE_ON_FORM_RESULT_KEY);
                //context.startModule(submodule, bundle);
            });

            this.form.getRootView().addView(formSubmitButtonWidget);
        }
        renderWidgets.set(false);
        // Inflate the layout for this fragment


        return rootView;
    }

    public void initFormData(Bundle bundle){

        final long SESSION_LOCATION_ID = context.getViewModel().getRepository().getDefaultSharePrefrences()
                .getLong(context.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);
        final String USER_UUID = context.getViewModel().getRepository().getDefaultSharePrefrences()
                .getString(context.getResources().getString(zm.gov.moh.core.R.string.logged_in_user_uuid_key), "null");

        bundle.putLong(Key.LOCATION_ID, SESSION_LOCATION_ID);

        if(formModel.getAttributes().getFormType().equals(FormType.ENCOUNTER))
            this.bundle.putLong(Key.ENCOUNTER_TYPE_ID, formModel.getAttributes().getEncounterId());

        context.getViewModel()
                .getRepository()
                .getDatabase()
                .providerUserDao()
                .getAllByUserUuid(USER_UUID)
                .observe(context, providerUser -> {

                    bundle.putLong(FormDataBundleKey.PROVIDER_ID, providerUser.provider_id);
                    bundle.putLong(FormDataBundleKey.USER_ID, providerUser.user_id);
                });
        //bundle.put(Key.PERSON_ID,)
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