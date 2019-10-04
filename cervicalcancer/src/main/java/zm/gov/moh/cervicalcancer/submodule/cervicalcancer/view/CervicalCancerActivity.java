package zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.BR;
import zm.gov.moh.cervicalcancer.databinding.CervicalCancerActivityBinding;
import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.viewmodel.CervicalCancerViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.common.ui.ToolBarEventHandler;
import zm.gov.moh.core.model.LineChartVisitItem;

public class CervicalCancerActivity extends BaseActivity {

    CervicalCancerViewModel cervicalCancerViewModel;
    TextView totalPatientsRegistered, totalSeen, totalScreened;
    BarChart barChart;
    BarData barData;
    ArrayList<BarEntry> barEntries = new ArrayList<>();
    ArrayList<BarEntry> barEntries1 = new ArrayList<>();
    ArrayList<BarEntry> barEntries2 = new ArrayList<>();
    ArrayList<BarEntry> barEntries3 = new ArrayList<>();
    List<Integer> ints;

    BarDataSet viaNegativeDataSet,viaPositiveDataSet,suspectCancerDataSet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cervicalCancerViewModel = ViewModelProviders.of(this).get(CervicalCancerViewModel.class);

        setViewModel(cervicalCancerViewModel);

        CervicalCancerActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.cervical_cancer_activity);

        binding.setVariable(BR.ccancerviewmodel, cervicalCancerViewModel);

        cervicalCancerViewModel.getStartSubmodule().observe(this, this::startModule);


        ToolBarEventHandler toolBarEventHandler = getToolbarHandler(this);
        toolBarEventHandler.setTitle("Cervical Cancer");
        binding.setToolbarhandler(toolBarEventHandler);
        binding.setContext(this);
        addDrawer(this);
        barChart= findViewById(R.id.barChart);



        totalPatientsRegistered = findViewById(R.id.totalRegistered);
        totalSeen = findViewById(R.id.totalPatientsSeen);
        totalScreened = findViewById(R.id.totalScreened);


        updateDataForViews();



    }

    //update data for the views

    public void updateDataForViews() {
        //observe the number of registered clients
        cervicalCancerViewModel.getAllRegisteredClients().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                totalPatientsRegistered.setText(aLong.toString());
            }
        });

        //observe the number of patients seen

        cervicalCancerViewModel.getAllseenClients().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                totalSeen.setText(aLong.toString());
            }
        });

        cervicalCancerViewModel.getAllScreenedClients().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                totalScreened.setText(aLong.toString());

            }
        });

        cervicalCancerViewModel.getAllScreenedInLastSevenDays().observe(this, new Observer<List<LineChartVisitItem>>() {
            @Override
            public void onChanged(List<LineChartVisitItem> lineChartVisitItems) {
                viaNegativeDataSet=new BarDataSet(viaNegativeEntries(lineChartVisitItems),"VIA negative");
                viaPositiveDataSet=new BarDataSet(viaPositiveEntries(lineChartVisitItems),"VIA positive");
                suspectCancerDataSet=new BarDataSet(suspectCancerEntries(lineChartVisitItems),"suspect cancer");

                viaPositiveDataSet.setColor(Color.BLUE);
                viaPositiveDataSet.setValueTextColor(Color.BLUE);
                viaNegativeDataSet.setColor(Color.GREEN);
                viaNegativeDataSet.setValueTextColor(Color.GREEN);
                suspectCancerDataSet.setColor(Color.RED);
                suspectCancerDataSet.setValueTextColor(Color.RED);
                BarData data = new BarData(viaPositiveDataSet,viaNegativeDataSet,suspectCancerDataSet);
                barChart.setData(data);
                XAxis xAxis = barChart.getXAxis();

                xAxis.setValueFormatter(new IndexAxisValueFormatter(getDates()));
                barChart.getAxisLeft().setAxisMinimum(0);
                data.setValueFormatter(new MyValueFormatter());
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1);
                xAxis.setCenterAxisLabels(true);
                xAxis.setGranularityEnabled(true);
                float barSpace = 0.04f;
                float groupSpace = 0.2f;
                int groupCount = 5;
                data.setBarWidth(0.22f);
                barChart.getDescription().setEnabled(false);
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                barChart.getAxisRight().setGranularity(1f);
                barChart.getAxisRight().setAxisMinimum(0);
                barChart.getAxisLeft().setGranularity(1f);
                barChart.getAxisLeft().setAxisMinimum(0);
                barChart.groupBars(0, groupSpace, barSpace);
                barChart.animateXY(2000,2000);

                data.notifyDataChanged();
                barChart.invalidate();
            }
        });

    }



    private String[] getXAxisValues(List<LineChartVisitItem> lineChartVisitItems) {
        ArrayList<String> xAxis = new ArrayList();

        for (int i = 0; i < lineChartVisitItems.size(); i++) {

            xAxis.add(lineChartVisitItems.get(i).getDateStarted());
        }

        Set<String> s=new HashSet<>(xAxis);
        xAxis.clear();
        xAxis.addAll(s);

        String[] dates=new String[getDates().length];

        for(int i=0;i<xAxis.size();i++)
        {
            dates[i]=xAxis.get(i);
        }

        return dates;
    }


    private ArrayList<BarEntry> viaPositiveEntries(List<LineChartVisitItem> lineChartVisitItems) {
        ArrayList<BarEntry> viaPositiveEntry = new ArrayList();
        ArrayList<Integer> tmp=new ArrayList<>();
        Multimap<String,Integer> days=ArrayListMultimap.create();

        String dates[]=new String[getDates().length];
        dates=getDates();

        for(int j=0;j<dates.length;j++)
        {
            days.put(getDates()[j],0);
        }


        for (int i = 0; i < lineChartVisitItems.size(); i++) {
            if (String.valueOf(lineChartVisitItems.get(i).valueCoded).equals("165162")) {
                days.put(lineChartVisitItems.get(i).dateStarted,lineChartVisitItems.get(i).Count_patient_id);
            }
        }


        for(int i=0;i<dates.length;i++) {
            ints = (List<Integer>) days.get(getDates()[i]);
            tmp.add(addAll(ints));
        }

        for(int x=0;x<tmp.size();x++)
        {
            viaPositiveEntry.add(new BarEntry(x+1,  tmp.get(x)));
        }

        return viaPositiveEntry;
    }



    private ArrayList<BarEntry> viaNegativeEntries(List<LineChartVisitItem> lineChartVisitItems) {
        ArrayList<BarEntry> viaNegativeEntry = new ArrayList();
        ArrayList<Integer> tmp=new ArrayList<>();
        Multimap<String,Integer> days=ArrayListMultimap.create();

        String dates[]=new String[getDates().length];
        dates=getDates();

        for(int j=0;j<dates.length;j++)
        {
            days.put(getDates()[j],0);
        }


        for (int i = 0; i < lineChartVisitItems.size(); i++) {
            if (String.valueOf(lineChartVisitItems.get(i).valueCoded).equals("165161")) {

                days.put(lineChartVisitItems.get(i).dateStarted,lineChartVisitItems.get(i).Count_patient_id);
            }
        }


       for(int i=0;i<dates.length;i++) {
           ints = (List<Integer>) days.get(getDates()[i]);
           tmp.add(addAll(ints));
       }


        for(int x=0;x<tmp.size();x++)
        {
            viaNegativeEntry.add(new BarEntry(x+1,  tmp.get(x)));
        }

        return viaNegativeEntry;
    }


    private ArrayList<BarEntry> suspectCancerEntries(List<LineChartVisitItem> lineChartVisitItems) {
        ArrayList<BarEntry> suspectCancerEntry = new ArrayList();
        ArrayList<Integer> tmp=new ArrayList<>();
        Multimap<String,Integer> days=ArrayListMultimap.create();

        String dates[]=new String[getDates().length];
        dates=getDates();

        for(int j=0;j<dates.length;j++)
        {
            days.put(getDates()[j],0);
        }


        for (int i = 0; i < lineChartVisitItems.size(); i++) {
            if (String.valueOf(lineChartVisitItems.get(i).valueCoded).equals("165163")) {
                days.put(lineChartVisitItems.get(i).dateStarted,lineChartVisitItems.get(i).Count_patient_id);
            }
        }

        for(int i=0;i<dates.length;i++) {
            ints = (List<Integer>) days.get(getDates()[i]);
            tmp.add(addAll(ints));
        }


        for(int x=0;x<tmp.size();x++)
        {
            suspectCancerEntry.add(new BarEntry(x+1,  tmp.get(x)));
        }

        return suspectCancerEntry;
    }


private Integer addAll(List<Integer> ints)
{
    int sum=0;
    for(int i=0;i<ints.size();i++)
    {
        sum+=ints.get(i);

    }
    return sum;
}

private  String[] getDates()
{
    String[] days=new String[6];
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar=Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR,-5);
    for(int i=0;i<5;i++)
    {
        calendar.add(Calendar.DAY_OF_YEAR,1);
        days[i]=simpleDateFormat.format(calendar.getTime());
    }
    return  days;
}

 //format values on top of the bars rto return whole numbers
private  class MyValueFormatter implements IValueFormatter{
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        int num=(int)value;
        return String.valueOf(num);
    }
}




    }
