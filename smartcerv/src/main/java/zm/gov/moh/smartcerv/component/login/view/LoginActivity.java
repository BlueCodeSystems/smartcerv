package zm.gov.moh.smartcerv.component.login.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zm.gov.moh.core.service.RestServiceImpl;
import zm.gov.moh.core.utils.Provider;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.smartcerv.R;
import zm.gov.moh.smartcerv.component.login.viewmodel.LoginViewModel;
import zm.gov.moh.smartcerv.databinding.LoginActivityBinding;
import zm.gov.moh.smartcerv.BR;



public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.setRestAPIService(new RestServiceImpl(Provider.getRestAPI()));
        loginViewModel.setUtil(new Utils());
        loginViewModel.setActivity(this);

        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        binding.setCredentials(loginViewModel.getCredentials());
        binding.setVariable(BR.viewmodel, loginViewModel);
    }
}
