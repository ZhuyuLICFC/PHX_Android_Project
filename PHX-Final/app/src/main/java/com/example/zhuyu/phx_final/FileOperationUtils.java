package com.example.zhuyu.phx_final;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;



public class FileOperationUtils {

    private static final String TAG = "MainActivity";
    private static Context appContext;

    public static void setContext(Context context) {
        appContext = context;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static String getPodcastCacheValuesDirectory() {
        return getInternalFilePath() + "/audio/values/";
    }

    public static String getPodcastCacheImagesDirectory() {
        return getInternalFilePath() + "/audio/images/";
    }

    public static String getVideoCacheValuesDirectory() {
        return getInternalFilePath() + "/video/values/";
    }

    public static String getVideoCacheImagesDirectory() {
        return getInternalFilePath() + "/video/images/";

    }

    public static String getUserDirectory() {
        return getExternalRootPath() + "/PHX/user/";

    }

    public static String getAudioDirectory() {
        return getExternalRootPath() + "/PHX/audio/";
    }

    public static String getVideoDirectory() {
        return getExternalRootPath() + "/PHX/video/";
    }

    public static boolean checkExist(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean createFile(String path) {
        File file = new File(path);
        try{
            file.createNewFile();
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }


    public static boolean createDirectory(String path) {
        File file = new File(path);
        try{
            file.mkdirs();
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public static String getExternalRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getInternalFilePath() {
        return getAppContext().getFilesDir().getAbsolutePath();

    }

    //get cache for adding the database, this will be in the future
}
