package com.example.zhuyu.phx_final;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class LaunchActivity extends AppCompatActivity implements PermissionInterface{

    private static final int REQ_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQ_CODE_WRITE_EXTERNAL_STORAGE = 2;
    private boolean isFirstTime = true;
    private String userInfoPath;
    private static final String TAG = "LaunchActivity";
    private PermissionUtils permissionUtils;
    private Button mSubmitButton;
    private TextView mShowTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        permissionUtils = new PermissionUtils(this, this);
        permissionUtils.requestPremissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_CODE_WRITE_EXTERNAL_STORAGE);
        getPermission();
        userInfoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "PHX" + File.separator + "User" + File.separator;

        mShowTextView = findViewById(R.id.name_text_view);
        mSubmitButton = findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRootDirectory(userInfoPath);
                Intent i = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        if (checkFileExist(userInfoPath)) {
            mShowTextView.setText("OK");
            Intent i = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    public void getPermission() {
        permissionUtils.requestPremissions(Manifest.permission.READ_EXTERNAL_STORAGE, REQ_CODE_READ_EXTERNAL_STORAGE);
        permissionUtils.requestPremissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_CODE_WRITE_EXTERNAL_STORAGE);
    }

    public boolean checkFileExist(String path) {
        File file = new File(path);
        return(file.exists());
    }

    public void makeFile(String path) {
        makeRootDirectory(path);
        Log.d(TAG, path);
        File file;
        try{
            file = new File(path);
            if (file.exists()) {
                boolean success;
                success = file.createNewFile();
                Log.d(TAG, success + "");
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void makeRootDirectory(String path) {
        File file;
        try {
            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionUtils.requestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void requestPermissionsSuccess(int callBackCode) {
    }

    @Override
    public void requestPermissionsFail(int callBackCode) {
    }
}
