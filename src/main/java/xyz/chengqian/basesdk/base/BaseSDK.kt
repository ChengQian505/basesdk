package xyz.chengqian.basesdk.base

import android.content.Context
import xyz.chengqian.basesdk.utils.base.FileUtils
import xyz.chengqian.basesdk.utils.base.NetworkUtils
import xyz.chengqian.basesdk.utils.base.Utils
import xyz.cq.clog.CLog

/**
 * @author 程前 created on 2018/11/29.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
object BaseSDK {
    fun init(context: Context,isLog:Boolean) {
        //App异常崩溃处理器
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler())
        Utils.context = context
        FileUtils.context = context
        NetworkUtils.context = context
        CLog.INSTANCE.context(context).isLog(isLog).logFile(FileUtils.logPath())
    }
}