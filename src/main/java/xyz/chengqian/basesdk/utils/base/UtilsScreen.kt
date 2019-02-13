package xyz.chengqian.basesdk.utils.base

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import xyz.cq.clog.CLog


/**
 * @author 程前 created on 2018/12/18.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
object UtilsScreen {
    fun property(context: Context) {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels         // 屏幕宽度（像素）
        val height = dm.heightPixels       // 屏幕高度（像素）
        val density = dm.density         // 屏幕密度（0.75 / 1.0 / 1.5）
        val densityDpi = dm.densityDpi     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        val screenWidth = (width / density).toInt()  // 屏幕宽度(dp)
        val screenHeight = (height / density).toInt()// 屏幕高度(dp)
        CLog.log().d("h_bl", "屏幕宽度（像素）：$width")
        CLog.log().d("h_bl", "屏幕高度（像素）：$height")
        CLog.log().d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：$density")
        CLog.log().d("h_bl", "屏幕密度dpi（120 / 160 / 240）：$densityDpi")
        CLog.log().d("h_bl", "屏幕宽度（dp）：$screenWidth")
        CLog.log().d("h_bl", "屏幕高度（dp）：$screenHeight")
    }

    fun screenHeightDp(context: Context): Float {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels / dm.density
    }
    fun screenWidthDp(context: Context): Float {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels / dm.density
    }
    fun screenHeightPx(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }
    fun screenWidthPx(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    fun dp2px(dpV:Float,context: Context)= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpV, context.resources.displayMetrics).toInt()


}