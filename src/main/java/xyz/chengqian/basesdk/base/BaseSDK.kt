package xyz.chengqian.basesdk.base

import android.content.Context
import xyz.chengqian.basesdk.BuildConfig
import xyz.chengqian.basesdk.utils.base.UtilsFile
import xyz.chengqian.basesdk.utils.base.UtilsNetwork
import xyz.chengqian.basesdk.utils.base.Utils
import xyz.cq.clog.CLog
import xyz.cq.clog.log.MLog

/**
 * @author 程前 created on 2018/11/29.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
object BaseSDK {

    fun init(context: Context): BaseSDK {
        UtilsFile.context = context
        CLog.INSTANCE.context(context).isLog(BuildConfig.DEBUG).logFile(UtilsFile.logPath())
        //App异常崩溃处理器
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler())
        Utils.context = context
        UtilsNetwork.context = context
        return this
    }


    fun baseLog(baseLog: String): BaseSDK {
        CLog.INSTANCE.baseLog(baseLog)
        return this
    }

    fun baseLog() = MLog.BASE_TAG

}