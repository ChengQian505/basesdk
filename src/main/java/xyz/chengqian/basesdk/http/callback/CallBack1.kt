package xyz.chengqian.basesdk.http.callback

interface CallBack1: CallBack {
    fun onError(errorCode:String,e: Throwable?)

}