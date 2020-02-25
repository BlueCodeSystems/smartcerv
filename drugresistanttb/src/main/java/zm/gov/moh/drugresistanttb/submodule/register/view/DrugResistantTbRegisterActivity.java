package zm.gov.moh.drugresistanttb.submodule.register.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.drugresistanttb.BR;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.databinding.ActivityDrugResistantTbBinding;
import zm.gov.moh.drugresistanttb.databinding.ActivityDrugResistantTbRegisterBinding;
import zm.gov.moh.drugresistanttb.submodule.drugresistanttb.viewmodel.DrugResistantTbViewModel;
import zm.gov.moh.drugresistanttb.submodule.register.adapter.DrugResistantTbClientListAdapter;
import zm.gov.moh.drugresistanttb.submodule.register.viewmodel.DrugResistantTbRegisterViewModel;
import zm.gov.moh.common.ui.BaseRegisterActivity;
import zm.gov.moh.common.base.BaseActivity;


public class DrugResistantTbRegisterActivity extends BaseRegisterActivity {

    DrugResistantTbRegisterViewModel drugResistantTbregisterViewModel;
    DrugResistantTbClientListAdapter drugResistantTbClientListAdapter;
    Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDrugResistantTbRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_drug_resistant_tb_register);
        AndroidThreeTen.init(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        drugResistantTbregisterViewModel = ViewModelProviders.of(this).get(DrugResistantTbRegisterViewModel.class);

        setViewModel(drugResistantTbregisterViewModel);
        //..binding.setTitle("MDR Client Register");
        binding.setSearchTermObserver(searchTermObserver);
        //..binding.setContext(this);

        RecyclerView clientRecyclerView = findViewById(R.id.mdr_client_list);

        clientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        drugResistantTbClientListAdapter = new DrugResistantTbClientListAdapter(this);

        clientRecyclerView.setAdapter(drugResistantTbClientListAdapter);
        getAllClient();


        DrugResistantTbViewModel drugResistantTbViewModel = ViewModelProviders.of(this).get(DrugResistantTbViewModel.class);
        //ActivityDrugResistantTbBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_drug_resistant_tb_register);
        binding.setVariable(zm.gov.moh.drugresistanttb.BR.mdrtbviewmodel, drugResistantTbViewModel);

        drugResistantTbViewModel.getStartSubmodule().observe(this, this::startModule);

        initPopupMenu(R.menu.base_menu_edit,toolBarEventHandler::onMenuItemSelected);
        initToolBar(binding.getRoot());
        //ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        binding.setTitle("MDR Client Register");
        binding.setContext(this);
    }

    @Override
    public void matchedSearchId(List<Long> ids) {
        drugResistantTbregisterViewModel.getMatchedClients(ids).observe(this, drugResistantTbClientListAdapter::setClientList);
    }

    @Override
    public void getAllClient() {
        drugResistantTbregisterViewModel.getAllClients().observe(this, drugResistantTbClientListAdapter::setClientList);
        setViewModel(drugResistantTbregisterViewModel);
        addDrawer(this);
    }
    public void mdrregisterClient(){

        try {
            mBundle  = new Bundle();
            JsonForm clientRegistration = new JsonForm("Client Registration",
                    Utils.getStringFromInputStream(this.getAssets().open("forms/client_registration.json")));



            mBundle.putSerializable(Key.JSON_FORM, clientRegistration);
            startModule(BaseApplication.CoreModule.FORM, mBundle);

        }catch (Exception e){

        }


    }
}