package com.hooli.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * [github]:https://github.com/nanchen2251/AiYaCompressHelper
 */
public class ImageUtil {

    public static File compressImgFile(File oldfile, Activity activity) throws FileNotFoundException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(oldfile.getAbsolutePath(),options);
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        File newFile = null;

        if(oldfile.length() >= 4*1024*1024){
            newFile = new CompressHelper.Builder(activity)
                    .setMaxWidth(imgWidth)  // 默认最大宽度为720
                    .setMaxHeight(imgHeight) // 默认最大高度为960
                    .setQuality(70)    // 默认压缩质量为80
                    .setFileName(oldfile.getName()) // 设置你需要修改的文件名
                    .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                    .setDestinationDirectoryPath(oldfile.getParent())
                    .build()
                    .compressToFile(oldfile);
            newFile.renameTo(oldfile);
        }else{
            // newFile = CompressHelper.getDefault(activity).compressToFile(oldfile);
            //Log.i("Compress","start press");
            newFile = new CompressHelper.Builder(activity)
                    .setMaxWidth(imgWidth)  // 默认最大宽度为720
                    .setMaxHeight(imgHeight) // 默认最大高度为960
                    .setQuality(80)    // 默认压缩质量为80
                    .setFileName(oldfile.getName()) // 设置你需要修改的文件名
                    .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                    .setDestinationDirectoryPath(oldfile.getParent())
                    .build()
                    .compressToFile(oldfile);
            newFile.renameTo(oldfile);
            //Log.i("Compress","finish press");
        }
        return newFile;
    }
}
