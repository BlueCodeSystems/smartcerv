package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.core.util.Consumer;
import butterknife.OnClick;
import zm.gov.moh.common.R;
import zm.gov.moh.core.utils.Utils;

import android.os.Build;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.DoubleSummaryStatistics;

public class FormImageViewButtonWidget extends androidx.appcompat.widget.AppCompatButton {

    private Bundle bundle;
    public DoubleSummaryStatistics onClick;

    public FormImageViewButtonWidget(Context context, DoubleSummaryStatistics onClick){
        super(context);
        this.onClick = onClick;

        this.setAllCaps(false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,Utils.dpToPx(context,20));
        this.setBackgroundColor(context.getResources().getColor(R.color.light_green));
        this.setTextColor(context.getResources().getColor(R.color.white));
        this.setLayoutParams(layoutParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setOnClick(Consumer<Bundle> OnClick) {

        this.setOnClickListener(view -> OnClick.accept(this.bundle));
    }

    public void setBundle(Bundle bundle) {

        this.bundle = bundle;
    }
}