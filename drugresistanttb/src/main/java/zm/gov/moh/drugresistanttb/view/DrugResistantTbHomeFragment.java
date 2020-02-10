package zm.gov.moh.drugresistanttb.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.databinding.FragmentDrugResistantTbHomeBinding;

public class DrugResistantTbHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDrugResistantTbHomeBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_drug_resistant_tb_home, container, false);
        binding.setContext((BaseActivity) getContext());
        return binding.getRoot();
    }
}
