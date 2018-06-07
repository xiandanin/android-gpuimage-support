package com.dyhdyh.gpuimage.support.example.util;

import android.app.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {

    public static void copyAssetFile(Activity activity, String assetFileName, File newFile) {
        try {
            InputStream is = activity.getAssets().open(assetFileName);
            int byteread = 0;
            FileOutputStream fs = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            while ((byteread = is.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            fs.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
