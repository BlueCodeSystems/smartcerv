package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter.MdrFormListAdapter;
import zm.gov.moh.core.utils.BaseApplication;

public class DrugResistantTbFormListFragment extends Fragment {

    private BaseActivity context;

    public DrugResistantTbFormListFragment() {

    }

    public DrugResistantTbFormListFragment newInstance() {
        DrugResistantTbFormListFragment fragment = new DrugResistantTbFormListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        this.context = (BaseActivity) this.getContext();
        context.initBundle(bundle);

        //String f = bundle.getString(Key.PERSON_GIVEN_NAME);
        // f.length();

        View view = inflater.inflate(R.layout.fragment_drug_resistant_tb_form_list, container, false);

        /*MdrFormListAdapter mdrFormListAdapter = new MdrFormListAdapter(context, ((BaseApplication) getContext().getApplicationContext()).getCareServices(),bundle);

        ExpandableListView MdrServiceList = view.findViewById(R.id.mdr_form_list);

        MdrServiceList.setAdapter(mdrFormListAdapter);*/

        // Inflate the layout for this fragment
        return view;
    }

}
