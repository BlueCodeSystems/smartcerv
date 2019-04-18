package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.util.Consumer;
import zm.gov.moh.common.R;
import zm.gov.moh.core.utils.Utils;

public class FormImageViewButtonWidget extends androidx.appcompat.widget.AppCompatButton {

    private Bundle bundle;
    private Consumer<Bundle> onClick;

    public FormImageViewButtonWidget(Context context, Bundle bundle){
        super(context);

        this.setAllCaps(false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,Utils.dpToPx(context,20));
        this.setBackgroundColor(context.getResources().getColor(R.color.light_green));
        this.setTextColor(context.getResources().getColor(R.color.white));
        this.setLayoutParams(layoutParams);
    }

    public void setOnClick(Consumer<Bundle> onClick) {
        this.onClick = onClick;

        this.setOnClickListener(view -> onClick.accept(this.bundle));
    }

    public void setBundle(Bundle bundle) {

        this.bundle = bundle;
    }
}
