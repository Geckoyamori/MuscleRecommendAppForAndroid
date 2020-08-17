package com.example.muscleapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.muscleapplication.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int addButtonCount;
    private float moveY;
    List<String> idData = new ArrayList<>();
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // intentの返却値を受け取るメソッド
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // RESULT_OKをセットしていない遷移は弾く
        if (Activity.RESULT_OK == resultCode) {
            // リクエストコードにより判別
            switch (requestCode) {
                // AddActivityからの返却
                case 1:
                    // intentからエンティティを取り出す
                    MenuEntity menuEntity = (MenuEntity) intent.getSerializableExtra("menuEntity");
                    createButton(menuEntity);
                    break;
                default:
                    break;
            }
        }

    }

    // ボタンを作成するメソッド
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createButton(final MenuEntity menuEntity) {
        frameLayout = findViewById(R.id.frame_layout);
        Button button = new Button(this);
        button.setId(ViewGroup.generateViewId());
        idData.add(String.valueOf(button.getId()));
        button.setText(menuEntity.getTrainingName());
        // ボタンにセットオンクリックリスナーを持たせる
        // この時intentとしてもつEntityは、メニュー追加された時のEntity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                intent.putExtra("menuEntity", menuEntity);
                startActivityForResult(intent, 2);
            }
        });

        // フレームレイアウトにボタンを加える
        frameLayout.addView(button, 0,
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        // ボタンを下に移動させる
        for (int i = 0 ; i < addButtonCount ; i++) {
            Button preButton = findViewById(Integer.parseInt(idData.get(i)));
            moveY = preButton.getY();
            moveY += 120;
            ObjectAnimator objectAnimator =
                    ObjectAnimator.ofFloat(preButton, "translationY", moveY);
            objectAnimator.start();
        }
        addButtonCount++;
    }



    // オプションメニューを作成する
    public boolean onCreateOptionsMenu(Menu menu){
        // menuにcustom_menuレイアウトを適用
        getMenuInflater().inflate(R.menu.custom_menu_add, menu);
        // オプションメニューを表示する場合はtrue
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(this, AddActivity.class);
                // リクエストコードとともにintentを渡すメソッド
                startActivityForResult(intent, 1);
                break;
            default:
        }
        return true;
    }

    // コミットの確認１
    // koredeiinoka
}