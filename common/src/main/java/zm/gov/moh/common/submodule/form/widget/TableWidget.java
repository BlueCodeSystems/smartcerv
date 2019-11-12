package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.model.TableData;

public class TableWidget extends RepositoryWidget {

    Builder builder;
    protected int mColSize;
    protected int mRowSize;
    protected TableData[] mTableData;

    private void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public TableWidget(Context context){
        super(context);
    }

    @Override
    public void onCreateView() {


        mColSize = builder.getColSize();
        mRowSize = builder.getRowSize();
        mTableData = builder.getTableData();

        Set<Integer> rowAdded = new HashSet<>();

        TableRow[] tableRow = new TableRow[mRowSize];

        for(int i =0 ; i< mRowSize; i++){

            TableRow row = new TableRow(mContext);
            row.setBackground(mContext.getResources().getDrawable(R.drawable.border_bottom));
            tableRow[i] = row;
        }

        TableLayout tableLayout = new TableLayout(mContext);
        WidgetUtils.setLayoutParams(tableLayout, WidgetUtils.MATCH_PARENT, WidgetUtils.WRAP_CONTENT);

        for(TableData tableData :mTableData)
        {
            TableRow dataRow = tableRow[tableData.getPosY()];

            dataRow.addView(tableData(tableData.getValue()));

            if(!rowAdded.contains(tableData.getPosY())) {
                tableLayout.addView(dataRow);
                rowAdded.add(tableData.getPosY());

            }



        }


        this.addView(tableLayout);
    }

    @Override
    public void setValue(Object value) {

    }

    public AppCompatTextView tableData(String text){
        AppCompatTextView textView = new AppCompatTextView(mContext);
       // textView.pas
        textView.setBackground(mContext.getResources().getDrawable(R.drawable.border_right));
        textView.setText(text);
        return textView;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    public static class Builder extends RepositoryWidget.Builder{

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

        @Override
        public BaseWidget build() {

            TableWidget widget = new TableWidget(mContext);
            widget.setBuilder(this);

            widget.onCreateView();
            return widget;
        }
    }
}
