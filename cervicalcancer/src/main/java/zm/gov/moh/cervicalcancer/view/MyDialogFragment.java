package zm.gov.moh.cervicalcancer.view;

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
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardActivity;

public class MyDialogFragment extends DialogFragment {
    int mNum;

    static MyDialogFragment newInstance(int num) {
        MyDialogFragment f = new MyDialogFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
       // View tv = v.findViewById(R.id.visit_list);
        Button viaVisitLink = (Button)v.findViewById(R.id.via_visit);
        Button leepVisitLink = (Button)v.findViewById(R.id.leep_visit);
       viaVisitLink.setOnClickListener(this::onClick);
       leepVisitLink.setOnClickListener(this::onClick);

        return v;
    }

    private void onClick(View view) {

        try {


            if (view.getId() == R.id.via_visit)
                ((PatientDashboardActivity) getActivity()).selectVisit(1);
            else if (view.getId() == R.id.leep_visit) {
                ((PatientDashboardActivity) getActivity()).selectVisit(2);
            }
        }catch (Exception e){

        }

        this.dismiss();
    }
}