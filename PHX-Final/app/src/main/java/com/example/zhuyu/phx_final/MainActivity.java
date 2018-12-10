package com.example.zhuyu.phx_final;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhuyu.phx_final.utils.FileOperationUtils;

import java.io.File;

public class MainActivity extends FragmentActivity implements PermissionInterface{

    private static final int REQ_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQ_CODE_WRITE_EXTERNAL_STORAGE = 2;
    private static final int REQ_CODE_ACCESS_INTERNET = 3;

    private static final String TAG = "MainActivity";
    private PermissionUtils permissionUtils;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        FileOperationUtils.setContext(getApplicationContext());
        getPermission();
        fm = getSupportFragmentManager();
        Fragment fragment;

        if (FileOperationUtils.checkExist(FileOperationUtils.getUserInfoFilePath())) {
            fragment = new MainFragment();
            fm.beginTransaction().add(R.id.main_container, fragment).commit();
        } else {
            Log.d(TAG, FileOperationUtils.getUserInfoFilePath());
            fragment = new RegisterFormFragment();
            fm.beginTransaction().add(R.id.main_container, fragment).commit();
        }
    }

    public void goToMain() {
        fm.beginTransaction().replace(R.id.main_container, new MainFragment()).commit();
    }

    public void getPermission() {
        permissionUtils = new PermissionUtils(this, this);
        permissionUtils.requestPremissions(Manifest.permission.READ_EXTERNAL_STORAGE, REQ_CODE_READ_EXTERNAL_STORAGE);
        permissionUtils.requestPremissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_CODE_WRITE_EXTERNAL_STORAGE);
        permissionUtils.requestPremissions(Manifest.permission.INTERNET, REQ_CODE_ACCESS_INTERNET);
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
