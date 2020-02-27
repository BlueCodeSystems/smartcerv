package zm.gov.moh.common.submodule.form.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.AppCompatTextView;
import zm.gov.moh.common.OpenmrsConfig;
import zm.gov.moh.common.R;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.submodule.form.model.TableData;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;

import static zm.gov.moh.common.base.Utils.dateCellView;
import static zm.gov.moh.common.base.Utils.renderCheckMarkIconView;

public class DrugSusceptibilityTestingResultsTableWidget extends BaseWidget {

    Builder builder;
    protected Bundle mBundle;
    protected int mColSize;
    protected int mRowSize;
    protected TableData[] mTableData;

    protected TableLayout tableLayout;
    protected TableRow tableRow;
    protected TableRow.LayoutParams rowLayoutParams;
    protected TableLayout.LayoutParams layoutParams;
    private BaseActivity context;

    private void setBuilder(Builder builder) {
        this.builder = builder;
    }

    Bundle bundle;

    public DrugSusceptibilityTestingResultsTableWidget(Context context) {
        super(context);
    }

    @Override
    public void onCreateView() {

        LayoutInflater inflater = ((BaseActivity) mContext).getLayoutInflater();
        this.addView(inflater.inflate(R.layout.dr_tb_drug_susceptibility_testing_results, null));
        //bundle = getArguments();
        //long patientId = bundle.getLong(Key.PERSON_ID);

        tableLayout = findViewById(R.id.dr_visit_type_table);

        //context.getViewModel()
                //.getRepository()
                //.getDatabase()
                //.visitDao()
                //.getByPatientIdVisitTypeId(patientId, 2L, 3L, 4L, 5L, 6L, 7L)
                //.observe(context, this::recordCompletedVisits);



        this.addView(mContext);
    }

    /*final public Bundle getArguments() {
        return mArguments;
    }*/

    public void tabulateVisitRow(String date, Map<Long, Boolean> visitCompleted) {

        TableRow tableRow = new TableRow(context);
        tableRow.setBackground(context.getResources().getDrawable(R.drawable.border_bottom));

        tableRow.addView(dateCellView(context, date));
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(OpenmrsConfig.VISIT_TYPE_ID_MDR_BACTERIAL_EXAM)));
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(OpenmrsConfig.VISIT_TYPE_ID_MDR_BASELINE_FOLLOW_UP_ASSESSMENT)));
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(OpenmrsConfig.VISIT_TYPE_ID_MDR_MONTHLY_REVIEW_FORM)));

        tableLayout.addView(tableRow);
    }

    public void recordCompletedVisits(List<VisitEntity> visitList) {

        HashMap<Long, Boolean> visitCompleted;
        visitCompleted = new HashMap<>();

        Iterator<VisitEntity> visitIterator = visitList.iterator();

        while (visitIterator.hasNext()) {

            visitCompleted.put(OpenmrsConfig.VISIT_TYPE_ID_MDR_BACTERIAL_EXAM, false);
            visitCompleted.put(OpenmrsConfig.VISIT_TYPE_ID_MDR_BASELINE_FOLLOW_UP_ASSESSMENT, false);
            visitCompleted.put(OpenmrsConfig.VISIT_TYPE_ID_MDR_MONTHLY_REVIEW_FORM, false);



            VisitEntity visit = visitIterator.next();
            String date = visit.getDateStarted().format(DateTimeFormatter.ISO_LOCAL_DATE);
            long visitTypeId = visit.getVisitTypeId();

            visitCompleted.put(visitTypeId, true);

            tabulateVisitRow(date, visitCompleted);
        }
    }
    private void addView(Context mContext) {
    }

    //@Override
    /*public View onCreate(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dr_tb_prev_treat_table, parent, false);
        return rootView;*/
        //return inflater.inflate(R.layout.dr_tb_prev_treat_table, container, false);



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

         public DrugSusceptibilityTestingResultsTableWidget.Builder setBundle(Bundle bundle) {
             mBundle = bundle;
             return this;
         }

        @Override
        public BaseWidget build() {

            DrugSusceptibilityTestingResultsTableWidget widget = new DrugSusceptibilityTestingResultsTableWidget(mContext);
            widget.setBuilder(this);

            widget.onCreateView();
            return widget;
        }

        }
    }

