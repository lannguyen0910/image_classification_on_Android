package com.lannguyen.ma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nex3z.fingerpaintview.FingerPaintView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context context = MainActivity.this;
    private Classifier mnistClassifier;
    private Button btnClassify;
    private ImageButton btnClear;
    private FingerPaintView fingerPaintView;
    private TextView numberPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClassify = findViewById(R.id.classify);
        btnClear = findViewById(R.id.clear_text);
        fingerPaintView = findViewById(R.id.fpv_paint);
        numberPredict = findViewById(R.id.number_predict);

        init();

        configBottomNavigationView();
    }

    private void init() {
        initClassifier();
        initView();
    }

    private void initView() {
        btnClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fingerPaintView.isEmpty()){
                    Toast.makeText(MainActivity.this, R.string.please_write_a_digit, Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap bitmap = fingerPaintView.exportToBitmap(ModelConfig.INPUT_IMG_SIZE_WIDTH, ModelConfig.INPUT_IMG_SIZE_HEIGHT);
                List<Recognition> recognitions = mnistClassifier.recognizeImage(bitmap);
                Recognition value = recognitions.get(0);
                numberPredict.setText(value.toString());
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fingerPaintView.clear();
                numberPredict.setText(R.string.empty);
            }
        });

    }

    private void initClassifier() {
        try {
            mnistClassifier = Classifier.classifier(getAssets(), ModelConfig.MODEL_FILENAME);
        } catch (IOException e) {
            Toast.makeText(this, "MNIST model couldn't be loaded. Check logs for details.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void configBottomNavigationView(){
        Log.d(TAG, "Config Bottom Navigation View!");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewUtils.configBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewUtils.navigating(context,this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}