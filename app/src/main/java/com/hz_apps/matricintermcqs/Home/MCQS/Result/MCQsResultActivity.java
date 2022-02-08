package com.hz_apps.matricintermcqs.Home.MCQS.Result;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hz_apps.matricintermcqs.Home.MCQS.MCQS;
import com.hz_apps.matricintermcqs.databinding.ActivityMcqsResultBinding;

import java.util.ArrayList;
import java.util.List;

public class MCQsResultActivity extends AppCompatActivity {
    private List<MCQS> mcqsList;
    int TotalQuestions, Attempted, CorrectAnswers, WrongAnswers, Unattempted;
    int accuracy;
    ActivityMcqsResultBinding binding;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMcqsResultBinding.inflate(getLayoutInflater());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(binding.getRoot());

        mcqsList = (List<MCQS>) getIntent().getSerializableExtra("MCQsList");
        getMCQsResult();

        binding.correctAnswersMcqsResult.setText(String.valueOf(CorrectAnswers));
        binding.wrongMcqsResult.setText(String.valueOf(WrongAnswers));
        binding.unattemptedMcqsResult.setText(String.valueOf(Unattempted));
        binding.accuracyResultActivity.setText(accuracy + "%");

        pieChart = binding.pieChartResultActivity;
        setUpChart();
        loadPieCharData();

    }

    private void getMCQsResult(){
        TotalQuestions = mcqsList.size();
        for (MCQS mcqs : mcqsList){
            if (mcqs.getUserAns() == 'N'){
                Unattempted += 1;
            }
            else{
                Attempted +=1;
                if (mcqs.getUserAns() == mcqs.getAns()){
                    CorrectAnswers += 1;
                }else{
                    WrongAnswers += 1;
                }
            }
        }
        //Finding percentage
        float attemptedQuestions = (float) Attempted;
        float correctAnswers = (float) CorrectAnswers;
        accuracy = (int) ((correctAnswers/attemptedQuestions)*100);
    }

    private void loadPieCharData(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if (CorrectAnswers != 0){
            entries.add(new PieEntry(CorrectAnswers, "CorrectAnswers"));
            colors.add(ColorTemplate.MATERIAL_COLORS[0]);
        }
        if (WrongAnswers != 0){
            entries.add(new PieEntry(WrongAnswers, "Wrong Answers"));
            colors.add(ColorTemplate.MATERIAL_COLORS[2]);
        }
        if (Unattempted !=0) {
            entries.add(new PieEntry(Unattempted, "Un-attempted"));
//            colors.add(ColorTemplate.MATERIAL_COLORS[3]);
            colors.add(ColorTemplate.rgb("#6C6C6C"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Test Report");
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(14f);
        pieData.setValueTextColor(Color.BLACK);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void setUpChart(){
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(6f);
        pieChart.getDescription().setEnabled(false);

        Legend chartLegend = pieChart.getLegend();
        chartLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        chartLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        chartLegend.setOrientation(Legend.LegendOrientation.VERTICAL);
        chartLegend.setDrawInside(false);
        chartLegend.setEnabled(true);
    }
}