package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;

public interface Widget {

    void addViewToViewGroup();
    void setWeight(int weight);
    int getWeight();

}
