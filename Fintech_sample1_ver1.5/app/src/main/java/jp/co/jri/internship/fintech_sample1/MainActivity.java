package jp.co.jri.internship.fintech_sample1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Locale;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //　プログラムの起動時に実行するメソッド
    Button editText1; //追記(カレンダー機能)
    Button editText2; //追記(カレンダー機能)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // レイアウト（activity_main.xml）を表示する
        setContentView(R.layout.activity_main);

        // 「表示」ボタンをタップしたらonClick()を呼び出す
        findViewById(R.id.btSearch).setOnClickListener(this);
        // 「遷移」ボタンをタップしたらonClick()を呼び出す
        findViewById(R.id.btLink).setOnClickListener(this);

        // 日付を入力するEditTextをIDで取得
        editText1 = findViewById(R.id.etStart); //追記(カレンダー機能)
        editText2 = findViewById(R.id.etEnd); //追記(カレンダー機能)

        ListView lvHistoricalData = (ListView)  findViewById(R.id.lvHistoricalData);
        lvHistoricalData.setOnItemClickListener(this);
    }

    // ボタンをタップした時に実行するメソッド
    @Override
    public void onClick(View view) {
        // クリックされたボタンを識別
        int getButton = view.getId();
        // 「表示」がクリックされたとき、表示処理用のメソッドを呼び出す
        if (getButton == R.id.btSearch) {
            clickBtSearch(view);
        }
        // 「遷移」がクリックされたとき、画面遷移用のメソッドを呼び出す
        if (getButton == R.id.btLink) {
            clickBtnLink(view);
        }
    }

    //「表示」がクリックされたとき
    @SuppressLint("DefaultLocale")
    public void clickBtSearch(View view) {

        // 収支の集計領域を準備する
        int sumIncome = 0;  // 収入の集計領域
        int sumExpense = 0; // 支出の集計領域

        // 最大支出の格納領域を準備する
        int maxAmount = 0;      // 最大支出の金額
        String maxTransDate = null;    // 最大支出の取引日付
        String maxSupplier = null;     // 最大支出の取引先
        String maxContent = null;      // 最大支出の内容
        String maxUse = null;          // 最大支出の用途

        // 対象期間の始まりと終わりを入力する
        Button etStart = findViewById(R.id.etStart);     // Viewにプログラムでの変数名を割り当てる
        Button etEnd = findViewById(R.id.etEnd);       // Viewにプログラムでの変数名を割り当てる
        String startDate = etStart.getText().toString();   // EditTextに入力した内容を文字列にして変数に渡す
        String endDate = etEnd.getText().toString();     // EditTextに入力した内容を文字列にして変数に渡す

        /*//キーボードの制御
        etStart.setOnFocusChangeListener((v, hasFocus) -> {
             //EditTextのフォーカスが外れた場合
            if (!hasFocus) {
                //ソフトキーボードを非表示にする
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        etStart.clearFocus();
        etEnd.setOnFocusChangeListener((v, hasFocus) -> {
            //EditTextのフォーカスが外れた場合
            if (!hasFocus) {
                //ソフトキーボードを非表示にする
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        etEnd.clearFocus();*/

        // 表示用のList(fintechDataList)を用意する
        List<FintechData> fintechDataList = new ArrayList<>();

        // LocalFintechDateBase.txt(Androidローカルファイル)が存在しない場合、FintechDataBase.csvからデータを読み込み、
        // LocalFintechDateBase.txtに書き出したのち、List(fintechDataList)を作成する
        // LocalFintechDateBase.txtが存在する場合、ローカルファイルからList(fintechDataList)を作成する
        CsvReader parser = new CsvReader();
        String filename = "LocalFintechDateBase.txt";
        File file = this.getFileStreamPath(filename);
        parser.readerFintechDataBase(getApplicationContext(), file.exists());

        // text.txt(Androidローカルファイル)から1行取り出し、表示用のList(fintechDataList)に入れる
        // 上記をtext.txtが終わるまで繰り返す
        for (FintechData fdata : parser.fintechObjects) {
            // transDateがstartDateから、endDateまでの日付のデータのみListに入れる
            if (fdata.getTransDate().compareTo(startDate) >= 0) {
                if (fdata.getTransDate().compareTo(endDate) <= 0) {
                    fintechDataList.add(fdata);
                    // 収支の合計を集計する
                    if (fdata.getAmount() >= 0) {
                        sumIncome = sumIncome + fdata.getAmount();   // 収入の合計を求める
                    } else {
                        sumExpense = sumExpense + fdata.getAmount(); // 支出の合計を求める
                    }
                    // 最大支出を求める
                    if (maxAmount >= fdata.getAmount()) {
                        maxAmount = fdata.getAmount();
                        maxTransDate = fdata.getTransDate();
                        maxSupplier = fdata.getSupplier();
                        maxContent = fdata.getContent();
                        maxUse = fdata.getUse();
                    }
                }
            }
        }

        // Adapterに表示用のList(fintechDataList)を受け渡す
        List<Map<String, ?>> listData = fintechDataToMapList(fintechDataList);  // Adapterに渡す形式のlist型変数の宣言と初期化
        SimpleAdapter adapter = new SimpleAdapter(                  // ()内で指定した内容のAdapterを生成する
                MainActivity.this,
                listData,                                           // ListView用に自作したレイアウトにFintechDataのどの項目を表示するかを指定する
                R.layout.custom_list_layout,                        // 自作したレイアウト名
                new String[]{"transDate", "content", "amount"},     // 表示するFintechDataの項目を指定
                new int[]{R.id.tvList1, R.id.tvList2, R.id.tvList3} // 自作したレイアウトのViewのidを指定
        );

        // Adapterの内容をlistViewに表示する
        ListView lvHistoricalData = (ListView) findViewById(R.id.lvHistoricalData); //Viewにプログラムでの変数名を割り当てる
        lvHistoricalData.setAdapter(adapter);   //Adapterの内容をlvHistoricalDataに表示する

        // 収入の合計を出力する
        TextView tvIncome = (TextView) findViewById(R.id.tvIncome);
        tvIncome.setText(String.format("%,d", sumIncome));

        // 支出の合計を出力する
        TextView tvExpense = (TextView) findViewById(R.id.tvExpense);
        tvExpense.setText(String.format("%,d", sumExpense));

        // 最大支出を表示する（各要素を紐づけ→値を反映）
        TextView tvMaxTransDate = (TextView) findViewById(R.id.tvMaxTransDate);
        TextView tvMaxSupplier = (TextView) findViewById(R.id.tvMaxSupplier);
        TextView tvMaxContent = (TextView) findViewById(R.id.tvMaxContent);
        TextView tvMaxUse = (TextView) findViewById(R.id.tvMaxUse);
        TextView tvMaxAmount = (TextView) findViewById(R.id.tvMaxAmount);

        tvMaxTransDate.setText(maxTransDate);
        tvMaxSupplier.setText(maxSupplier);
        tvMaxContent.setText(maxContent);
        tvMaxUse.setText(maxUse);
        tvMaxAmount.setText(String.format("%,d", maxAmount));
    }

    //「遷移」がクリックされたとき
    public void clickBtnLink(View view) {
        Intent intent = new Intent(this, Main2Activity.class);  //インテントの作成
        // ここから追加
        // 7月・8月の収入in、支出out
        int in_7 = 0;
        int in_8 = 0;
        int out_7 = 0;
        int out_8 = 0;
        int amount_7 = 0;
        int amount_8 = 0;
        // 基準となる日付
        String base_date = "2022/08/01";

        // 遷移先で表示するため、遷移ボタンのボタン名を取得しintentに格納する（消去）
//        TextView tv = (TextView) view;
//        String buttonName = tv.getText().toString();
//        intent.putExtra("ButtonName",buttonName);

        // 7月、8月の支出、収入をそれぞれ計算
        CsvReader parser = new CsvReader();
        String filename = "LocalFintechDateBase.txt";
        File file = this.getFileStreamPath(filename);
        parser.readerFintechDataBase(getApplicationContext(), file.exists());

        for (FintechData fdata : parser.fintechObjects) {
            if (fdata.getAmount() >= 0) {
                if (fdata.getTransDate().compareTo(base_date) < 0) {
                    in_7 += fdata.getAmount();
                    amount_7 = fdata.getBalance();
                } else{
                    in_8 += fdata.getAmount();
                    amount_8 = fdata.getBalance();
                }
            } else {
                if (fdata.getTransDate().compareTo(base_date) < 0) {
                    out_7 += fdata.getAmount();
                    amount_7 = fdata.getBalance();
                } else {
                    out_8 += fdata.getAmount();
                    amount_8 = fdata.getBalance();
                }
            }
        }

        // 7月、8月の収支それぞれに名前を付けてintentに格納
        intent.putExtra("in_7", Integer.valueOf(in_7).toString());
        intent.putExtra("in_8", Integer.valueOf(in_8).toString());
        intent.putExtra("out_7", Integer.valueOf(out_7).toString());
        intent.putExtra("out_8", Integer.valueOf(out_8).toString());
        // 月末残高
        intent.putExtra("amount_7", Integer.valueOf(amount_7).toString());
        intent.putExtra("amount_8", Integer.valueOf(amount_8).toString());
        // ここまで

        startActivity(intent); //画面遷移
    }

    // Adapterに渡す形式のlist型変数の宣言と初期化
    private List<Map<String, ?>> fintechDataToMapList(List<FintechData> fintechDataList) {
        List<Map<String, ?>> data = new ArrayList<>();
        for (FintechData fintechData : fintechDataList) {
            data.add(fintechDataToMap(fintechData));
        }
        return data;
    }

    // Adapterに渡す形式のlist型変数の宣言と初期化（詳細）
    @SuppressLint("DefaultLocale")
    private Map<String, ?> fintechDataToMap(FintechData fintechData) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", fintechData.getId());
        map.put("transDate", fintechData.getTransDate());
        map.put("transTime", fintechData.getTransTime());
        map.put("service", fintechData.getService());
        map.put("category", fintechData.getCategory());
        map.put("supplier", fintechData.getSupplier());
        map.put("content", fintechData.getContent());
        map.put("use", fintechData.getUse());
        map.put("amount", String.format("%,d", fintechData.getAmount()));
        map.put("balance", String.format("%,d", fintechData.getBalance()));
        return map;
    }

    //カレンダーで日付入力
    //追記(カレンダー機能)
    public void calendarButton1(View view) {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view1, year, month, dayOfMonth) -> editText1.setText(String.format(Locale.JAPAN, "%02d/%02d/%02d", year, month + 1, dayOfMonth)),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    //追記(カレンダー機能)
    public void calendarButton2(View view) {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view1, year, month, dayOfMonth) -> editText2.setText(String.format(Locale.JAPAN, "%02d/%02d/%02d", year, month + 1, dayOfMonth)),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast myToast = Toast.makeText(
                getApplicationContext(),
                "これはメモですこれはメモですこれはメモですこれはメモですこれはメモですこれはメモです",
                Toast.LENGTH_SHORT
        );
        myToast.show();
    }
}