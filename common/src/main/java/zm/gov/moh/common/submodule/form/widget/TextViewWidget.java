package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import zm.gov.moh.common.ui.Utils;

public class TextViewWidget extends BaseWidget implements Labeled {

    protected String mLabel;
    protected int mTextSize;
    protected AppCompatTextView mTextView;

    @Override
    public void setLabel(String label) {
        mLabel = label;
    }

    @Override
    public String getLabel() {
        return mLabel;
    }

    TextViewWidget(Context context){
        super(context);

        WidgetUtils.setLayoutParams(this, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, mWeight);
        setOrientation(WidgetUtils.HORIZONTAL);
    }

    TextViewWidget getRoot(){
        return this;
    }

    @Override
    public void setTextSize(int size) {
        mTextSize = size;
    }

    @Override
    public int getTextSize() {
        return mTextSize;
    }

    @Override
    public void addViewToViewGroup() {

        mTextView = new AppCompatTextView(mContext);
        mTextView.setText(mLabel);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        WidgetUtils.setLayoutParams(mTextView, WidgetUtils.WRAP_CONTENT, WidgetUtils.WRAP_CONTENT, mWeight)
        .setGravity(Gravity.CENTER_VERTICAL);
        addView(mTextView);

    }

    public static class Builder extends BaseWidget.Builder{

        protected String mLabel;
        protected int mTextSize;

        public Builder(Context context){
            super(context);
        }

        public Builder setLabel(String label){
            mLabel = label;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.mTextSize = textSize;
            return this;
        }

        @Override
        public BaseWidget build() {

           TextViewWidget widget = new TextViewWidget(mContext);

           if(mLabel != null)
               widget.setLabel(mLabel);
            if(mTag != null)
                widget.setTag(mTag);

           widget.setWeight(mWeight);
           widget.setTextSize(mTextSize);
           widget.addViewToViewGroup();

            return widget;
        }
    }
}
