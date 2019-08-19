package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;

import androidx.appcompat.widget.LinearLayoutCompat;

public  abstract class BaseWidget extends LinearLayoutCompat implements Widget{

    protected Context mContext;
    protected int mWeight;

    public BaseWidget(Context context){
        super(context);
        mContext = context;
    }

    public void setWeight(int weight) {
        mWeight = weight;
    }

    @Override
    public int getWeight() {
        return mWeight;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        super.onLayout(b, i,i1,i2,i3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public abstract void onCreateView();


    public abstract static class Builder {

        Object mTag;
        int mWeight = 0;
        Context mContext;
        String mFutureDate;

        Builder(Context context){

            mContext = context;
        }

        public Builder setTag(Object tag){

            mTag = tag;
            return this;
        }

        public Builder setWeight(int weight){

            mWeight = weight;
            return this;
        }
        public Builder setFutureDate(String FutureDate){
            mFutureDate = FutureDate;
            return this;
        }

        public abstract BaseWidget build();
    }

}