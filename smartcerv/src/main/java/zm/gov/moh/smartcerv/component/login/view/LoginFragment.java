package zm.gov.moh.smartcerv.component.login.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import zm.gov.moh.smartcerv.R;
import zm.gov.moh.smartcerv.component.login.viewmodel.LoginViewModel;
import zm.gov.moh.smartcerv.component.Submodules.SubModulesActivity;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);


        final Context activity = getActivity();

        Button smartCerv =  view.findViewById(R.id.login_button);
        smartCerv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent module = new Intent(activity, SubModulesActivity.class);
               activity.startActivity(module);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

}
