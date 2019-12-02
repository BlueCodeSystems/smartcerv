package zm.gov.moh.drugresistanttb.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import zm.gov.moh.drugresistanttb.R;

public class DrugResistantTbHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug_resistant_tb_home, container, false);
        return view;
    }
}
