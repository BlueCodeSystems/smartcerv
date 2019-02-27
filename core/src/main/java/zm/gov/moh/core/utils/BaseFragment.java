package zm.gov.moh.core.utils;

import androidx.lifecycle.LiveData;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

import zm.gov.moh.core.model.submodule.Module;

public class BaseFragment extends Fragment implements Serializable {

    protected static final String JSON_FORM_KEY = "JSON_FORM_KEY";
    protected LiveData<Module> startSubmodule;



}
