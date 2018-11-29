package xyz.chengqian.basesdk.showinfo

/**
 * qian.cheng create on 2017/12/15.
 * content :
 */

class Log2 internal constructor(TAG: String?) : Log1() {

    private var TAG: String = DEFULAT

    init {
        if (TAG != null)
            this.TAG = TAG
        else
            this.TAG= DEFULAT
    }

    /**
     * CQLOG/DEFULAT
     * @param logmsg 打印信息
     */
    fun i(logmsg: String) {
        i(TAG, logmsg)
    }

    /**
     * CQLOG/DEFULAT
     * @param logmsg 打印信息
     * @param throwable 异常
     */
    fun i(logmsg: String, throwable: Throwable) {
        i(TAG, logmsg, throwable)
    }

    /**
     * CQLOG/DEFULAT
     * @param cls 打印所在类
     * @param logmsg 打印信息
     */
    fun i(cls: Class<*>, logmsg: String) {
        i(TAG, logmsg + getCodeLocation(cls.name))
    }


    /**
     * @param cls 打印所在类
     * @param tag 打印tag
     * @param logmsg 打印信息
     */
    fun i(cls: Class<*>?, tag: String, logmsg: String) {
        i(tag, logmsg + getCodeLocation(cls!!.name))
    }

    /**
     * @param logmsg 打印信息
     */
    fun w(logmsg: String) {
        w(TAG, logmsg)
    }

    /**
     * @param logmsg 打印信息
     */
    fun w(logmsg: String, throwable: Throwable) {
        w(TAG, logmsg, throwable)
    }

    /**
     * @param logmsg 打印信息
     */
    fun d(logmsg: String) {
        d(TAG, logmsg)
    }

    fun d(t: Throwable) {
        d(TAG, "", t)
    }

    /**
     * @param logmsg 打印信息
     */
    fun d(logmsg: String, t: Throwable) {
        d(TAG, logmsg, t)
    }

    /**
     * CQLOG/DEFULAT
     * @param cls 打印所在类
     * @param logmsg 打印信息
     */
    fun e(cls: Class<*>, logmsg: String) {
        e(TAG, logmsg + getCodeLocation(cls.name))
    }

    /**
     * CQLOG/DEFULAT
     * @param cls 打印所在类
     * @param t 错误信息
     */
    fun e(cls: Class<*>, t: Throwable) {
        e(TAG, getCodeLocation(cls.name), t)
    }

    /**
     * @param logmsg 打印信息
     */
    fun e(logmsg: String) {
        e(TAG, logmsg)
    }

    /**
     */
    fun e(t: Throwable) {
        e(TAG, "", t)
    }

    /**
     * @param tag 打印tag
     * @param logmsg 打印信息
     */
    fun e(cls: Class<*>, tag: String, logmsg: String, tr: Throwable) {
        e(tag, logmsg + getCodeLocation(cls.name), tr)
    }

    /**
     * @param logmsg 打印信息
     */
    fun e(cls: Class<*>, logmsg: String, tr: Throwable) {
        e(TAG, logmsg + getCodeLocation(cls.name), tr)
    }

    /**
     * @param logmsg 打印信息
     */
    fun e(logmsg: String, tr: Throwable) {
        e(TAG, logmsg, tr)
    }

    /**
     * @param cls 打印所在类
     * @param tag 打印tag
     * @param logmsg 打印信息
     */
    fun e(cls: Class<*>, tag: String, logmsg: String) {
        e(tag, logmsg + getCodeLocation(cls.name))
    }


    /**
     * 获取行数信息
     * @param tag 类名
     * @return 行数信息
     */
    private fun getCodeLocation(tag: String): String {
        val targetStack = getTargetStack(tag)
        try {
            return "(" + targetStack!!.fileName + ":" + targetStack.lineNumber + ")"
        } catch (e: NullPointerException) {
            return tag
        }

    }

    // 获取最后调用我们log的StackTraceElement
    private fun getTargetStack(tag: String): StackTraceElement? {
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
