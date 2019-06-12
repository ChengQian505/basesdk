package xyz.chengqian.basesdk.utils.base

import android.content.Context
import top.zibin.luban.OnCompressListener
import android.text.TextUtils
import top.zibin.luban.Luban
import java.io.File


/**
 * @author 程前 created on 2019/1/29.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote: 等比例缩放
 */
object UtilsPhotoCompress {
    fun compress(context: Context, path: String, onCompressListener: CompressListener) {
        Luban.with(context)
                .load(path)
                .ignoreBy(100)
                .setTargetDir(path.substring(0, path.lastIndexOf("/")))
                .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                .setCompressListener(object : OnCompressListener {
                    override fun onSuccess(file: File?) {
                        if (file != null && file.path != path) {
                            File(path).delete()
                        }
                        onCompressListener.onSuccess(file)
                    }

                    override fun onError(e: Throwable?) {
                        onCompressListener.onError(e)
                    }

                    override fun onStart() {
                        onCompressListener.onStart()
                    }

                }).launch()
    }

    interface CompressListener {
        fun onError(e: Throwable?)

        fun onStart()

        fun onSuccess(file: File?)
    }

}
