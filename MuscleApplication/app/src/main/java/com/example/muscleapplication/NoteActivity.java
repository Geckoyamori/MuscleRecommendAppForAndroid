package com.example.muscleapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.muscleapplication.entity.IncludeListEntity;
import com.example.muscleapplication.entity.OptimalEntity;
import com.example.muscleapplication.entity.MenuEntity;
import com.example.muscleapplication.entity.ResultEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {
    // 1setの結果を格納するエンティティ
    ResultEntity resultEntity = new ResultEntity();
    private int count;
    // スピナーのidを保管するリスト
    List<String> noteWeightList = new ArrayList<>();
    List<String> noteRepList = new ArrayList<>();
    // スピナーの値を保持するエンティティを保管するリスト
    List<ResultEntity> resultEntityList = new ArrayList<>();
    // スピナーの値と推奨メニューを保持するエンティティを保管するリストをもつエンティティ(intentに乗せるため)
    IncludeListEntity includeListEntity = new IncludeListEntity();
    int count10;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // タイトルの変更
        setTitle("記録を追加");

        // 閉じるボタン作成
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        // スピナーセット
        for (int i = 0 ; i < 5 ; i++) {
            createSpinner();
        }
        // intentの取得
        Intent intent = getIntent();
        MenuEntity menuEntity = (MenuEntity)intent.getSerializableExtra("menuEntity");
        if (menuEntity.getSetCount()%4 == 0) {
            // 推奨メニューのセット
            setRecommendMenu(6);
        } else if (menuEntity.getSetCount()%4 == 1) {
            setRecommendMenu(8);
        } else if (menuEntity.getSetCount()%4 == 2) {
            setRecommendMenu(12);
        } else if (menuEntity.getSetCount()%4 == 3) {
            setRecommendMenu(10);
        }

        // 追加ボタンを押すとセットが増加
        Button button = findViewById(R.id.set_add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                createSpinner();
            }
        });
    }

    // 推奨メニューの設定
    public void setRecommendMenu(int rep) {
        Intent intent = getIntent();
        MenuEntity menuEntity = (MenuEntity)intent.getSerializableExtra("menuEntity");
        double maxWeight = menuEntity.getMaxWeight();

        for (int i = 0 ; i < (menuEntity.getSetCount()+1)/4 ; i++) {
            maxWeight += 2.5;
        }

        // 基準値(10rep3set)の場合のトータルボリューム
        double totalVol = maxWeight*3*10;

        // 基準値の場合の最大挙上重量
        double max1repWeight = maxWeight * (1.0 + (10.0 / 40.0));

        List<OptimalEntity> optimalList = new ArrayList<>();
        for (int i = 5; i < 17 ; i++) {
            OptimalEntity optimalEntity = new OptimalEntity();
            // repに対する相応重量(2.5刻み)
            double optimalWeight = Math.round((40.0*max1repWeight/(double) (40.0 + i))*4.0/10.0) * 10.0/4.0;
            // 必要セット数
            int optimlSet = (int)Math.ceil(totalVol/optimalWeight/i);
            // 目指すボリューム
            double optimalVolume = optimalWeight*optimlSet*i;

            optimalEntity.setOptimalRep(i);
            optimalEntity.setOptimalSet(optimlSet);
            optimalEntity.setOptimalWeight(optimalWeight);
            optimalEntity.setOptimalVolume(optimalVolume);
            optimalList.add(optimalEntity);
        }
        double optimalWeight = optimalList.get(rep-5).getOptimalWeight();
        int optimalRep = optimalList.get(rep-5).getOptimalRep();
        int optimalSet = optimalList.get(rep-5).getOptimalSet();
        double optimalVolume = optimalList.get(rep-5).getOptimalVolume();
        String msg = optimalWeight + "kg × " + optimalRep + "rep × " + optimalSet + "set" +
                "     トータルボリューム：" + optimalVolume + "kg";

        // テキストにセット
        TextView recommendText = findViewById(R.id.recommendMenu);
        recommendText.setText(msg);

        includeListEntity.setOptimalWeight(optimalWeight);
        includeListEntity.setOptimalRep(optimalRep);
        includeListEntity.setOptimalSet(optimalSet);
        includeListEntity.setOptimalVolume(optimalVolume);
    }

    // スピナーの生成
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createSpinner() {
        // NoteActivityのレイアウトを取得
        LinearLayout noteLayout = findViewById(R.id.note_layout);
        // 個別で定義したspinnerLayoutを取得
        LinearLayout spinnerLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.spinner_layout, null);
        // spinnerLayoutの中のテキストを取得して、変更
        TextView textView = spinnerLayout.findViewById(R.id.text_set);
        String strSetNum = (count + 1) + "set";
        textView.setText(strSetNum);
        // spinnerLayoutの中の重量スピナーを取得して、変更
        Spinner weightSpinner = spinnerLayout.findViewById(R.id.weight1);
        setWeightSpinnerItemsOnly(weightSpinner);
        // スピナーにidを設定して、それをリストとして保管
        weightSpinner.setId(View.generateViewId());
        noteWeightList.add(String.valueOf(weightSpinner.getId()));
        // spinnerLayoutの中の回数スピナーを取得して、変更
        Spinner repSpinner = spinnerLayout.findViewById(R.id.rep1);
        setRepSpinnerItemsOnly(repSpinner);
        // 回数スピナーにidを設定して、それをリストとして保管
        repSpinner.setId(View.generateViewId());
        noteRepList.add(String.valueOf(repSpinner.getId()));

        // MainAtivityのレイアウトにスピナーレイアウトを追加
        noteLayout.addView(spinnerLayout);
        count++;
    }

    // 重量スピナーの中身をセットする専用のメソッド
    public void setWeightSpinnerItemsOnly(Spinner spinner) {
        // リストの作成
        ArrayList<String> weightList = new ArrayList<>();
        double num = 0.0;
        for (int i = 0; i < 300; i++) {
            String strNum = String.valueOf(num);
            weightList.add(strNum);
            num += 2.5;
        }
        // アダプタの作成
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightList);
        // ドロップダウンの設定
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // セット
        spinner.setAdapter(adapter);
    }

    // 回数スピナーの中身をセットする専用のメソッド
    public void setRepSpinnerItemsOnly(Spinner spinner) {
        // リストの作成
        ArrayList<String> weightList = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < 100; i++) {
            String strNum = String.valueOf(num);
            weightList.add(strNum);
            num ++;
        }
        // アダプタの作成
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightList);
        // ドロップダウンの設定
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // セット
        spinner.setAdapter(adapter);
    }

    // オプションメニューを作成する
    public boolean onCreateOptionsMenu(Menu menu) {
        // menuにcustom_menuレイアウトを適用
        getMenuInflater().inflate(R.menu.custom_menu_save, menu);
        // オプションメニューを表示する場合はtrue
        return true;
    }

    // スピナーの情報をエンティティリストに格納
    public void saveInfo() {
        for (int i = 0 ; i < count ; i++) {
            ResultEntity resultEntity = new ResultEntity();
            // 対応するスピナーの数値をエンティティに格納
            Spinner weightSpinner = findViewById(Integer.parseInt(noteWeightList.get(i)));
            resultEntity.setWeight1(Double.parseDouble(weightSpinner.getSelectedItem().toString()));
            Spinner repSpinner = findViewById(Integer.parseInt(noteRepList.get(i)));
            resultEntity.setRep1(Double.parseDouble(repSpinner.getSelectedItem().toString()));

            // エンティティを保管するリスト
            resultEntityList.add(resultEntity);
        }
        includeListEntity.setResultEntityList(resultEntityList);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            // 閉じるボタンを押された場合
            case android.R.id.home:
                finish();
                break;
            // saveボタンを押された場合
            case R.id.action_save:
                saveInfo();
                Intent getIntent = getIntent();
                MenuEntity menuEntity = (MenuEntity) getIntent.getSerializableExtra("menuEntity");
                Intent intent = new Intent(this, RecordActivity.class);
                intent.putExtra("includeListEntity", (Serializable) includeListEntity);
                intent.putExtra("menuEntity", menuEntity);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;

            default:
        }
        return true;
    }

}