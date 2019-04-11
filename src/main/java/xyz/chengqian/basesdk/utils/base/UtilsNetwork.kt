package xyz.chengqian.basesdk.utils.base

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import xyz.cq.clog.CLog

/**
 * 网络连接Utils
 * author:qian.cheng
 */
@SuppressLint("StaticFieldLeak")
object UtilsNetwork {

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
            CLog.log().i("UtilsNetwork init")
        }

    private const val NETWORK_OK = 0//网络正常
    private const val NETWORK_BAD = 1//网络不可用
    private const val NETWORK_UNCONNECTED = 2//网络未连接

    private const val NETWORK_WIFI = 10
    private const val NETWORK_MOBILE = 11
    private const val NETWORK_UNKNOW = 12

    /**
     * 获取网络状态
     * @return
     */
    private val netWorkState: Int
        get() {
            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return if (networkInfo != null && networkInfo.isConnected && networkInfo.isAvailable&&isNetSystemUsable(context!!))
                NETWORK_OK
            else if (networkInfo != null && !networkInfo.isConnected)
                NETWORK_UNCONNECTED
            else
                NETWORK_BAD
        }


    /**
     * 获取网络类型
     * @return
     */
    val netWorkType: Int
        get() {
            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI)
                NETWORK_WIFI
            else if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE)
                NETWORK_MOBILE
            else
                NETWORK_UNKNOW
        }

    /**
     * 检查网络是否可用
     * @return true可用 false不可用
     */
    fun checkNetWork(): Boolean {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected && networkInfo.isAvailable
    }

    /**
     * 检查网络无网络进行toast
     * @return true为网络正常,false为网络不正常
     */
    fun netWorkToast(context: Context): Boolean {
        return when (netWorkState) {
            NETWORK_OK -> true
            NETWORK_UNCONNECTED -> {
                CLog.show(context,action_network_error_toast)
                false
            }
            NETWORK_BAD -> {
                CLog.show(context,action_network_error_toast)
                false
            }
            else -> {
                CLog.show(context,action_network_error_toast)
                false
            }
        }
    }

    /**
     * 判断当前网络是否可用(6.0以上版本)
     * 实时
     * @param context
     * @return
     */
    fun isNetSystemUsable(context:Context):Boolean{
        var isNetUsable = false
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val networkCapabilities =manager.getNetworkCapabilities(manager.activeNetwork);
            isNetUsable = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        }
        return isNetUsable;
    }

    /**
     * 检查网络无网络进行toast
     * @return true为网络正常,false为网络不正常
     */
    fun netWorkToast(): Boolean {
        return when (netWorkState) {
            NETWORK_OK -> true
            NETWORK_UNCONNECTED -> {
                try {
                    CLog.show(action_network_error_toast)
                }catch (e:IllegalArgumentException){
                    throw java.lang.IllegalArgumentException("请先调用BaseSDK.init(context:Context)方法或使用netWorkToast(context:Context)方法")
                }
                false
            }
            NETWORK_BAD -> {
                try {
                    CLog.show(action_network_error_toast)
                }catch (e:IllegalArgumentException){
                    throw java.lang.IllegalArgumentException("请先调用BaseSDK.init(context:Context)方法或使用netWorkToast(context:Context)方法")
                }
                false
            }
            else -> {
                try {
                    CLog.show(action_network_error_toast)
                }catch (e:IllegalArgumentException){
                    throw java.lang.IllegalArgumentException("请先调用BaseSDK.init(context:Context)方法或使用netWorkToast(context:Context)方法")
                }
                false
            }
        }
    }

    private const val action_network_error_toast="网络异常,请检查网络连接"

}
