package jp.co.jri.internship.fintech_sample1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TapPagerAdapter extends FragmentStateAdapter {

    // 追加
    String In_7, In_8, Out_7, Out_8;
    // さらに追加
    String Amount_7, Amount_8;

    public TapPagerAdapter(Main2Activity fragment, String in_7, String in_8, String out_7, String out_8, String amount_7, String amount_8){
        super(fragment);
        // 追加
        In_7 = in_7;
        In_8 = in_8;
        Out_7 = out_7;
        Out_8 = out_8;
        // さらに追加
        Amount_7 = amount_7;
        Amount_8 = amount_8;
    }

    // 指定されたタブの位置（position）に対応するタブページ（Fragment）を作成する
    @NonNull
    @Override
    public Fragment createFragment(int position){
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        // tab1選択時
        if (position == 0) {
            fragment = new Tab1Fragment();

            // 追加
            bundle.putString("in_7", In_7);
            bundle.putString("in_8", In_8);
            bundle.putString("out_7", Out_7);
            bundle.putString("out_8", Out_8);
        }
        // tab2選択時
        else if (position == 1){
            fragment = new Tab2Fragment();

            // 追加
            bundle.putString("amount_7", Amount_7);
            bundle.putString("amount_8", Amount_8);
        }
        assert fragment != null;
        fragment.setArguments(bundle);
        return fragment;
    }

    //タブの数を返す
    @Override
    public int getItemCount(){
      return 2;
    }
}