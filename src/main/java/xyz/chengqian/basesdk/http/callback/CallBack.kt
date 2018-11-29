package xyz.chengqian.basesdk.http.callback


interface CallBack {

    companion object {
        const val ERROR_CODE="900991"
        const val ERROR_NETWORK_CODE="900992"
    }

    fun onSuccess(any: Any)

}