package com.example.zhuyu.phx_final;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PermissionUtils {

    private Activity mActivity;
    private PermissionInterface mPermissionInterface;
    private String permision;
    private int callBackCode;
    private static final String TAG = "MainActivity";

    public PermissionUtils(@NonNull Activity activity, @NonNull PermissionInterface permissionInterface) {
        mActivity = activity;
        mPermissionInterface = permissionInterface;
    }

    public static boolean hasPermission(Context context,String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void requestPremissions(String permission, int callBackCode) {
        this.permision = permission;
        this.callBackCode = callBackCode;
        if (hasPermission(mActivity, permission)) {
            mPermissionInterface.requestPermissionsSuccess(callBackCode);
        } else {
            ActivityCompat.requestPermissions(mActivity, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, callBackCode);
        }
    }

    public void requestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == callBackCode) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    mPermissionInterface.requestPermissionsSuccess(callBackCode);
                } else {
                    mPermissionInterface.requestPermissionsFail(callBackCode);
                }
            }
        }
    }
}
