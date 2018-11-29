package xyz.chengqian.basesdk.showinfo

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

@SuppressLint("StaticFieldLeak")
/**
 * qian.cheng create on 2017/12/15.
 * content :
 */

object CToast {

    private var toast: Toast? = null

    var context: Context? = null


    /**
     * 底部短时间Toast
     * @param text 吐司内容
     */
    private fun show1(text: String) {
        if (context == null) {
            throw IllegalArgumentException("context为空")
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        } else {
            toast?.setText(text)
        }
        toast?.show()
    }
    /**
     * 底部长时间Toast
     * @param text 吐司内容
     */
    private fun show1long(text: String) {
        if (context == null) {
            throw IllegalArgumentException("context为空")
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        } else {
            toast?.setText(text)
        }
        toast?.show()
    }



    fun show(text: String) {
        if (context == null) {
            throw IllegalArgumentException("context为空")
        }
        show1(text)
        CLog.log("TOAST").i("SHORT-$text")
    }
    fun showLong(text: String) {
        if (context == null) {
            throw IllegalArgumentException("context为空")
        }
        showLong(text)
        CLog.log("TOAST").i("LONG-$text")
    }

    fun show(text1: String, text2: String) {
        if (context == null) {
            throw IllegalArgumentException("context为空")
        }
        show1(text1)
        CLog.log("TOAST").i("$text1 $text2")
    }

    /**
     * 设置时间长短
     * @param duration
     * @return1 当前对象
     */
    fun setDuration(duration: Int): CToast {
        toast!!.duration = duration
        return this
    }

}
