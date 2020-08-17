package com.example.muscleapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.muscleapplication.entity.IncludeListEntity;
import com.example.muscleapplication.entity.MenuEntity;
import com.example.muscleapplication.entity.ResultEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    // ListViewに表示する項目を準備
    List<String> recordData = new ArrayList<>();
    String okFlag;
    List<String> okFragData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // タイトルの変更
        Intent intent = getIntent();
        MenuEntity menuEntity = (MenuEntity) intent.getSerializableExtra("menuEntity");
        setTitle(menuEntity.getTrainingName());

        // 閉じるボタン作成
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        // リスト項目とListViewを対応づけるArrayAdapterを用意
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recordData);
        // ListViewにArrayAdapterを設定
        ListView recordData = findViewById(R.id.recordData);
        recordData.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // RESULT_OKをセットしていない遷移は弾く
        if (Activity.RESULT_OK == resultCode) {
            // リクエストコードにより判別
            switch (requestCode) {
                // ResultActivityからの返却
                case 1:
                    IncludeListEntity includeListEntity = (IncludeListEntity) intent.getSerializableExtra("includeListEntity");
                    MenuEntity menuEntity = (MenuEntity) intent.getExtras().get("menuEntity") ;
                    createList(includeListEntity, menuEntity);
                    break;
                default:
                    break;
            }
        }
    }

    // 筋トレ結果のリスト追加
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createList(final IncludeListEntity includeListEntity, final MenuEntity menuEntity) {
        // 日付の算出
        LocalDateTime today = LocalDateTime.now();
        String strToday = today.format(DateTimeFormatter.ISO_DATE);
        // トータルボリュームの算出
        double total = 0.0;
        int count = 0;
        okFlag = "FAIL";
        for (int i = 0 ; i < includeListEntity.getResultEntityList().size() ; i++) {
            total += includeListEntity.getResultEntityList().get(i).getWeight1() * includeListEntity.getResultEntityList().get(i).getRep1();

            // 推奨重量とを満たした場合
            if (includeListEntity.getResultEntityList().get(i).getWeight1() >= includeListEntity.getOptimalWeight()
            && includeListEntity.getResultEntityList().get(i).getRep1() >= includeListEntity.getOptimalRep()) {
                count++;
            }
        }
        // 推奨セットを満たした場合
        if (count >= includeListEntity.getOptimalSet()) {
            okFlag = "CLEAR";
        }

        // メッセージの作成
        String strMsg = "トータルボリューム：" + total + "　　　　\n" + strToday +
                "                                                " + okFlag;
        // リストを上から追加する
        recordData.add(0, strMsg);
        okFragData.add(0, okFlag);

        // リスト項目とListViewを対応づけるArrayAdapterを用意
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recordData);
        // ListViewにArrayAdapterを設定
        ListView recordData = findViewById(R.id.recordData);
        // ListViewをクリックした時に遷移するようにする
        recordData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(RecordActivity.this, NoteActivity.class);
                intent.putExtra("includeListEntity", includeListEntity);
                intent.putExtra("menuEntity", menuEntity);
                startActivityForResult(intent, 1);
            }
        });
        recordData.setAdapter(adapter);
    }

    // オプションメニューを作成する
    public boolean onCreateOptionsMenu(Menu menu) {
        // menuにcustom_menuレイアウトを適用
        getMenuInflater().inflate(R.menu.custom_menu_add, menu);
        // オプションメニューを表示する場合はtrue
        return true;
    }

    // MainActivityのintentに乗ってきたmenuEntityをゲットして、ResultActivityに渡す
    public void passIntent() {
        // MainActivityのintentゲット
        Intent getIntent = getIntent();
        MenuEntity menuEntity = (MenuEntity) getIntent.getSerializableExtra("menuEntity");

        // レップ数のセットのための振り分け
        int setCount = 0;
        for (int i = 0 ; i < recordData.size() ; i++) {
            // okFlagがCLEAR、つまりリストと一番上がCLEARなら、次のaddボタン押されたときの推奨メニューを変える
            if (okFragData.get(i).equals("CLEAR")) {
                setCount++;
            }
        }
        menuEntity.setSetCount(setCount);

        // ResultActivityに渡すintent作成
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("menuEntity", menuEntity);
        startActivityForResult(intent, 1);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // 閉じるボタンを押された場合
            case android.R.id.home:
                finish();
                break;
            // addボタンを押された場合（ResultActivityに遷移）
            case R.id.action_add:
                passIntent();
                break;
            default:
        }
        return true;
    }
}