package zm.gov.moh.cervicalcancer.submodule.register.view;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.threetenabp.AndroidThreeTen;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.cervicalcancer.OpenmrsConfig;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.databinding.ActivityCervicalCancerRegisterBinding;
import zm.gov.moh.cervicalcancer.databinding.FragmentRegisterBinding;
import zm.gov.moh.cervicalcancer.submodule.register.adapter.ClientListAdapter;
import zm.gov.moh.cervicalcancer.submodule.register.viewmodel.RegisterViewModel;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.ui.BaseRegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RegisterActivity context = (RegisterActivity)getContext();

        FragmentRegisterBinding binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_register,container,false);
        View root = binding.getRoot();

        context.setTitle("Client Register");
        binding.setSearchTermObserver(context.getSearchTermObserver());
        binding.setContext(context);

        context.setInPatientCountCallback(binding::setInPatientCount);

        RecyclerView clientRecyclerView = root.findViewById(R.id.client_list);

        clientRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        clientRecyclerView.setAdapter(context.getAdapter());

        //Get all clients initially
        context.getAllClient();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }



}
