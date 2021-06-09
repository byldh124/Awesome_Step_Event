package com.moondroid.awesome_step_event.tabfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.moondroid.awesome_step_event.R;

import java.util.ArrayList;
import java.util.List;

public class TabFragment03 extends Fragment {

    private BarChart chart;

    List<BarEntry> entries;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_tab_03, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chart = view.findViewById(R.id.bar_chart_week);

        entries = new ArrayList<>();

        entries.add(new BarEntry(1f, 8000f));
        entries.add(new BarEntry(2f, 5600f));
        entries.add(new BarEntry(3f, 7200f));
        entries.add(new BarEntry(4f, 9300f));
        entries.add(new BarEntry(5f, 11200f));
        entries.add(new BarEntry(6f, 4800f));
        entries.add(new BarEntry(7f, 6800f));
        entries.add(new BarEntry(8f, 8000f));
        entries.add(new BarEntry(9f, 5600f));
        entries.add(new BarEntry(10f, 7200f));
        entries.add(new BarEntry(11f, 9300f));
        entries.add(new BarEntry(12f, 11200f));

        BarDataSet barDataSet = new BarDataSet(entries, "step");
        barDataSet.setColor(ColorTemplate.rgb("#30c9de"));
        BarData barData = new BarData(barDataSet);

        chart.setData(barData);
        chart.invalidate();

        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.animateY(1000);
        chart.setDescription(null);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisRight().setDrawGridLines(false);
//        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisRight().setInverted(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setTouchEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setBorderWidth(1f);
    }
}
