package jp.co.jri.internship.fintech_sample1;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// 追加
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;


public class Tab1Fragment extends Fragment {

    protected BarChart chart;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // レイアウト（tab1_fragment.xml）を作成する
        View v =inflater.inflate(R.layout.tab1_fragment,container,false);

        // 7月・8月支出・収入それぞれをintentから取り出す
        int in_7 = Integer.parseInt(getArguments().getString("in_7"));
        int in_8 = Integer.parseInt(getArguments().getString("in_8"));
        int out_7 = Integer.parseInt(getArguments().getString("out_7"));
        int out_8 = Integer.parseInt(getArguments().getString("out_8"));

        // 棒グラフ表示領域を取得
        chart = v.findViewById(R.id.barChart);

        // 棒グラフにデータをセット。getBarDataの関数は別途定義
        BarData data = new BarData(getBarData(in_7, in_8, out_7, out_8));
        chart.setData(data);

        //Y軸(左)
        YAxis left = chart.getAxisLeft();
        left.setAxisMinimum(-600000);
        left.setAxisMaximum(600000);
        left.setGranularity(200000);
        left.setDrawTopYLabelEntry(true);

        //Y軸(右)
        YAxis right = chart.getAxisRight();
        right.setDrawLabels(false);
        right.setDrawGridLines(false);
        right.setDrawZeroLine(true);
        right.setDrawTopYLabelEntry(true);

        //X軸
        XAxis xAxis = chart.getXAxis();
        //X軸に表示するLabelのリスト(最初の""は原点の位置)
        final String[] labels = {"", "7月", "", "8月"};

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        XAxis bottomAxis = chart.getXAxis();
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setDrawLabels(true);
        bottomAxis.setDrawGridLines(false);
        bottomAxis.setDrawAxisLine(true);

        // グラフ上の表示
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setClickable(false);

        // 凡例を非表示
        chart.getLegend().setEnabled(false);

        chart.setScaleEnabled(false);
        // アニメーション
        chart.animateY(1200, Easing.EasingOption.Linear);

        return v;
    }

    //棒グラフのデータをセットする関数
    private List<IBarDataSet> getBarData(int in_7, int in_8, int out_7, int out_8){
        // 表示させるデータをリストに格納
        ArrayList<BarEntry> entries_1 = new ArrayList<>();
        ArrayList<BarEntry> entries_2 = new ArrayList<>();
        entries_1.add(new BarEntry(1, in_7));
        entries_1.add(new BarEntry(3, in_8));
        entries_2.add(new BarEntry(1, out_7));
        entries_2.add(new BarEntry(3, out_8));

        // 棒グラフ表示用のデータセットに準備したリストをセット
        BarDataSet dataSet_1 = new BarDataSet(entries_1, "bar");
        dataSet_1.setColors(Color.rgb(20,10,200));
        BarDataSet dataSet_2 = new BarDataSet(entries_2, "bar");
        dataSet_2.setColors(Color.rgb(200,10,20));

        //ハイライトさせない
        dataSet_1.setHighlightEnabled(false);
        dataSet_2.setHighlightEnabled(false);

        List<IBarDataSet> bars = new ArrayList<>();
        bars.add(dataSet_1);
        bars.add(dataSet_2);

        return bars;
    }
}
