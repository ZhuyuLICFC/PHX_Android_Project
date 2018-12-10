package com.example.zhuyu.phx_final.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;


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

    public static String getUserInfoFilePath() {
        return getExternalRootPath() + "/PHX/user/info.txt";

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

    public static boolean writeToFile(String path, String contentToWrite) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                Log.d(TAG, "write issue: file not exitst");
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(file.length());
            randomAccessFile.write(contentToWrite.getBytes());
            randomAccessFile.close();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "write issue: " + e.toString());
            return false;
        }
    }


    public static String readFromFile(String path) {
        String content = "";
        try {
            File file = new File(path);
            InputStream inputStream = new FileInputStream((file));
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content += line + "\n";
                }
                inputStream.close();
            }
        } catch (Exception e) {
            Log.d(TAG, "write issue: " + e.toString());
        }

        return content;
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
