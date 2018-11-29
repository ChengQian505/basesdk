package xyz.chengqian.basesdk.showinfo

import java.util.*

/**
 * qian.cheng create on 2017/12/15.
 * content :
 */

object CLog {

    private val tags = WeakHashMap<String, Log2>()

    fun log(): Log2 {
        return log(null)
    }

    fun log(TAG: String?): Log2 {
        return if (tags.contains(TAG)){
            tags[TAG]!!
        }else{
            tags[TAG] = Log2(TAG)
            log(TAG)
        }
    }

    fun logThread()= log(THREAD)
    fun logView()= log(VIEW)
    fun logStorage()= log(STORAGE)

    private const val THREAD = "THREAD"
    const val VIEW = "VIEW"
    const val STORAGE = "STORAGE"
    const val REQUEST = Log1.DEBUG +"REQUEST"
    const val RESPONSE = Log1.DEBUG +"RESPONSE"

}
