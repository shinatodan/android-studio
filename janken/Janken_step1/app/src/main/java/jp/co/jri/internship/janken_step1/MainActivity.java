package jp.co.jri.internship.janken_step1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // *step1* グー、チョキ、パーの値の定義
    final int GUU   = 1;
    final int CHOKI = 2;
    final int PAR   = 3;

    // *step1* 勝敗結果の値の定義
    final int WIN   = 1;
    final int LOSS  = 2;
    final int DRAW  = 3;

    // *step1* プレイヤーとスマホの手を入れる変数の宣言
    int playerHand;
    int smartphoneHand;

    // *step1* 勝敗結果を入れる変数の宣言
    int gameResult;

    //　プログラムの起動時に実行するメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // *step1* 画面(activity_main.xml)の表示
        setContentView(R.layout.activity_main);

        // *step1* グー、チョキ、パーのボタンをタップしたらonClick()を呼び出す
        findViewById(R.id.btGuu).setOnClickListener(this);
        findViewById(R.id.btChoki).setOnClickListener(this);
        findViewById(R.id.btPar).setOnClickListener(this);
    }

    //  ボタンをタップした時に実行するメソッド
    @Override
    public void onClick(View view) {

        // *step1* タップしたButtonのidをgetButtonに入れる
        int getButton = view.getId();

        // *step1* getButtonに応じたプレイヤーの手をplayerHandに入れて表示する
        ImageView ivPlayer = findViewById(R.id.ivPlayer);  //Viewにプログラムでの変数名を割り当てる
        if (getButton == R.id.btGuu) {
            playerHand = GUU;
            ivPlayer.setImageResource(R.drawable.guu);
        } else if (getButton == R.id.btChoki) {
            playerHand = CHOKI;
            ivPlayer.setImageResource(R.drawable.choki);
        } else if (getButton == R.id.btPar) {
            playerHand = PAR;
            ivPlayer.setImageResource(R.drawable.par);
        }

        //  *step1* スマホの手（smartphoneHand）を乱数で生成する（1：グー 2:チョキ 3：パー）
        Random rnd = new Random();
        smartphoneHand = rnd.nextInt(3)+1;  //　1～3の乱数を生成してsmartphoneHandに入れる

        // *step1* スマホの手を表示する
        ImageView ivSmartphone = findViewById(R.id.ivSmartphone);  //Viewにプログラムでの変数名を割り当てる
        if (smartphoneHand == GUU) {
            ivSmartphone.setImageResource(R.drawable.guu);
        } else if (smartphoneHand == CHOKI) {
            ivSmartphone.setImageResource(R.drawable.choki);
        } else if (smartphoneHand == PAR) {
            ivSmartphone.setImageResource(R.drawable.par);
        }

        // *step1* 勝敗を判定する
        // グーの場合の処理
        if (playerHand == GUU) {
            if (smartphoneHand == GUU) {
                gameResult=DRAW;
            } else if (smartphoneHand == CHOKI) {
                gameResult=WIN;
            } else if (smartphoneHand == PAR) {
                gameResult = LOSS;
            }
        // チョキの場合の処理
        } else if (playerHand == CHOKI) {
            if (smartphoneHand == GUU) {
                gameResult=LOSS;
            } else if (smartphoneHand == CHOKI) {
                gameResult=DRAW;
            } else if (smartphoneHand == PAR)  {
                gameResult=WIN;
            }
        // パーの場合の処理
        } else if (playerHand == PAR) {
            if (smartphoneHand == GUU) {
                gameResult=WIN;
            } else if (smartphoneHand == CHOKI) {
                gameResult=LOSS;
            } else if (smartphoneHand == PAR) {
                gameResult=DRAW;
            }
        }

        // *step1* 勝負の結果を表示する
        TextView tvMain = findViewById(R.id.tvMain);  //Viewにプログラムでの変数名を割り当てる
        if(gameResult==WIN){
            tvMain.setText("プレイヤーの勝ち!");
        }else if(gameResult==LOSS){
            tvMain.setText("プレイヤーの負け!");
        }else if(gameResult==DRAW){
            tvMain.setText("あいこでしょ！");
        }
    }
}