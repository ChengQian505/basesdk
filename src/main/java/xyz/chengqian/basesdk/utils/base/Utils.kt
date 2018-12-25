package xyz.chengqian.basesdk.utils.base

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import xyz.cq.clog.CLog

@SuppressLint("StaticFieldLeak")
object Utils {

    var context: Context? = null
        get() {
            if (field == null) {
                throw IllegalArgumentException("context为空")
            } else {
                return field
            }
        }
        set(value) {
            field=value
            CLog.log().i("Utils init")
        }
    /**
     * 获取当前应用版本
     */
    fun getVersionCode() = try {
        val pm = context!!.packageManager
        val pi = pm.getPackageInfo(context!!.packageName, 0)
        pi.versionCode
    } catch (e: Exception) {
        CLog.log().d("Version", "versionName get error", e)
    }
    /**
     * 获取当前应用版本
     */
    fun getVersionName(): String {
        val pm = context!!.packageManager
        val pi = pm.getPackageInfo(context!!.packageName, 0)
        return pi.versionName.toString()
    }

    /**
     * 方法描述：判断某一应用是否正在运行
     * Created by cafeting on 2017/2/4.
     * @param context     上下文
     * @param packageName 应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    fun isAppRunning(context: Context, packageName:String):Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = am.getRunningTasks(100);
        if (list.size <= 0) {
            return false;
        }
        for (info in list) {
            if (info.baseActivity.packageName == packageName) {
                return true;
            }
        }
        return false;
    }

}
