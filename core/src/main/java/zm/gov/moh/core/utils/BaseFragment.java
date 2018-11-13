package zm.gov.moh.core.utils;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.Consumer;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.io.Serializable;
import java.util.Map;

import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.utils.Utils;

public class BaseFragment extends Fragment implements Serializable {

    protected static final String JSON_FORM_KEY = "JSON_FORM_KEY";
    protected LiveData<Submodule> startSubmodule;



}
