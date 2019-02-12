package xyz.chengqian.basesdk.utils.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author 程前 created on 2018/12/18.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
public class SystemUtils {

    public static DisplayMetrics getWindowDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }


    public static Bitmap generateImageFromView(View view, int width, int height) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, width, height);
        Bitmap image = Bitmap.createBitmap(view.getDrawingCache());
        view.destroyDrawingCache();

        return image;
    }


    /**
     * 将图片保存到系统相册
     *
     * @param context
     * @param bmp
     * @return
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {

        String galleryPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES;
        File galleryDir = new File(galleryPath);
        if (!galleryDir.exists()) {
            galleryDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(galleryPath, fileName);

        FileOutputStream fos = null;
        boolean isSuccess = false;

        try {
            fos = new FileOutputStream(file);
            isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return isSuccess;
    }
}
