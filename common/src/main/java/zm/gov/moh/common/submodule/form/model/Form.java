package zm.gov.moh.common.submodule.form.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Form{

    FormContext context;
    ViewGroup rootView;


    public void setFormContext(FormContext context) {
        this.context = context;
    }

    public FormContext getFormContext() {
        return context;
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }

    public ViewGroup getRootView() {
        return rootView;
    }
}