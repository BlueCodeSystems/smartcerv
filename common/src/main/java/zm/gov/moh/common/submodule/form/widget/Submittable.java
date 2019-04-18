package zm.gov.moh.common.submodule.form.widget;

import android.os.Bundle;

public interface Submittable<T> {

    void setValue(T value);
    T getValue();
    void setBundle(Bundle bundle);
}