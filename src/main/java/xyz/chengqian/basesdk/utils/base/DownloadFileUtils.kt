package xyz.chengqian.basesdk.utils.base

import okhttp3.*
import xyz.cq.clog.CLog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * author 程前
 * 2018/3/20
 * 描述
 */
object DownloadFileUtils {

    private const val TAG = "DownloadFileUtils"

    private val okHttpClient = OkHttpClient()

    private var isDownLoads = HashMap<String, Boolean>()//避免重复下载，值为true正在下载，负责为未下载


    fun downloadFile(urlStr: String, pathName: String, callback: OnDownloadListener) {
        downloadFile("", urlStr, pathName, callback)
    }

    fun downloadFile(tag: String, urlStr: String, pathName: String, callback: OnDownloadListener) {
        if (isDownLoads[urlStr] == null) {
            isDownLoads[urlStr] = true
            download(tag, urlStr, pathName, object : OnDownloadListener {
                override fun onDownloadSuccess() {
                    isDownLoads[urlStr] = false
                    callback.onDownloadSuccess()
                }

                override fun onDownloading(progress: Float) {
                    callback.onDownloading(progress)
                }

                override fun onDownloadFailed() {
                    isDownLoads[urlStr] = false
                    callback.onDownloadFailed()
                }

            })
        }
    }


    /**
     * @param url 下载连接
     * @param saveDir 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    fun download(tag: String, url: String, filePath: String, listener: OnDownloadListener) {
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }
        val request = try {
            Request.Builder().url(url).build()
        } catch (e: IllegalArgumentException) {
            // 下载失败
            CLog.log(TAG).d("$tag-下载失败 $url ", e)
            file.delete()
            listener.onDownloadFailed()
            return
        }
        okHttpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                // 下载失败
                CLog.log(TAG).d("$tag-下载失败 $url ", e)
                file.delete()
                listener.onDownloadFailed()
            }

            override fun onResponse(call: Call, response: Response) {

                var inputStream: InputStream? = null
                var fileOutputStream: FileOutputStream? = null
                val buf = ByteArray(1024 * 100)
                var len = 0
                try {
                    inputStream = response.body()!!.byteStream()
                    val total = response.body()!!.contentLength()
                    fileOutputStream = FileOutputStream(file)
                    var sum = 0
                    var lastLog = 0
                    while (inputStream.read(buf).apply { len = this } > 0) {
                        fileOutputStream.write(buf, 0, len);
                        sum += len
                        val progress = (sum * 100F / total)
                        if (progress > lastLog) {
                            CLog.log(TAG).i("$tag-$filePath 下载${progress.toInt()}%")
                            lastLog = progress.toInt()
                        }
                        // 下载中
                        listener.onDownloading(progress)
                    }
                    fileOutputStream.flush()
                    CLog.log().i(DownloadFileUtils::class.java, "$tag-下载成功 $url \n 文件地址：$filePath")
                    // 下载完成
                    listener.onDownloadSuccess()
                } catch (e: Exception) {
                    CLog.log(TAG).d("$tag-下载失败 $url ", e)
                    file.delete()
                    listener.onDownloadFailed()
                } finally {
                    try {
                        if (inputStream != null)
                            inputStream.close()
                    } catch (e: IOException) {
                    }
                    try {
                        if (fileOutputStream != null)
                            fileOutputStream.close()
                    } catch (e: IOException) {
                    }
                }
            }
        });
    }

    interface OnDownloadListener {
        /**
         * 下载成功
         */
        fun onDownloadSuccess();

        /**
         * @param progress
         * 下载进度
         */
        fun onDownloading(progress: Float);

        /**
         * 下载失败
         */
        fun onDownloadFailed();
    }

}