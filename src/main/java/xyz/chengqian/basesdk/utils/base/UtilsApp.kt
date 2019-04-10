package xyz.chengqian.basesdk.utils.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import java.lang.reflect.InvocationTargetException

/**
 * @author 程前 created on 2019/3/22.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
object UtilsApp {

    @SuppressLint("StaticFieldLeak")
    private var sApplication: Application? = null

    /**
     * Init utils.
     *
     * Init it in the class of Application.
     *
     * @param context context
     */
    fun init(context: Context) {
        init(context.applicationContext as Application)
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }


    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    fun getApp(): Application {
        try {
            @SuppressLint("PrivateApi")
            val activityThread = Class.forName("android.app.ActivityThread")
            val at = activityThread.getMethod("currentActivityThread").invoke(null)
            val app = activityThread.getMethod("getApplication").invoke(at)
            if (app == null) {
                throw NullPointerException("u should init first")
            } else {
                return app as Application
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        throw NullPointerException("u should init first")
    }

    fun launchApp(packageName: String) {
        if (isSpace(packageName)) return
        getApp().startActivity(getLaunchAppIntent(packageName, true))
    }

    fun getLaunchAppIntent(packageName: String, isNewTask: Boolean): Intent? {
        val intent = getApp().getPackageManager()?.getLaunchIntentForPackage(packageName)
                ?: return null
        return getIntent(intent, isNewTask)
    }

    private fun getIntent(intent: Intent, isNewTask: Boolean): Intent {
        return if (isNewTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) else intent
    }


}
