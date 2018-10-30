package zm.gov.moh.common.submodule.register.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.threeten.bp.LocalDate;

import java.util.List;

import zm.gov.moh.common.R;
import zm.gov.moh.core.repository.database.entity.derived.Client;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder> {

    private LayoutInflater mInflater;
    private List<Client> clientList;

    class ClientViewHolder extends RecyclerView.ViewHolder{

        TextView patientId;
        TextView firstName;
        TextView lastName;
        TextView age;


        private ClientViewHolder(View item){
            super(item);

            patientId = item.findViewById(R.id.client_id);
            firstName = item.findViewById(R.id.first_name);
            lastName = item.findViewById(R.id.last_name);
            age = item.findViewById(R.id.age);
        }
    }

    public ClientListAdapter(Context context){

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.client_list_view_item, parent,false);

        return new ClientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {

        int currentYear = LocalDate.now().getYear();
        if(clientList != null){

            Client current = clientList.get(position);
            int clientBirthYear =current.birthdate.getYear();
            holder.patientId.setText(String.valueOf(current.person_id));
            holder.firstName.setText(current.given_name+" "+current.family_name);
            holder.age.setText(String.valueOf(currentYear - clientBirthYear));
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