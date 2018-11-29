package xyz.chengqian.basesdk.showinfo;

/**
 * author 程前
 * 2018/2/27
 * 描述 日志打印接口2.0
 */
interface ILog {

    fun i(tag:String,msg:String): Int

    fun i(tag: String, msg: String, t: Throwable): Int

    fun d(tag:String,msg:String): Int

    fun d(tag:String,msg:String,t:Throwable): Int

    fun e(tag:String,msg:String): Int

    fun e(tag:String,msg:String,t:Throwable): Int

    fun w(tag: String, msg: String): Int

    fun w(tag: String, msg: String, t: Throwable): Int
}