package com.example.muscleapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.muscleapplication.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private EditText registerTrainingName;
    private RadioGroup trainingCategoryRg;
    private RadioButton trainigCategoryBt;
    private RadioGroup trainingPartRg;
    private RadioButton trainingPartBt;
    private EditText maxWeight;
    List<MenuEntity> menuList = new ArrayList<>();
    MenuEntity menuEntity = new MenuEntity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // タイトルの変更
        setTitle("メニューの追加");

        // 閉じるボタン作成
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

    }

    // オプションメニューを作成する
    public boolean onCreateOptionsMenu(Menu menu){
        // menuにcustom_menuレイアウトを適用
        getMenuInflater().inflate(R.menu.custom_menu_save, menu);
        // オプションメニューを表示する場合はtrue
        return true;
    }

    public void saveEntity() {

        try {
            // 入力値をオブジェクトとして取得
            registerTrainingName = findViewById(R.id.register_training_name);
            trainingCategoryRg = findViewById(R.id.training_category);
            trainigCategoryBt = findViewById(trainingCategoryRg.getCheckedRadioButtonId());
            trainingPartRg = findViewById(R.id.training_part);
            trainingPartBt = findViewById(trainingPartRg.getCheckedRadioButtonId());
            maxWeight = findViewById(R.id.max_weight);

            menuEntity.setTrainingName(registerTrainingName.getText().toString());
            menuEntity.setMaxWeight(Double.parseDouble(maxWeight.getText().toString()));
            menuEntity.setTrainingCategory(trainigCategoryBt.getText().toString());
            menuEntity.setTrainingPart(trainingPartBt.getText().toString());

        } catch (Exception e) {
            e.getStackTrace();
        }
        // リストにセット
        menuList.add(menuEntity);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            // 閉じるボタンを押された場合
            case android.R.id.home:
                finish();
                break;
            // saveボタンを押された場合
            case R.id.action_save:
                saveEntity();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("menuEntity", menuEntity);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;

            default:
        }
        return true;
    }






}