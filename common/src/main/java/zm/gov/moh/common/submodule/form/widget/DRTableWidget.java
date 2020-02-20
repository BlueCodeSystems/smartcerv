package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.zip.Inflater;

import androidx.appcompat.widget.AppCompatTextView;
import zm.gov.moh.common.R;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.submodule.form.model.TableData;

public class DRTableWidget extends BaseWidget {

    Builder builder;
    protected Bundle mBundle;
    protected int mColSize;
    protected int mRowSize;
    protected TableData[] mTableData;

    protected TableLayout tableLayout;
    protected TableRow tableRow;
    protected TableRow.LayoutParams rowLayoutParams;
    protected TableLayout.LayoutParams layoutParams;

    private void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public DRTableWidget(Context context) {
        super(context);
    }

    @Override
    public void onCreateView() {

        LayoutInflater inflater =((BaseActivity) mContext).getLayoutInflater();
        this.addView(inflater.inflate(R.layout.dr_table, null));


        this.addView(mContext);
    }

    private void addView(Context mContext) {
    }

    //@Override
    /*public View onCreate(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dr_table, parent, false);
        return rootView;*/
        //return inflater.inflate(R.layout.dr_table, container, false);



        /*layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        tableLayout = new TableLayout(mContext);
        tableLayout.setLayoutParams(layoutParams);

        rowLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        addView(tableLayout);

        tableRow = new TableRow(mContext);
        tableRow.setBackground(mContext.getResources().getDrawable(R.drawable.border_bottom));

        tableLayout.addView(tableRow);
    }*/



        //@Override
   /* public void setValue(Object value) {

    }*/

    public AppCompatTextView tableData(String text){
        AppCompatTextView textView = new AppCompatTextView(mContext);
        // textView.pas
        textView.setBackground(mContext.getResources().getDrawable(R.drawable.border_right));
        textView.setText(text);
        return textView;
    }

    public static class Builder extends BaseWidget.Builder{

        protected Bundle mBundle;
        protected int mRowSize;
        protected int mColSize;
        protected TableData[] tableData;
        public Builder(Context context){
            super(context);
        }

        public Builder setRowSize(int mRowSize) {
            this.mRowSize = mRowSize;
            return this;
        }

        public Builder setColSize(int mColSize) {
            this.mColSize = mColSize;
            return this;
        }

        public Builder setTableData(TableData[] tableData) {
            this.tableData = tableData;
            return this;
        }

        public TableData[] getTableData() {
            return tableData;
        }

        public int getColSize() {
            return mColSize;
        }

        public int getRowSize() {
            return mRowSize;
        }

         public DRTableWidget.Builder setBundle(Bundle bundle) {
             mBundle = bundle;
             return this;
         }

        @Override
        public BaseWidget build() {

            DRTableWidget widget = new DRTableWidget(mContext);
            widget.setBuilder(this);

            widget.onCreateView();
            return widget;
        }

        }
    }

