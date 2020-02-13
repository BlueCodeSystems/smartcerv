package zm.gov.moh.common.submodule.login.view;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.FragmentLoginCredentialsBinding;
import zm.gov.moh.common.submodule.login.model.ViewBindings;
import zm.gov.moh.common.submodule.login.viewmodel.LoginViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CredentialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CredentialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LoginViewModel viewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CredentialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CredentialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CredentialFragment newInstance(String param1, String param2) {
        CredentialFragment fragment = new CredentialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLoginCredentialsBinding binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_login_credentials,container, false);

        Context context = getContext();
        LoginActivity loginActivity =((LoginActivity)context);
        viewModel = (LoginViewModel) loginActivity.getViewModel();
        binding.setViewmodel(viewModel);
        binding.setCredentials(new ViewBindings());
        return binding.getRoot();
    }

}
