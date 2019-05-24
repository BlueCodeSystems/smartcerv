package zm.gov.moh.common.submodule.form.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.FragmentFormBinding;
import zm.gov.moh.common.model.FormJson;
import zm.gov.moh.common.submodule.form.adapter.FormAdapter;
import zm.gov.moh.common.submodule.form.adapter.WidgetModelToWidgetAdapter;
import zm.gov.moh.common.submodule.form.model.Action;
import zm.gov.moh.common.submodule.form.model.Form;
import zm.gov.moh.common.submodule.form.model.FormContext;
import zm.gov.moh.common.submodule.form.model.FormDataBundleKey;
import zm.gov.moh.common.submodule.form.model.FormModel;
import zm.gov.moh.common.submodule.form.model.FormType;
import zm.gov.moh.common.submodule.form.model.Logic;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetModel;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetSectionModel;
import zm.gov.moh.common.submodule.form.widget.FormImageViewButtonWidget;
import zm.gov.moh.common.submodule.form.widget.FormSectionWidget;
import zm.gov.moh.common.submodule.form.widget.FormSubmitButtonWidget;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.service.DemographicsPersist;
import zm.gov.moh.core.service.EncounterPersist;
import zm.gov.moh.core.utils.BaseFragment;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        context = (FormActivity) getContext();
        renderWidgets = new AtomicBoolean();
        this.form = new Form();

        renderWidgets.set(true);

        bundle = getArguments();

        context.activityResultEmitter.observe(context,this::onUriRetrieved);

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

        //Criteria,Action
        if( formModel.getAttributes().getLogic() != null)
            for(Logic logic : formModel.getAttributes().getLogic()){

                if(logic.getAction().getType().equals(Action.ACTION_TYPE_CRITERIA)){

                    for(String tag : logic.getAction().getMetadata().getTags())
                        if(bundle.containsKey(tag)) {

                            String value = bundle.getString(tag);
                            if(!value.equals(logic.getCondition().getValue())){
                                context.onBackPressed();
                                //Toast.makeText(context, context.getString(R.string.male_patient_block),Toast.LENGTH_LONG).show();
                            }
                        }
                }

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
            //FormImageViewButtonWidget formImageViewButtonWidget = new FormImageViewButtonWidget(getContext());
            //formImageViewButtonWidget.setText(formModel.getAttributes().getSubmitLabel());
            FormSubmitButtonWidget formSubmitButtonWidget = new FormSubmitButtonWidget(getContext());
            formSubmitButtonWidget.setText(formModel.getAttributes().getSubmitLabel());
            //formSubmitButtonWidget.setBundle(this.bundle);

            //formImageViewButtonWidget.setOnClick(bundle1 ->

            formSubmitButtonWidget.setOnSubmit(bundle -> {
                bundle = this.bundle;
                Bundle contextbundle = context.getIntent().getExtras();
                //bundle.putSerializable(EncounterSubmission.FORM_DATA_KEY, bundle);
                this.bundle.putAll(contextbundle);
                Intent intent = new Intent(context,EncounterPersist.class);

               ArrayList<String> tags = form.getFormContext().getTags();

                this.bundle.putStringArrayList(Key.FORM_TAGS, form.getFormContext().getTags());

                ObsValue<String> obsValue1 = ( ObsValue<String>)bundle.getSerializable("image view button");

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

            //this.form.getRootView().addView(formImageViewButtonWidget);
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
    public void onUriRetrieved(Map.Entry<Integer, Uri> data) {
       String tag = bundle.getString(Key.VIEW_TAG);
       View view = rootView.findViewWithTag(tag);
        ((FormImageViewButtonWidget)view).onUriRetrieved(data.getValue());

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