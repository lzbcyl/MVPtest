package lzb.com.mvptest.presenter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.TextView;

import java.io.File;


/**
 * Created by lzb on 2016/7/13.
 */
public class SystemActivity {


    /**
     * 从图库中选择图片
     */
    public static void FromPhoto(Activity activity, int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, requestCode);
    }

    /**
     * 从系统中选择视频
     *
     * @param activity
     * @param requestCode
     */
    public static void FromGetVideo(Activity activity, int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Video.Media.INTERNAL_CONTENT_URI);
        i.setType("video/*");
        activity.startActivityForResult(i, requestCode);
    }


    /**
     * 获取视频文件 路径以及名称
     *
     * @param context
     */
    public static void getAllVieo(Context context, TextView v) {
        ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA};
        Cursor cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null,
                null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();
        for (int counter = 0; counter < fileNum; counter++) {

            v.append(cursor.getString(cursor
                    .getColumnIndex(MediaStore.Video.Media.TITLE))
                    + "\n"
                    + cursor.getString(cursor
                    .getColumnIndex(MediaStore.Video.Media.DATA))
                    + "\n\n-----");
            cursor.moveToNext();
        }
        cursor.close();
    }

    /**
     * 获取所有的音频文件
     *
     * @param context
     */
    public static void getAllAudio(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int counter = cursor.getCount();
        String title = cursor.getString(cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

        for (int j = 0; j < counter; j++) {
            cursor.moveToNext();
        }
        cursor.close();
    }
}
