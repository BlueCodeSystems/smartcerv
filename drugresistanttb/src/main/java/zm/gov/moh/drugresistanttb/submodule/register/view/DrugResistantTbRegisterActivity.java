package zm.gov.moh.drugresistanttb.submodule.register.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.databinding.ActivityDrugResistantTbRegisterBinding;
import zm.gov.moh.drugresistanttb.submodule.register.adapter.DrugResistantTbClientListAdapter;
import zm.gov.moh.drugresistanttb.submodule.register.viewmodel.DrugResistantTbRegisterViewModel;
import zm.gov.moh.common.ui.BaseRegisterActivity;


public class DrugResistantTbRegisterActivity extends BaseRegisterActivity {

    DrugResistantTbRegisterViewModel drugResistantTbregisterViewModel;
    DrugResistantTbClientListAdapter drugResistantTbClientListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDrugResistantTbRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_drug_resistant_tb_register);
        AndroidThreeTen.init(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        drugResistantTbregisterViewModel = ViewModelProviders.of(this).get(DrugResistantTbRegisterViewModel.class);

        setViewModel(drugResistantTbregisterViewModel);
        binding.setTitle("Client Register");
        binding.setSearchTermObserver(searchTermObserver);

        RecyclerView clientRecyclerView = findViewById(R.id.client_list);

        clientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        drugResistantTbClientListAdapter = new DrugResistantTbClientListAdapter(this);

        clientRecyclerView.setAdapter(drugResistantTbClientListAdapter);
        getAllClient();
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
}