package com.tkxel.admin.ordertaking.AllClass;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tkxel.admin.ordertaking.R;
import com.tkxel.admin.ordertaking.Utilities.MyConstants;

import java.util.ArrayList;

public class GraphActivity extends Activity {
    PieChart pieChart;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;
    ImageView imgchartbar;
    BarChart Barchart;
    int isPia = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);

        imgchartbar = (ImageView) findViewById(R.id.imgchartbar);
        pieChart = (PieChart) findViewById(R.id.chart_pia);
        Barchart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        Barchart.setData(data);
        Barchart.setDescription("");
        Barchart.animateXY(500, 500);
        Barchart.invalidate();
        fun_piaChart();

        findViewById(R.id.add_new_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgchartbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPia == 0) {
                    isPia = 1;

                    pieChart.setVisibility(View.VISIBLE);
                    Barchart.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imgchartbar.setImageDrawable(getResources().getDrawable(R.drawable.barchart, getApplicationContext().getTheme()));
                    } else {
                        imgchartbar.setImageDrawable(getResources().getDrawable(R.drawable.barchart));
                    }
                } else {
                    isPia = 0;

                    pieChart.setVisibility(View.GONE);
                    Barchart.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imgchartbar.setImageDrawable(getResources().getDrawable(R.drawable.piehart, getApplicationContext().getTheme()));
                    } else {
                        imgchartbar.setImageDrawable(getResources().getDrawable(R.drawable.piehart));
                    }
                }

            }
        });
    }

    int totleTarget = 0, totleactived = 0;

    void funDat() {
        for (int i = 0; i < MyConstants.listEmployeeTarget.size(); i++) {
            totleactived = totleactived + MyConstants.listEmployeeTarget.get(i).get_SaleTarget();
            totleTarget = totleTarget + MyConstants.listEmployeeTarget.get(i).get_TargetAchieved();


        }

    }

    void fun_piaChart() {

        funDat();
        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        AddValuesToPIEENTRY();

        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "");
        pieChart.setDescription("");
        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieData.setValueTextColor(Color.parseColor("#ffffff"));
        pieData.setValueTextSize(10);
           // (255,0,0)
        int[] colors = {Color.rgb(34,139,34), Color.rgb(255,0,0)};
        pieDataSet.setColors(colors);

        pieChart.setHoleRadius(0);
        pieChart.setDrawCenterText(false);
        pieChart.setData(pieData);

        pieChart.setDrawHoleEnabled(false);
        //pieChart.set
        pieChart.animateY(500);
    }

    public void AddValuesToPIEENTRY() {
        // MyConstants.listEmployeeTarget


        entries.add(new BarEntry(totleTarget, 0));
        entries.add(new BarEntry(totleactived, 1));


    }

    public void AddValuesToPieEntryLabels() {
        PieEntryLabels.add("Total Target ");
        PieEntryLabels.add("Target Achieved");



    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();


        for (int i = 0; i < MyConstants.listEmployeeTarget.size(); i++) {
            //   entries.add(new BarEntry(MyConstants.listEmployeeTarget.get(i).get_TargetAchieved(), i));
            BarEntry v1e1 = new BarEntry(MyConstants.listEmployeeTarget.get(i).get_TargetAchieved(), i); // Jan
            valueSet1.add(v1e1);
        }


        ArrayList<BarEntry> valueSet2 = new ArrayList<>();


        for (int i = 0; i < MyConstants.listEmployeeTarget.size(); i++) {
            //   entries.add(new BarEntry(MyConstants.listEmployeeTarget.get(i).get_TargetAchieved(), i));
//            BarEntry v1e1 = new BarEntry(MyConstants.listEmployeeTarget.get(i).get_TargetAchieved(), i); // Jan
//            valueSet1.add(v1e1);

            BarEntry v2e1 = new BarEntry(MyConstants.listEmployeeTarget.get(i).get_SaleTarget(), i); // Jan
            valueSet2.add(v2e1);


        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Target Achieved");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Target Set");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();


        for (int i = 0; i < MyConstants.listEmployeeTarget.size(); i++) {
            // PieEntryLabels.add(MyConstants.listEmployeeTarget.get(i).get_Month()+"");
            xAxis.add(MyConstants.listEmployeeTarget.get(i).get_Month() + "");
        }

        return xAxis;
    }
}