package zm.gov.moh.drugresistanttb.view;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view.DrugResistantTbPatientDashboardActivity;

public class MyDrugResistantTbDialogFragment extends DialogFragment {
    int mNum;

    static MyDrugResistantTbDialogFragment newInstance(int num) {
        MyDrugResistantTbDialogFragment f = new MyDrugResistantTbDialogFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mdr_dialog, container, false);
       // View tv = v.findViewById(R.id.visit_list);
        Button viaVisitLink = (Button)v.findViewById(R.id.str_regimen_register);
        Button leepVisitLink = (Button)v.findViewById(R.id.mortality_summary);
       viaVisitLink.setOnClickListener(this::onClick);
       leepVisitLink.setOnClickListener(this::onClick);

        return v;
    }

    private void onClick(View view) {

        try {


            if (view.getId() == R.id.str_regimen_register)
                ((DrugResistantTbPatientDashboardActivity) getActivity()).selectVisit(1);
            else if (view.getId() == R.id.mortality_summary) {
                ((DrugResistantTbPatientDashboardActivity) getActivity()).selectVisit(2);
            }
        }catch (Exception e){

        }

        this.dismiss();
    }
}