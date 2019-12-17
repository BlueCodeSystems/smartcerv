package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.LinkedList;
import java.util.List;

import androidx.core.util.Consumer;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import zm.gov.moh.drugresistanttb.databinding.FragmentDrugResistantTbPatientDashboardVisitBinding;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.viewmodel.DrugResistantTbPatientDashboardViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrugResistantTbPatientDashboardVisitFragment extends Fragment {


    DrugResistantTbPatientDashboardActivity mContext;
    DrugResistantTbPatientDashboardViewModel viewModel;
    FragmentDrugResistantTbPatientDashboardVisitBinding binding;
    public DrugResistantTbPatientDashboardVisitFragment() {
        // Required empty public constructor
    }
}
