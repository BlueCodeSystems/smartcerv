package zm.gov.moh.cervicalcancer.view;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.databinding.FragmentCervicalCancerHomeBinding;
import zm.gov.moh.common.base.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CervicalCancerHomeFragment extends Fragment {


    public CervicalCancerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentCervicalCancerHomeBinding binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_cervical_cancer_home,container,false);
        binding.setContext((BaseActivity) getContext());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}
