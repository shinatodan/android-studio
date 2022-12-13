package jp.co.jri.internship.fintech_sample1;

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

public class Tab2Fragment extends Fragment {

    protected BarChart chart;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // レイアウト（tab2_fragment.xml）を作成する
        View v = inflater.inflate(R.layout.tab2_fragment, container, false);

        // 7月・8月末残高それぞれをintentから取り出す
        int amount_7 = Integer.parseInt(getArguments().getString("amount_7"));
        int amount_8 = Integer.parseInt(getArguments().getString("amount_8"));

        // 折れ線グラフ表示領域を取得
        chart = v.findViewById(R.id.LineChart);

        BarData data = new BarData(getBarData(amount_7, amount_8));
        chart.setData(data);

        //Y軸(左)
        YAxis left = chart.getAxisLeft();
        left.setAxisMinimum(0);
        left.setAxisMaximum(1000000);
        left.setGranularity(100000);
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


        return v;
    }

    //折れ線グラフのデータをセットする関数
    private List<IBarDataSet> getBarData(int amount_7, int amount_8) {
        // 表示させるデータをリストに格納
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, amount_7));
        entries.add(new BarEntry(3, amount_8));

        // 折れ線グラフ表示用のデータセットに準備したリストをセット
        BarDataSet dataSet = new BarDataSet(entries, "Bar");

        List<IBarDataSet> Bars = new ArrayList<>();
        Bars.add(dataSet);

        return Bars;
    }
}