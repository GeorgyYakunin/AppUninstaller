package com.app.uninstaller.appuninstaller212.utils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

public class Memory {

    public static float getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blockSize = stat.getBlockSize();
        float availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    public static String formatSize(float size) {
        String suffix = null;
//        if (size >= 1024) {
//            suffix = "";
//            //suffix = "";
//            size /= 1024;
//            if (size >= 1024) {
//                suffix = "";
//                size /= 1024;
//            }
//            if (size >= 1024) {
//                suffix = "";
//                size /= 1024;
//            }
//        }

        if (size >= 1024) {
            suffix = "";
            //suffix = "";
            size /= (1024*1024*1024);
        }

        StringBuilder resultBuffer = new StringBuilder(Float.toString(size));

//        Log.d("KiemTraSize", resultBuffer + "");
//        int commaOffset = resultBuffer.length() - 3;
//        while (commaOffset > 0) {
//            resultBuffer.insert(commaOffset, '.');
//            commaOffset -= 2;
//        }
//        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
}