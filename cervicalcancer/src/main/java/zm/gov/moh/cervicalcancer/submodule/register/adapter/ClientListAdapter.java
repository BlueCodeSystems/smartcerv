package zm.gov.moh.cervicalcancer.submodule.register.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zm.gov.moh.cervicalcancer.CervicalCancerModule;
import zm.gov.moh.cervicalcancer.databinding.ClientCardBinding;
import zm.gov.moh.common.databinding.PartitionViewBinding;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.common.databinding.ClientDemographicsBinding;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder> {

    private LayoutInflater mInflater;
    private List<Client> clientList;
    private BaseActivity context;
    private Module patientDashboard;
    private Bundle bundle;

    class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private  ClientCardBinding binding = null;
        private  ClientDemographicsBinding demoBinding = null;
        private  PartitionViewBinding partitionViewBinding = null;


        private ClientViewHolder(ClientCardBinding binding){
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        private ClientViewHolder(ClientDemographicsBinding binding){
            super(binding.getRoot());

            this.demoBinding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        private ClientViewHolder(PartitionViewBinding binding){
            super(binding.getRoot());

            this.partitionViewBinding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Client client){

            if(demoBinding != null){

                demoBinding.setClient(client);
                demoBinding.executePendingBindings();
            }else if(binding != null) {

                binding.setClient(client);
                binding.executePendingBindings();
            }else if(partitionViewBinding != null) {

                partitionViewBinding.setClient(client);
                partitionViewBinding.executePendingBindings();
            }
        }

        @Override
        public void onClick(View view) {

            Client client = clientList.get(getAdapterPosition());
            long clientId = client.getPatientId();
            bundle.putLong(ClientDashboardActivity.PERSON_ID, clientId);

            //Module call = (Module) context.getIntent().getSerializableExtra(RegisterActivity.START_SUBMODULE_WITH_RESULT_KEY);
           context.startModule(patientDashboard, bundle);
        }
    }

    class OutClientViewHolder extends ClientViewHolder{

        private final ClientDemographicsBinding binding;

        private OutClientViewHolder(ClientDemographicsBinding binding){
            super(binding);

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

            //Module call = (Module) context.getIntent().getSerializableExtra(RegisterActivity.START_SUBMODULE_WITH_RESULT_KEY);
            context.startModule(patientDashboard, bundle);
        }

    }

    class PartitionViewHolder extends ClientViewHolder{

        private final PartitionViewBinding binding;

        private PartitionViewHolder(PartitionViewBinding binding){
            super(binding);

            this.binding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Client client){
            binding.setClient(client);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {

        }
    }

    public ClientListAdapter(Context context){

        mInflater = LayoutInflater.from(context);
        this.context = (BaseActivity) context;
        BaseApplication applicationContext = (BaseApplication)((BaseActivity) context).getApplication();
        patientDashboard = ((ModuleGroup)applicationContext.getModule(CervicalCancerModule.MODULE))
                            .getSubmodule(CervicalCancerModule.Submodules.PATIENT_DASHBOARD);

        bundle = new Bundle();
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        ClientViewHolder clientViewHolder = null;

        if(Client.Type.INPATIENT.ordinal() == viewType) {
            ClientCardBinding clientCardBinding =
                    ClientCardBinding.inflate(layoutInflater, parent, false);

            clientViewHolder = new ClientViewHolder(clientCardBinding);
        }
        else if(Client.Type.OUTPATIENT.ordinal() == viewType){

            ClientDemographicsBinding clientDemographicsBinding =
                    ClientDemographicsBinding.inflate(layoutInflater, parent, false);

            clientViewHolder = new OutClientViewHolder(clientDemographicsBinding);
        }else{
            PartitionViewBinding partitionViewBinding = PartitionViewBinding.inflate(layoutInflater, parent, false);

            clientViewHolder = new PartitionViewHolder(partitionViewBinding);
        }

        return clientViewHolder;
    }

    @Override
    public int getItemViewType(int position) {

        return clientList.get(position).getType().ordinal();
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