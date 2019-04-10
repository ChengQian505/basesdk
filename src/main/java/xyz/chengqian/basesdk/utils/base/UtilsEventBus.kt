package xyz.chengqian.basesdk.utils.base

import org.greenrobot.eventbus.EventBus
import xyz.chengqian.basesdk.bean.event.DefaultEvent

/**
 * @author 程前 created on 2019/3/6.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
object UtilsEventBus {
    fun send(arg1:Int){
        EventBus.getDefault().post(DefaultEvent(arg1))
    }

    fun send(arg1:Int,any: Any?){
        EventBus.getDefault().post(DefaultEvent(arg1,any))
    }
    fun send(arg1:Int,arg2:Int,any: Any?){
        EventBus.getDefault().post(DefaultEvent(arg1,arg2,any))
    }
    fun register(subscriber: Any){
        EventBus.getDefault().register(subscriber)
    }
    fun unregister(subscriber: Any){
        EventBus.getDefault().unregister(subscriber)
    }
}