package xyz.chengqian.basesdk.showinfo


/**
 * author 程前
 * 2018/2/27
 * 描述 日志打印基类2.0
 */
open class Log1 : ILog {

    private val dealtReturn = 0

    companion object {
        var isLog = true
        const val DEBUG = "CLOG/"
        const val DEFULAT = "DEFAULT"
    }

    override fun d(tag: String, msg: String): Int {
        return if (isLog)
            android.util.Log.d(DEBUG + tag, msg)
        else
            dealtReturn
    }

    override fun d(tag: String, msg: String, t: Throwable): Int {
        return if (isLog)
            android.util.Log.d(DEBUG + tag, msg, t)
        else
            dealtReturn
    }

    override fun e(tag: String, msg: String): Int {
        return if (isLog)
            android.util.Log.e(DEBUG + tag, msg)
        else
            dealtReturn
    }

    override fun e(tag: String, msg: String, t: Throwable): Int {
        return if (isLog)
            android.util.Log.e(DEBUG + tag, msg, t)
        else
            dealtReturn
    }

    override fun w(tag: String, msg: String): Int {
        return if (isLog)
            android.util.Log.w(DEBUG + tag, msg)
        else
            dealtReturn
    }

    override fun w(tag: String, msg: String, t: Throwable): Int {
        return if (isLog)
            android.util.Log.w(DEBUG + tag, msg, t)
        else
            dealtReturn
    }

    override fun i(tag: String, msg: String): Int {
        return if (isLog)
            android.util.Log.i(DEBUG + tag, msg)
        else
            dealtReturn
    }

    override fun i(tag: String, msg: String, t: Throwable): Int {
        return if (isLog)
            android.util.Log.i(DEBUG + tag, msg, t)
        else
            dealtReturn
    }

    fun d(cls: Class<*>, tag: String, tr: Throwable, vararg msg: String): Int {
        return d(tag, byteToString(msg) + getCodeLocation(cls.name), tr)
    }

    fun d(cls: Class<*>, tag: String, vararg msg: String): Int {
        return d(tag, byteToString(msg) + getCodeLocation(cls.name))
    }

    fun i(cls: Class<*>, tag: String, tr: Throwable, vararg msg: String): Int {
        return i(tag, byteToString(msg) + getCodeLocation(cls.name), tr)
    }

    fun i(cls: Class<*>, tag: String, vararg msg: String): Int {
        return i(tag, byteToString(msg) + getCodeLocation(cls.name))
    }

    fun i(cls: Class<*>, tag1: String, tag2: String, vararg msg: String): Int {
        return i(tag1, tag2 + ": " + byteToString(msg) + getCodeLocation(cls.name))
    }


    fun e(cls: Class<*>, tag: String, tr: Throwable, vararg msg: String): Int {
        return e(tag, byteToString(msg) + getCodeLocation(cls.name), tr)
    }

    fun e(cls: Class<*>, tag1: String, tag2: String, tr: Throwable, vararg msg: String): Int {
        return e(tag1, tag2 + ": " + byteToString(msg) + getCodeLocation(cls.name), tr)
    }

    fun e(cls: Class<*>, tag: String, vararg msg: String): Int {
        return e(tag, byteToString(msg) + getCodeLocation(cls.name))
    }

    /**
     * 单字符串数组的拼接
     */
    private fun byteToString(msg: Array<out String>): String {
        val sb = StringBuffer()
        return if (msg.isNotEmpty()) {
            for (s in msg) {
                if ("n" == s) {
                    sb.append(System.getProperty("line.separator"))
                } else {
                    sb.append("$s  ")
                }
            }
            sb.toString()
        } else
            ""
    }

    /**
     * 代码定位行数
     */
    private fun getCodeLocation(tag: String): String {
        if (getTargetStack(tag) == null) {
            return ""
        }
        return "(" + getTargetStack(tag)!!.fileName + ":" + getTargetStack(tag)!!.lineNumber + ")"
    }


    /**
     * 获取最后调用我们log的StackTraceElement
     */
    private fun getTargetStack(tag: String): StackTraceElement? {
        // 获取不到返回 null
        // 用于存储 目标类 中所有调用的方法
        for (element in Thread.currentThread().stackTrace) {

            // 只获取目标类中的 element
            if (element.className.contains(tag)) {
                return element
            }
        }
        // 获取不到返回 null
        return null
    }


}