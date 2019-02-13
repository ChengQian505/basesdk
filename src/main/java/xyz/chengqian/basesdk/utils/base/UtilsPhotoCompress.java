package xyz.chengqian.basesdk.utils.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 程前 created on 2019/1/29.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote: 等比例缩放
 */
public class UtilsPhotoCompress {

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     * @param size 图片在max*0.5-max*1.5字节之间
     */
    public static Bitmap smallBitmap(String filePath, long size) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        //这里*8是因为位图是bit，需要*8变成字节
        options.inSampleSize = calculateInSampleSize(options, size * 8);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     * @param size 图片在max*0.5-max*1.5字节之间
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, long size) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        return (int) (height * width / size + 0.5);
    }

    /**
     * 图片压缩
     * @param path 图片文件地址
     * @param size 图片文件在size*0.5-size*1.5 KB之间
     */
    public static void compress(String path,long size) throws IOException {
        File file=new File(path);
        if (!file.exists()) {
            return;
        }
        Bitmap bitmap=smallBitmap(path,size*1024);
        boolean d=file.delete();
        boolean c=file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 把图片文件转换成Base64String
     * @param filePath 图片文件地址
     * @param size 图片文件在size*0.5-size*1.5 KB之间
     */
    public static String file2Base64(String filePath,long size) {
        Bitmap bm = smallBitmap(filePath, size * 1024);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

}
