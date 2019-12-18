package zm.gov.moh.drugresistanttb.submodule.register.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zm.gov.moh.drugresistanttb.DrugResistantTbModule;
import zm.gov.moh.drugresistanttb.databinding.ClientCardBinding;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.drugresistanttb.submodule.register.view.DrugResistantTbRegisterActivity;

public class DrugResistantTbClientListAdapter extends RecyclerView.Adapter<DrugResistantTbClientListAdapter.ClientViewHolder> {

    private LayoutInflater mInflater;
    private List<Client> clientList;
    private BaseActivity context;
    private Module mdrpatientDashboard;
    private Bundle bundle;

    class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ClientCardBinding binding;

        private ClientViewHolder(ClientCardBinding binding){
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Client client){
            binding.setClient(client);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {

            Client client = clientList.get(getAdapterPosition());
            long clientId = client.getPatientId();
            bundle.putLong(ClientDashboardActivity.PERSON_ID, clientId);

            //Module call = (Module) context.getIntent().getSerializableExtra(DrugResistantTbRegisterActivity.START_SUBMODULE_WITH_RESULT_KEY);
           context.startModule(mdrpatientDashboard, bundle);
        }

    }

    public DrugResistantTbClientListAdapter(Context context){

        mInflater = LayoutInflater.from(context);
        this.context = (BaseActivity) context;
        BaseApplication applicationContext = (BaseApplication)((BaseActivity) context).getApplication();
        //mdrpatientDashboard = ((ModuleGroup)applicationContext.getModule(DrugResistantTbModule.MODULE))
                            //.getSubmodule(DrugResistantTbModule.Submodules.MDR_PATIENT_DASHBOARD);

        bundle = new Bundle();
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        ClientCardBinding clientCardBinding =
                ClientCardBinding.inflate(layoutInflater, parent, false);

        return new ClientViewHolder(clientCardBinding);
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {

        if(clientList != null){

            Client client = clientList.get(position);
            holder.bind(client);
        }
    }

    public void setClientList(List<Client> clients){

        clientList = clients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(clientList != null)
            return clientList.size();
        return 0;
    }
}