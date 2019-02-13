package xyz.chengqian.basesdk.utils.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import xyz.chengqian.basesdk.BuildConfig
import xyz.cq.clog.CLog
import java.io.File
import java.io.FileInputStream
import java.text.DecimalFormat

/**
 * content：
 * @author 程前
 * @createDate: 2018/6/15
 * @blog: https://blog.csdn.net/ch1406285246
 * modifyNote:
 */
@SuppressLint("StaticFieldLeak")
object UtilsFile {
    var context: Context? = null
        set(value) {
            field = value
            CLog.log().i("UtilsFile init")
        }

    private fun fileDir(): String {
        if (context == null) {
            throw IllegalArgumentException("context为空")
        } else {
            return context!!.filesDir.absolutePath
        }
    }

    /**
     * 获取SD卡路径
     */
    fun getSDCardPath(path: String): String {
        val file=File(getSDCardPath() + "/" + path)
        if (!file.exists()){
            file.mkdirs()
        }
        return file.absolutePath
    }

    private fun getSDCardPath(): String {
        return context!!.externalCacheDir.absolutePath
    }

    fun getApkPath(apkName:String): String {
        val path = File(getPath("setting") +"/$apkName")
        if (!path.exists()) {
            path.createNewFile()
        }
        return path.absolutePath
    }

    public fun logPath():String{
        val path = File(getSDCardPath("log")+"/cqlog.txt")
        if (!path.exists()) {
            path.createNewFile()
        }
        return path.absolutePath
    }

    private fun filePath() = if (BuildConfig.DEBUG) getSDCardPath() else fileDir()
    private val settingPath: String
        get() {
            return getPath("setting")
        }
    private val imagePath: String
        get() {
            return getPath("image")
        }

    private fun getPath(childPath: String): String {
        val path = File("${filePath()}/$childPath")
        if (!path.exists()) {
            path.mkdirs()
        }
        return path.absolutePath
    }

    /**
     * 增加需要在xml文件中配置
     */
    fun getSettingPath(path: String): String {
        val path1 = File("$settingPath/$path")
        if (!path1.exists()) {
            path1.createNewFile()
        }
        return path1.absolutePath
    }

    fun getImagePath(path: String): String {
        val path1 = File("${getSDCardPath("Image")}/$path")
        if (!path1.exists()) {
            path1.createNewFile()
        }
        return path1.absolutePath
    }

    const val SIZETYPE_B = 1//获取文件大小单位为B的double值
    const val SIZETYPE_KB = 2//获取文件大小单位为KB的double值
    const val SIZETYPE_MB = 3//获取文件大小单位为MB的double值
    const val SIZETYPE_GB = 4//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    fun getFileOrFilesSize(filePath: String, sizeType: Int): Double {
        val file = File(filePath)
        var blockSize: Long = 0
        try {
            if (file.isDirectory) {
                blockSize = getFileSizes(file)
            } else {
                blockSize = getFileSize(file)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return formetFileSize(blockSize, sizeType)
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    fun getAutoFileOrFilesSize(filePath: String): String {
        val file = File(filePath)
        var blockSize: Long = 0
        try {
            if (file.isDirectory) {
                blockSize = getFileSizes(file)
            } else {
                blockSize = getFileSize(file)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return formetFileSize(blockSize)
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun getFileSize(file: File): Long {
        var size: Long = 0
        if (file.exists()) {
            val fis = FileInputStream(file)
            size = fis.available().toLong()
        } else {
            file.createNewFile()
        }
        return size
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun getFileSizes(f: File): Long {
        var size: Long = 0
        val flist = f.listFiles()
        for (i in flist!!.indices) {
            if (flist[i].isDirectory) {
                size += getFileSizes(flist[i])
            } else {
                size += getFileSize(flist[i])
            }
        }
        return size
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private fun formetFileSize(fileS: Long): String {
        val df = DecimalFormat("0.00")
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        return if (fileS < 1024) {
            df.format(fileS.toDouble()) + "B"
        } else if (fileS < 1048576) {
            df.format(fileS.toDouble() / 1024) + "KB"
        } else if (fileS < 1073741824) {
            df.format(fileS.toDouble() / 1048576) + "MB"
        } else {
            df.format(fileS.toDouble() / 1073741824) + "GB"
        }
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private fun formetFileSize(fileS: Long, sizeType: Int): Double {
        val df = DecimalFormat("0.00")
        var fileSizeLong = 0.0
        when (sizeType) {
            SIZETYPE_B -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble()))
            SIZETYPE_KB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1024))
            SIZETYPE_MB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1048576))
            SIZETYPE_GB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1073741824))
            else -> {
            }
        }
        return fileSizeLong
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file
     *            要删除的根目录
     */
    fun deleteFile(file: File) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory) {
            val childFile = file.listFiles();
            if (childFile == null || childFile.isEmpty()) {
                file.delete();
                return;
            }
            for (f in childFile) {
                deleteFile(f);
            }
            file.delete();
        }
    }


}