package jp.co.jri.internship.fintech_sample1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // レイアウト（activity_main2.xml）を表示する
        setContentView(R.layout.activity_main2);

        // intentに格納されている遷移元のidを受け取る
        Intent intent = getIntent();

        // ここから変更
        String in_7 = intent.getStringExtra("in_7");
        String in_8 = intent.getStringExtra("in_8");
        String out_7 = intent.getStringExtra("out_7");
        String out_8 = intent.getStringExtra("out_8");
        // さらに変更
        String amount_7 = intent.getStringExtra("amount_7");
        String amount_8 = intent.getStringExtra("amount_8");

        // アダプタ(TapPagerAdapter)を用いてタブ切り替え時のViewPager2の内容表示を制御する.
        // 遷移元のボタン名（buttonName）もアダプタに受け渡す
        ViewPager2 pager = (ViewPager2)findViewById(R.id.pager);
        // 受け渡し変数追加
        TapPagerAdapter adapter = new TapPagerAdapter(this, in_7, in_8, out_7, out_8, amount_7, amount_8);
        pager.setAdapter(adapter);
        // ここまで

        String[] tab_name = {"月別収支", "月末資産"};
        // TabLayoutとViewPager2を関連付ける（押下されたタブと内容表示を関連付ける）
        TabLayout tabs = (TabLayout)findViewById(R.id.tab_layout);
        new TabLayoutMediator(
                tabs,
                pager,
                (tab, position) -> tab.setText(tab_name[position])
        ).attach();
    }
}

