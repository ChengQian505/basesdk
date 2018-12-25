package xyz.chengqian.basesdk.http.callback

/**
 * @author 程前 created on 2018/12/5.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
interface CallBack {
    companion object {
        const val ERROR_CODE="900991"
        const val ERROR_NETWORK_CODE="900992"
    }

    fun onSuccess(any: Any)
}