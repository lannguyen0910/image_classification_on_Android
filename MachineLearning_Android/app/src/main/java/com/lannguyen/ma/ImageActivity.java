package com.lannguyen.ma;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.IOException;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG = "ImageActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context context = ImageActivity.this;
    private Classifier mnistClassifier;
    private ImageView image;
    private Button btnClassify;
    private ImageButton btnClear;
    private TextView numberPredict;
    private Bitmap bitmap;
    Uri imageuri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        btnClassify = findViewById(R.id.classify);
        btnClear = findViewById(R.id.clear_text);
        numberPredict = findViewById(R.id.number_predict);
        image = findViewById(R.id.image);

        init();

        configBottomNavigationView();
    }

    private void init() {
        initClassifier();
        openImage();
        predict();
        clear();
    }

    private void initClassifier() {
        try {
            mnistClassifier = Classifier.classifier(getAssets(), ModelConfig.MODEL_FILENAME);
        } catch (IOException e) {
            Toast.makeText(this, "MNIST model couldn't be loaded. Check logs for details.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openImage(){
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),12);
            }
        });
    }

    private void predict(){
        btnClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap preprocessedImage = ImageUtils.prepareImageForClassification(bitmap);
                List<Recognition> recognitions = mnistClassifier.recognizeImage(preprocessedImage);
                Recognition value = recognitions.get(0);
                numberPredict.setText(value.toString());
            }
        });
    }

    private void clear(){
        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                image.setImageResource(0);
                numberPredict.setText(R.string.empty);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK && data!=null) {
            imageuri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
